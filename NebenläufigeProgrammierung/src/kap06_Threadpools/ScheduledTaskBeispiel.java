package kap06_Threadpools;
/**
 * Codebeispiel für ScheduledTask
 */
import java.awt.Toolkit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledTaskBeispiel
{
  public static void main(String[] args)
  {
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate( Toolkit.getDefaultToolkit()::beep, 0, 1, TimeUnit.SECONDS);

    scheduler.schedule( () -> { beeperHandle.cancel(true); scheduler.shutdown();}, 10, TimeUnit.SECONDS);
    
  }

}
