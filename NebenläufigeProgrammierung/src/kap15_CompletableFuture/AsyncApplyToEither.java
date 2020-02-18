package kap15_CompletableFuture;

/**
 * Codebeispiel für die ODER-Zusammenführung von zwei nebenläugen Tasks
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class AsyncApplyToEither
{

  public static void main(String[] args)
  {
    CompletableFuture<Number>  task1 = CompletableFuture.supplyAsync( () -> { randDelay(); return 42.0; } );
    CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync( () -> { randDelay(); return 13; } );
      
    CompletableFuture<String>  result = task1.applyToEitherAsync( task2,  t ->  "Hallo " + t   );
    
    
    System.out.println( result.join() );
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
}
