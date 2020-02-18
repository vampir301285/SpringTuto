package kap03_Konkurriende_Zugriffe;
/**
 * Codebeispiel für eine falsche Verwendung von ThreadLocalRandom
 */

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MyThreadLocalRandomWrong
{
  public static Random sRand = ThreadLocalRandom.current();

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
          strBuf.append(sRand.nextInt(100) + " ");
        }
        System.out.println(strBuf);
      }).start();
    }
  }
}
