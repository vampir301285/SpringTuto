package kap15_CompletableFuture;

/**
 * Codebeispiel: Verarbeitung einer Exception
 */

import java.util.concurrent.CompletableFuture;

public class AsyncSequenzWithError
{

  public static void main(String[] args)
  {
    CompletableFuture<Object> cf = CompletableFuture.supplyAsync( () -> 42 )
              .thenApplyAsync( r -> r/0 )
              .thenApplyAsync( r -> r*r )
              .handleAsync( (r,th) -> { 
                 if( r != null )
                 {
                   return r;
                 }
                 else
                 {
                   return "Error";
                 }
                   
              } );

    System.out.println( cf.join() );
  }

}
