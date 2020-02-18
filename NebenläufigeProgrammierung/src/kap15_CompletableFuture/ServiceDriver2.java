package kap15_CompletableFuture;

/**
 * Codebeispiel für eine sequenzielle Aufrufkette unter
 * Verwednung von Methodenreferenzen und einem expliziten 
 * Threadpool
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ServiceDriver2
{

  public static void main(String[] args) throws InterruptedException
  {
    ExecutorService threadPool = Executors.newCachedThreadPool();
    
    CompletableFuture.supplyAsync(
        () -> Service.getUser(42), threadPool )
                     .thenApplyAsync( Service::getProfile, threadPool )
                     .thenApplyAsync( Service::getAccessRight, threadPool )
                     .thenAcceptAsync( System.out::println, threadPool );
    
    // ....
    int wait = 10;
    while( wait > 0 )
    {
      TimeUnit.MILLISECONDS.sleep(500);
      System.out.println(wait);
      wait--;
    }
    
    threadPool.shutdown();
  }

}
