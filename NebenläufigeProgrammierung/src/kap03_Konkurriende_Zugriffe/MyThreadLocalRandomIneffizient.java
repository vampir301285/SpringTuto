package kap03_Konkurriende_Zugriffe;
/**
 * Codebeispiel für eine Anwendung Thread-lokaler Daten
 */

import java.util.Random;

public class MyThreadLocalRandomIneffizient
{
  private static ThreadLocal<Random> rand = new ThreadLocal<Random>()
  {
    @Override
    protected Random initialValue()
    {
      return new Random();
    }
  };

  public static void main(String[] args)
  {
    for (int i = 0; i < 10; i++) // erzeuge 10 Threads
    {
      new Thread(() ->
      {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append(Thread.currentThread().getId() + " : ");
        for (int j = 0; j < 100; j++)
          strBuf.append(rand.get().nextInt(100) + " ");
        System.out.println(strBuf);
      }).start();
    }
  }
}
