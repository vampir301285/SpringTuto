package kap03_Konkurriende_Zugriffe;

/**
 * Codebeispiel für Anwendung von ThreadLocalRandom
 */
import java.util.concurrent.ThreadLocalRandom;

public class MyThreadLocalRandom
{
  public static void main(String[] args)
  {
    // erzeuge 5 Threads
    for (int i = 0; i < 5; i++)
    {
      new Thread(() ->
      {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append(Thread.currentThread().getId() + " : ");
        for (int j = 0; j < 10; j++)
        {
          strBuf
              .append(ThreadLocalRandom.current().nextInt(100) + " ");
        }
        System.out.println(strBuf);
      }).start();
    }
  }
}
