package kap15_CompletableFuture;

import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Service
{
  static class User
  {
    private String name;
    
    User(String name)
    {
      super();
      this.name = name;
    }
    
    String getName()
    {
      return this.name;
    }
  }
  
  static class Profile
  {
    private User user;
    
    Profile(User user)
    {
      super();
      this.user = user;
    }
    
    String getUserName()
    {
      return this.user.getName();
    }
  }
  
  static class AccessRight
  {
    AccessRight(Profile profile)
    {
      super();
    }
    
    int getAccessId()
    {
      return 42;
    }
  }
  
  private static Random rand = new Random();
  
  public static User getUser(int userId)
  {
    delayRandom(500, 1000);
    return new User("Douglas Adams");
  }
  
  public static Profile getProfile(User user)
  {
    delayRandom(500, 1000);
    return new Profile(user);
  }
  
  public static AccessRight getAccessRight(Profile profile)
  {
    delayRandom(500, 1000);
    return new AccessRight(profile);
  }
  
  private static void delayRandom(int offset, int milliseconds)
  { 
    int time = offset + rand.nextInt(milliseconds);
    delay(time);
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
