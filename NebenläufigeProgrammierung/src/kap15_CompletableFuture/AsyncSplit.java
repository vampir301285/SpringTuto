package kap15_CompletableFuture;

/**
 * Codebeispiel für das Aufspalten in zwei Task
 * Das Programm sollte mehrfach ausgeführt werden, damit
 * auch erkennbar wird, dass verchiedene Threads mit beteiligt
 * sind.
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class AsyncSplit
{
  public static void main(String[] args)
  {
    CompletableFuture<Integer> cf = CompletableFuture.supplyAsync( 
        () -> { 
                 System.out.println("Start " + Thread.currentThread()); 
                 delay(100);
                 return 2; } 
        );
    
    cf.thenApplyAsync( r -> { System.out.println("Zweig 1 " + Thread.currentThread()); delay(100); return r*r; } )
      .thenAcceptAsync( System.out::println );
    
    cf.thenApplyAsync( r -> { System.out.println("Zweig 2 " + Thread.currentThread());  return r*r; } )
      .thenApplyAsync( r -> { System.out.println("Zweig 2 " + Thread.currentThread());  return r+2; } )
      .thenApplyAsync(r -> { System.out.println("Zweig 2 " + Thread.currentThread());  return r; } )
      .thenAcceptAsync( System.out::println );
    
    delay(5000);
    System.out.println("done");
  }

  private static void delay(int milliseconds)
  {
    try
    {
      TimeUnit.MILLISECONDS.sleep(milliseconds);
    }
    catch (InterruptedException e)
    {
      Thread.currentThread().interrupt();
    }
  }
}
