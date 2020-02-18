package kap05_Grundlegende_Threadsteuerung;

/**
 * Codebeispiel für eine fehlerhafte "Startlinie"
 */
class BadStartingLine
{
  private boolean haltCondition = true;

  public synchronized void halt()
  {
    while (this.haltCondition)
    {
      try
      {
        this.wait();
      } catch (InterruptedException e)
      {
        Thread.currentThread().interrupt();
      }
    }
  }

  public synchronized void go()
  {
    haltCondition = false;
    notifyAll();
  }
}
