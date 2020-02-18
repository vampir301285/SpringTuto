package kap13_ForkJoinFramework;

/**
 * Codebeispiel zu CountedCompleter für eine Dateisuche
 */

import java.io.File;
import java.io.FileFilter;
import java.util.Optional;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("serial")
public class FindTask extends CountedCompleter<Optional<File>>
{
  private static final FileFilter fileFilter = new FileFilter()
  {
    public boolean accept(File f)
    {
      return f.isDirectory() || f.isFile();
    }
  };

  private final File dir;
  private final String regex;
  private final AtomicReference<File> result;

  public FindTask(File dir, String regex)
  {
    this(null, dir, regex, new AtomicReference<File>(null));
  }

  private FindTask(CountedCompleter<?> parent, File dir, String regex, AtomicReference<File> result)
  {
    super(parent);
    this.dir = dir;
    this.regex = regex;
    this.result = result;
  }

  @Override
  public void compute()
  {
    File[] entries = dir.listFiles(fileFilter);

    if (entries != null )
    {
      for (File entry : entries)
      {
        if( result.get() != null )
          break;
        
        if (entry.isDirectory())
        {
          addToPendingCount(1);
          FindTask task = new FindTask(this, entry, this.regex, result);
          task.fork();
        }
        else
        {
          String tmp = entry.getPath();
          if (tmp.matches(regex) && result.compareAndSet(null, entry))
          {
            quietlyCompleteRoot();
            break;
          }
        }
      }
    }
    tryComplete();
  }

  @Override
  public Optional<File> getRawResult()
  {
    File res = result.get();
    if (res == null)
      return Optional.empty();
    else
      return Optional.of(res);
  }
}

