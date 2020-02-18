/**
 * Codebeispiel zur Ausgabe verschiedener Attribute des main-Threads.
 * Für jedes ausführbare Programm wird ein Java-Prozess gestartet.
 * Dieser Prozess besteht mindestens aus einem Thread, dem main-Thread. 
 * Der main-Thread hat die ID (identification) 1 und den Namen main
 */
package kap02_Thread_Konzept;

public class MainThreadEigenschaft
{
  public static void main(String[] args)
  {
    // Anzahl der Prozessoren abfragen
    int nr = Runtime.getRuntime().availableProcessors();
    System.out.println("Anzahl der Prozessoren " + nr);
    // Eigenschaften des main-Threads
    Thread self = Thread.currentThread();
    System.out.println("Name : " + self.getName());
    System.out.println("Priorität : " + self.getPriority());
    System.out.println("ID : " + self.getId());
  }
}