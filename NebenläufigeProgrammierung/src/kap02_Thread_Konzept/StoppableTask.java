package kap02_Thread_Konzept;

/**
 * Codebeispiel zum sicheren Beenden eines Threads mithilfe einer
 * boolean-Variablen
 */
public class StoppableTask implements Runnable
{
  // Um den Träger-Thread zu merken
  private volatile Thread runThread;
  private volatile boolean isStopped = false;

  public void stopRequest()
  {
    isStopped = true;
    if (runThread != null)
    {
      runThread.interrupt(); // damit der Thread ggf. geweckt wird
    }
  }

  public boolean isStopped()
  {
    return isStopped;
  }

  public void run()
  {
    runThread = Thread.currentThread();
    // Initialisierungsphase
    while (isStopped() == false)
    {
      // Arbeitsphase
    }
    // Aufräumphase
  }
}
