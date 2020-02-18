package kap14_Arrays_und_Streams;

/**
 * Codebeispiel Implementierung einer grep-Funktionalität
 */

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileGrep
{
  public static void main(String[] args) throws IOException
  {
    PathMatcher javaMatcher = FileSystems.getDefault().getPathMatcher("glob:**.java");
    Path start = Paths.get("C:/JavaDev");
    
    try(Stream<Path> pathStream = Files.walk(start) )
    {
      pathStream.filter(Files::isRegularFile)
                .filter(javaMatcher::matches)
                .flatMap(path -> {
                    try
                    {
                      return Files.readAllLines(path).stream();
                    }
                    catch (IOException exce)
                    {
                      return Stream.empty();
                    }
                  }).filter(line -> line.contains("Thread")).forEach(System.out::println);
    }
  }
  
}
