package kap15_CompletableFuture;

/**
 * Codebeispiel für eine sequenzielle Aufrufkette
 */

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import kap15_CompletableFuture.Service.AccessRight;
import kap15_CompletableFuture.Service.Profile;
import kap15_CompletableFuture.Service.User;

public class ServiceDriver1
{

  public static void main(String[] args) throws InterruptedException
  {
    CompletableFuture.supplyAsync(
         () -> Service.getUser(42))
                      .thenApplyAsync((user) -> Service.getProfile(user))
                      .thenApplyAsync((profile) -> Service.getAccessRight(profile))
                      .thenAcceptAsync((access) -> System.out.println(access));

    // Alternative Aufrufkette
    CompletableFuture<User> cfUser = CompletableFuture.supplyAsync(() -> Service.getUser(42));
    CompletableFuture<Profile> cfProfile = cfUser.thenApplyAsync((user) -> Service.getProfile(user));
    CompletableFuture<AccessRight> cfAccessRight = cfProfile.thenApplyAsync((profile) -> Service.getAccessRight(profile));
    cfAccessRight.thenAcceptAsync((accessRight) -> System.out.println(accessRight));
    
    // Hier muss der Main-Thread warten, damit das
    // Programm nicht sofort terminiert
    int wait = 10;
    while( wait > 0 )
    {
      TimeUnit.MILLISECONDS.sleep(500);
      System.out.println(wait);
      wait--;
    }
  }

}
