package kap15_CompletableFuture;

/**
 * Codebeispiel: Verarbeitung von Cancel
 */

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class AsyncSequenzWithCancel
{

  public static void main(String[] args)
  {
    CompletableFuture<Object> cf = CompletableFuture.supplyAsync( () -> 42 )
              .thenApplyAsync( r -> { delay(1000); return r-13;} )
              .thenApplyAsync( r -> r*r )
              .handleAsync( (r,th) -> { 
                 if( r != null )
                 {
                   return r;
                 }
                 else
                 {
                   if( th instanceof CancellationException )
                     return "Canceled";
                   else
                     return "Error";
                 }
                   
              } );

    delay(100);
    cf.cancel(true);
    
    System.out.println( cf.join() );
  }
  
  public static void delay(int ms)
  {
    try
    {
      TimeUnit.MILLISECONDS.sleep(ms);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }

}
