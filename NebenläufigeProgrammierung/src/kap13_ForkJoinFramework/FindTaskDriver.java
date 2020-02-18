package kap13_ForkJoinFramework;

/**
 * Codebeispiel für die Verwendung von CountedCompleter
 */

import java.io.File;

public class FindTaskDriver
{
  public static void main(String[] args)
  {
    String search = ".*.java$";
   
    File rootDir = new File("C:\\Dev");
    FindTask root = new FindTask(rootDir, search);
    root.invoke().ifPresent( System.out::println );

    // Alternativer Aufruf
    // root.invoke();
    // root.join().ifPresent( System.out::println );

    System.out.println("done");
  }
}
