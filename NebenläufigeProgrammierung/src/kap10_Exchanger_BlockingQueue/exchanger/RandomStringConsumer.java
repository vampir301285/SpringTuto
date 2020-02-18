package kap10_Exchanger_BlockingQueue.exchanger;

/**
 * Codebeispiel für eine Verbraucher-Klasse mit einem Exchanger
 */
import java.util.concurrent.Callable;
import java.util.concurrent.Exchanger;

public class RandomStringConsumer implements Callable<int[]>
{
  private Exchanger<String[]> mExchanger = null;
  private String[] mData = new String[100];

  public RandomStringConsumer(Exchanger<String[]> exchanger)
  {
    super();
    mExchanger = exchanger;
  }

  public int[] call()
  {
    try
    {
      int[] charFrequency = new int[Util.BUCHSTABEN.length()];

      while (true)
      {
        // Rendezvous-Punkt
        mData = mExchanger.exchange(mData);

        if (mData == null)
        {
          break;
        } else
        {
          for (int i = 0; i < mData.length; i++)
          {
            String str = mData[i];
            for (int j = 0; j < str.length(); j++)
            {
              char c = str.charAt(j);

              int pos = Util.BUCHSTABEN.indexOf(c);
              if (pos != -1)
                charFrequency[pos]++;
            }
          }
        }
      }

      return charFrequency;
    } catch (Exception exce)
    {
      exce.printStackTrace();
      return null;
    }
  }
}
