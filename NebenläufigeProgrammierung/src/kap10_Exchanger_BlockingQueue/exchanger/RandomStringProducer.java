package kap10_Exchanger_BlockingQueue.exchanger;

/**
 * Codebeispiel für eine Erzeuger-Klasse mit einem Exchanger
 */
import java.util.concurrent.Exchanger;

public class RandomStringProducer implements Runnable
{
  private Exchanger<String[]> mExchanger = null;
  private String[] mData = new String[100];

  public RandomStringProducer(Exchanger<String[]> exchanger)
  {
    super();
    mExchanger = exchanger;
  }

  public void run()
  {
    try
    {
      for (int j = 0; j < 10; j++)
      {
        for (int i = 0; i < mData.length; i++)
        {
          mData[i] = Util.getRandomString(100);
        }

        // Rendezvous-Punkt
        mData = mExchanger.exchange(mData);
      }

      // Signalisiert das Ende des Austauschs
      mExchanger.exchange(null);
    } catch (InterruptedException exce)
    {
      exce.printStackTrace();
    }
  }

}
