package kap16_Asynchrones_Logging;

/**
 * Codebeispiel für eine einfache, gepufferte Logger-Klasse.
 *
 */
public class SimpleLogger
{  private static final int CAPACITY = 1024;  private final StringBuilder log = new StringBuilder(CAPACITY);
  public void log(String msg)
  {
    log.append(Thread.currentThread().getName()).append(" : ")        .append(msg).append(System.lineSeparator());
    if (log.length() > CAPACITY)    {      System.out.print(log);      log.setLength(0);    }  }
  public void flush()  {    if (log.length() > 0)    {      System.out.print(log);      log.setLength(0);    }  }}
