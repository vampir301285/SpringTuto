package kap14_Arrays_und_Streams;

/**
 * Codebeispiel Auflistung aller Java-Dateien in einem Verzeichnis
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileListing
{
  public static void main(String[] args) throws IOException
  {
    PathMatcher javaMatcher = FileSystems.getDefault().getPathMatcher("glob:**.java");
    Path start = Paths.get("C:/JavaDev");
    
    try( Stream<Path> pathStream = Files.walk(start) )
    {
      pathStream.filter(Files::isRegularFile)
                .filter(javaMatcher::matches)
                .map(file -> file.toString())
                .collect(Collectors.groupingBy( 
                     key -> key.substring(0, key.lastIndexOf(File.separator)), Collectors.toList()))
                .entrySet().stream()
                           .map(entry -> entry.getKey() + " => "
                              + entry.getValue().stream().map(str -> str.substring(str.lastIndexOf(File.separator) + 1, str.length())).collect(Collectors.joining(", ")))
                           .forEach(System.out::println);
    }
  }
}
