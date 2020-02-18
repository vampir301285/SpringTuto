package kap16_Asynchrones_Logging.loggerMitExchanger;
/**
 * Codebeispiel für Logger mit Exchanger. Der Service
 * tauscht die auszugebenden Puffer mit anderen Threads
 * unter der Verwendung von einem Exchanger
 */

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.concurrent.Exchanger;

class LogService extends Thread
{
  // Klassenobjekt, wird beim Laden der Klasse erzeugt
  private static LogService instance = new LogService();
  // Für die Ausgabe
  private PrintStream out;
  // Exchanger für den Puffertausch
  private Exchanger<StringBuilder> exchanger = new Exchanger<>();
  // Tausch-Puffer
  private StringBuilder buff = new StringBuilder(Logger.CAPACITY);

  // Nur ein Objekt ist erlaubt!
  private LogService()
  {

    try
    {
      out = new PrintStream("log.txt");
      setDaemon(true);
      setPriority(MIN_PRIORITY);
      start(); // Es ist OK, weil der Konstruktor private ist
    } catch (FileNotFoundException ex)
    {
      throw new IllegalArgumentException(ex);
    }
  }

  public static StringBuilder log(StringBuilder obj)
  {
    return instance.print(obj);
  }

  private synchronized StringBuilder print(StringBuilder obj)
  {
    StringBuilder b = null;
    try
    {
      b = exchanger.exchange(obj); // Puffer tauschen
    } catch (InterruptedException ex)
    {
    }
    return b;
  }

  public void run()
  {
    while (true)
    {
      try
      {
        // auszugebenden Puffer holen
        buff = exchanger.exchange(buff);
        // ausgeben und ihn leeren
        out.print(buff);
        buff.setLength(0);
      } catch (Exception ex)
      {
      }
    }
  }
}
