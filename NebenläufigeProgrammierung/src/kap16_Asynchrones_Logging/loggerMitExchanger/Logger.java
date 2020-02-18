package kap16_Asynchrones_Logging.loggerMitExchanger;

public class Logger implements AutoCloseable
{
  static final int CAPACITY = 1024;
  public static ThreadLocal<Logger> logger = ThreadLocal
      .withInitial(Logger::new);

  public static Logger get()
  {
    return logger.get();
  }

  public static void log(String msg)
  {
    logger.get().add(msg);
  }

  private StringBuilder logBuffer = new StringBuilder(CAPACITY);

  private Logger()
  {
  }

  private void add(String msg)
  {
    logBuffer.append(Thread.currentThread().getName()).append(" : ")
        .append(msg).append(System.lineSeparator());
    if (logBuffer.length() > CAPACITY)
    {
      // Tausch den Buffer!
      logBuffer = LogService.log(logBuffer);
    }
  }

  private void flush()
  {
    if (logBuffer.length() > 0)
    {
      logBuffer = LogService.log(logBuffer);
    }
  }

  @Override
  public void close()
  {
    flush();
  }
}
