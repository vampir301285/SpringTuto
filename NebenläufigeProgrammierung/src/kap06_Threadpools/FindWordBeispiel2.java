package kap06_Threadpools;

/**
 * Codebeispiel für eine parallele Suche nach einem Wort 
 * in einer Datei mithilfe von invokeAll
 */
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FindWordBeispiel2
{

  public static void main(String[] args)
  {
    ExecutorService pool = Executors.newCachedThreadPool();

    String search = "Haus";

    List<Callable<List<String>>> tasks = new ArrayList<>();
    tasks.add(new FindWordInFiles(Paths.get("Text1.txt"), search));
    tasks.add(new FindWordInFiles(Paths.get("Text2.txt"), search));
    tasks.add(new FindWordInFiles(Paths.get("Text3.txt"), search));

    try
    {
      List<Future<List<String>>> tasksFuture = pool.invokeAll(tasks);

      for (Future<List<String>> future : tasksFuture)
      {
        future.get().forEach(System.out::println);
      }
    } catch (InterruptedException | ExecutionException e)
    {
      e.printStackTrace();
    }

    pool.shutdown();
  }

}
