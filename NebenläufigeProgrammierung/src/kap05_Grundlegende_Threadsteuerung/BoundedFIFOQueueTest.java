package kap05_Grundlegende_Threadsteuerung;

/**
 * Codebeispiel für einen Test mit zwei Erzeugern
 * und einem Verbraucher.
 */
import java.util.concurrent.TimeUnit;

public class BoundedFIFOQueueTest
{
  public static void main(String[] args) throws InterruptedException
  {
    BoundedFIFOQueue<Integer> queue = new BoundedFIFOQueue<>(10);
    Producer producer1 = new Producer(queue);
    Producer producer2 = new Producer(queue);
    Consumer consumer = new Consumer(queue);
    Thread p1 = new Thread(producer1, "Producer1");
    Thread p2 = new Thread(producer2, "Producer2");
    p1.start();
    p2.start();
    Thread c = new Thread(consumer, "Consumer");
    c.start();
    // Warte auf das Ende der Erzeuger
    p1.join();
    p2.join();
    // Warte kurz , dann wird der Verbraucher gestoppt
    TimeUnit.MILLISECONDS.sleep(100);
    c.interrupt();
  }
}
