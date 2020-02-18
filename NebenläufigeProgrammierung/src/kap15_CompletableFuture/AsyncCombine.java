package kap15_CompletableFuture;

/**
 * Codebeispiel für das Zusammenführen von zwei nebenläugen Tasks
 */

import java.util.concurrent.CompletableFuture;

public class AsyncCombine
{

  public static void main(String[] args)
  {
    CompletableFuture<String>  task1  = CompletableFuture.supplyAsync( () -> "Hello ");
    CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync( () ->  42 );
      
    CompletableFuture<String>  result = task1.thenCombine(task2, (t,r) -> t+r );
    
    System.out.println( result.join() );
  }

}
