package kap15_CompletableFuture;

/**
 * Codebeispiel: Wirkungsweise von allOf und anyOf
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Barrieren
{

  public static void main(String[] args)
  {
    CompletableFuture.allOf(
        CompletableFuture.supplyAsync( () -> { randDelay(); System.out.println("Ende Task 1"); return 1; } ),
        CompletableFuture.supplyAsync( () -> { randDelay(); System.out.println("Ende Task 2"); return 2; } ),
        CompletableFuture.supplyAsync( () -> { randDelay(); System.out.println("Ende Task 3"); return 3; } )
        ).thenAccept( (Void) -> System.out.println("allOf done") );

    delay(3000);
    
    CompletableFuture<Object> result = CompletableFuture.anyOf(
        CompletableFuture.supplyAsync( () -> { randDelay();  return 1; } ),
        CompletableFuture.supplyAsync( () -> { randDelay();  return 2; } ),
        CompletableFuture.supplyAsync( () -> { randDelay();  return 3; } )
         );
    
    System.out.println("anyOf done : " + result.join() );
  }

  private static void randDelay()
  {
    try
    {
      TimeUnit.MILLISECONDS.sleep( (int) (Math.random()*2000) );
    }
    catch (InterruptedException e)
    {
      Thread.currentThread().interrupt();
    }
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
