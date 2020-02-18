package kap15_CompletableFuture;

/**
 * Codebeispiel für Komposition von zwei Tasks
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

public class AsyncCompose
{
  public static void main(String[] args)
  {
    CompletableFuture<Integer> task = 
        CompletableFuture.supplyAsync( () -> 42 )
                         .thenApplyAsync( r -> r - 40 )
                         .thenComposeAsync( x -> squareAsync( x ) ); 
    System.out.println( task.join() );
  }
  
  
  public static  CompletableFuture<Integer> squareAsync(int n)
  {
    CompletableFuture<Integer> result = new CompletableFuture<Integer>();
   
    Runnable task = new Runnable()
    {
      @Override
      public void run()
      {
        result.complete(n*n);
      }
    }; 
    ForkJoinPool.commonPool().submit(task);
    
    return result;
  }

}
