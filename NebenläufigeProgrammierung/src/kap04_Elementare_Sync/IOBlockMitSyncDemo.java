package kap04_Elementare_Sync;
/**
 * Codebeispiel für schlechte Synchronisation.
 * Es werden blockierende IO-Zugriffe in einem 
 * kritischen Abschnitt durchgeführt.
 */

import java.io.InputStream;
import java.net.Socket;

public class IOBlockMitSyncDemo
{
  private InputStream mIn;
  private Socket mURL;
  public IOBlockMitSyncDemo() throws Exception
  {
    mURL = new Socket("www.hs-kl.de", 80);
    mIn = mURL.getInputStream();
  }
  public synchronized int read() throws Exception
  {
    return mIn.read();
  }
  public synchronized void close() throws Exception
  {
    mIn.close();
  }
}
