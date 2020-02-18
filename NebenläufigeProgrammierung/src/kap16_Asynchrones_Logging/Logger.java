package kap16_Asynchrones_Logging;
/** * Codebeispiel für Logger-Objekte als Thread-lokale Daten */

import java.io.FileWriter;
import java.io.IOException;import java.io.Writer;

public class Logger implements AutoCloseable{  // Thread-lokales Objekt - Wenn ein Thread das erste Mal  // auf logger zugreift, wird ein Logger-Objekt angelegt  private static ThreadLocal<Logger> logger = ThreadLocal      .withInitial(() -> new Logger(          "Log-" + Thread.currentThread().getName() + ".txt"));
  public static Logger get()  {    return logger.get();  }

  public static void log(String msg)  {    logger.get().add(msg);  }
  // Kapazität für den Puffer  // In der Praxis wird oft ein Vielfaches von 1024 gewählt  static final int CAPACITY = 1024;  private StringBuilder logBuffer = new StringBuilder(CAPACITY);  private Writer fileWriter = null;
  private Logger(String filename)  {    try    {      fileWriter = new FileWriter(filename);    }     catch (IOException ex)    {      throw new IllegalArgumentException(ex);    }  }

  private void add(String msg)  {    logBuffer.append(Thread.currentThread().getName()).append(" : ")        .append(msg).append(System.lineSeparator());
    if (logBuffer.length() > CAPACITY)    {      flush();    }  }
  private void flush()  {    if (logBuffer.length() > 0)    {      try      {        fileWriter.write(logBuffer.toString());      }       catch (Exception ex)      {        ex.printStackTrace();      }
      logBuffer.setLength(0);    }  }
  @Override  public void close()  {    if (fileWriter != null)    {      this.flush();      try      {        fileWriter.flush();        fileWriter.close();      }       catch (IOException ex)      {        ex.printStackTrace();      }
      fileWriter = null;    }  }}
