package kap06_Threadpools;

/**
 * Codebeispiel für eine parallele Suche nach einem Wort 
 * in einer Datei
 */
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

class FindWordInFiles implements Callable<List<String>>
{
  private final Pattern searchPattern;
  private final Path path; // Dateipfad

  public FindWordInFiles(Path path, String search)
  {
    this.path = path;
    this.searchPattern = Pattern.compile(".*\\b" + search + "\\b.*");
  }

  public List<String> call() throws IOException
  {
    List<String> result = new ArrayList<>();

    List<String> lines = Files.readAllLines(path,
        StandardCharsets.UTF_8);

    int count = 0;
    for (String line : lines)
    {
      count++;
      if (searchPattern.matcher(line).matches())
      {
        result.add(path + " " + count + " : " + line);
      }
    }

    return result;
  }
}

public class FindWordBeispiel
{

  public static void main(String[] args)
  {
    ExecutorService pool = Executors.newCachedThreadPool();

    String search = "Haus";

    Callable<List<String>> task1 = new FindWordInFiles(
        Paths.get("Text1.txt"), search);
    Callable<List<String>> task2 = new FindWordInFiles(
        Paths.get("Text2.txt"), search);
    Callable<List<String>> task3 = new FindWordInFiles(
        Paths.get("Text3.txt"), search);

    Future<List<String>> task1Future = pool.submit(task1);
    Future<List<String>> task2Future = pool.submit(task2);
    Future<List<String>> task3Future = pool.submit(task3);

    try
    {
      List<String> task1Liste = task1Future.get();
      List<String> task2Liste = task2Future.get();
      List<String> task3Liste = task3Future.get();

      task1Liste.forEach(System.out::println);
      task2Liste.forEach(System.out::println);
      task3Liste.forEach(System.out::println);
    } catch (InterruptedException | ExecutionException e)
    {
      e.printStackTrace();
    }

    pool.shutdown();
  }

}
