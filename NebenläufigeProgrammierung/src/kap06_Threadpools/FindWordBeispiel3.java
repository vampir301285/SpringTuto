package kap06_Threadpools;

/**
 * Codebeispiel für die Verwendung von CompletionService.
 */
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FindWordBeispiel3
{
  public static void main(String[] args)
  {
    ExecutorService pool = Executors.newCachedThreadPool();

    String search = "Haus";

    List<Callable<List<String>>> tasks = new ArrayList<>();
    tasks.add(new FindWordInFiles(Paths.get("Text1.txt"), search));
    tasks.add(new FindWordInFiles(Paths.get("Text2.txt"), search));
    tasks.add(new FindWordInFiles(Paths.get("Text3.txt"), search));

    CompletionService<List<String>> completionService = new ExecutorCompletionService<>(
        pool);
    tasks.forEach(completionService::submit);

    try
    {
      for (int i = 0; i < tasks.size(); i++)
      {
        Future<List<String>> future = completionService.take();
        future.get().forEach(System.out::println);
      }
    } catch (InterruptedException | ExecutionException e)
    {
      e.printStackTrace();
    }

    pool.shutdown();
  }

}
