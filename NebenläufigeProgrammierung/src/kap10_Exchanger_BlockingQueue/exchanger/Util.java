package kap10_Exchanger_BlockingQueue.exchanger;

/**
 * Hilfsklasse zur Erzeugung von Zufallsstrings.
 */
import java.util.concurrent.ThreadLocalRandom;

public class Util
{
  public static final String BUCHSTABEN = "abcdefghijklmnopqrstuvwxyzüöäß";

  public static String getRandomString(int len)
  {
    StringBuilder strBuild = new StringBuilder(len);
    for (int i = 0; i < len; i++)
    {
      int idx = ThreadLocalRandom.current()
          .nextInt(BUCHSTABEN.length());
      strBuild.append(BUCHSTABEN.charAt(idx));
    }
    return strBuild.toString();
  }
}
