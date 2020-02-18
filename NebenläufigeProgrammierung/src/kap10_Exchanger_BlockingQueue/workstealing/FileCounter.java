package kap10_Exchanger_BlockingQueue.workstealing;

/**
 * Codebeispiel für WorkStealing
 */
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Hilfsklasse für einen Zähler zum koordinierten Beenden der Tasks
 */
class TerminationMonitor
{
  private final AtomicInteger count;

  TerminationMonitor()
  {
    this.count = new AtomicInteger(0);
  }

  void setActive(boolean active)
  {
    if (active)
      count.getAndIncrement();
    else
      count.getAndDecrement();
  }

  boolean isTerminated()
  {
    return count.get() == 0;
  }
}

public class FileCounter
{
  public static class FileCountTask implements Callable<Integer>
  {
    private static final FileFilter fileFilter = new FileFilter()
    {
      public boolean accept(File f)
      {
        return f.isDirectory() || f.isFile();
      }
    };

    private final int nr;
    private final BlockingDeque<File>[] workDeque;
    private final TerminationMonitor barrier;

    private FileCountTask(int nr, BlockingDeque<File>[] workQueues,
        TerminationMonitor barrier)
    {
      this.nr = nr;
      this.workDeque = workQueues;
      this.barrier = barrier;
    }

    @Override
    public Integer call() throws Exception
    {
      int len = this.workDeque.length;
      int count = 0;
      File file = null;

      this.barrier.setActive(true);

      // Hole Elemente aus der dem Thread zugeordneten Queue
      file = this.workDeque[this.nr].pollFirst();
      while (true)
      {
        // Prüfe, ob in der dem Task zugeordneten Queue Elemente vorhanden sind
        while (file != null)
        {
          File[] files = file.listFiles(fileFilter);

          for (File f : files)
          {
            if (f.isDirectory())
            {
              this.workDeque[this.nr].offerFirst(f);
            } else
            {
              count++;
            }
          }
          file = this.workDeque[this.nr].pollFirst();
        }

        // Queue ist jetzt leer
        this.barrier.setActive(false);

        // Work-Stealing-Procedure
        while (file == null)
        {
          // Wenn es nur einen Task gibt, ist Work-Stealing sinnlos
          if (len == 1)
            break;

          // Suche "Victim-Queue", Strategie Round-Robin
          for (int i = 1; i < len; i++)
          {
            int victimQueue = (this.nr + i) % len;
            if (this.workDeque[victimQueue].isEmpty() == false)
            {
              // Hole Item aus der Victim-Queue
              file = this.workDeque[victimQueue].pollLast();
              if (file != null)
              {
                break; // Element war vorhanden
              }
              this.barrier.setActive(false);
            }
            // Alle Elemente sind abgearbeitet
            if (this.barrier.isTerminated())
            {
              return count;
            }
          }
        }
      }
    }
  }

  public static void main(String[] args) throws Exception
  {
    final File root = new File("C:\\DEV");

    final int WORKER = 4;
    TerminationMonitor barrier = new TerminationMonitor();

    @SuppressWarnings("unchecked")
    BlockingDeque<File>[] queues = new LinkedBlockingDeque[WORKER];
    for (int i = 0; i < WORKER; i++)
    {
      queues[i] = new LinkedBlockingDeque<>();
    }
    queues[0].offerFirst(root);

    List<FileCountTask> worker = new ArrayList<>();
    for (int i = 0; i < WORKER; i++)
    {
      worker.add(new FileCountTask(i, queues, barrier));
    }

    ExecutorService threadpool = Executors.newFixedThreadPool(WORKER);

    List<Future<Integer>> futures = threadpool.invokeAll(worker);

    int count = 0;
    for (Future<Integer> f : futures)
    {
      count += f.get();
    }
    System.out.println("Anzah der Dateien : " + count);

    threadpool.shutdown();
  }
}
