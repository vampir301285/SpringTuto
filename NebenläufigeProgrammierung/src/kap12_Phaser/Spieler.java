package kap12_Phaser;

/**
 * Codebeispiel: Die Spieler-Klasse
 */

import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;


public class Spieler implements Runnable
{
  private final String name;
  private final Phaser phaser;
  private int punktestand = 0;

  public Spieler(String name, Phaser phaser)
  {
    this.name = name;
    this.phaser = phaser;
  }

  @Override
  public void run()
  {
    // Warte bis alle Spieler bereit sind
    this.phaser.arriveAndAwaitAdvance();                           

    while (true)
    {
      if (phaser.isTerminated())                              
      {
        System.out.println("Spielende: " + name + " hat "
                                 + punktestand + " Punkte");
        return;
      }

      // würfeln
      int wurf = 1 + ThreadLocalRandom.current().nextInt(6);      
      punktestand += wurf;

      if (wurf == 1)
      {
        System.out.println(" - " + this.name + " ist ausgeschieden");
        this.phaser.arriveAndDeregister();                         
        break;
      }
      else if (wurf == 6)
      {
        int wait = ThreadLocalRandom.current().nextInt(3);         
        if( wait > 0 )
          System.out.println(" + " + name + " muss " 
                                   + wait + " Runde(n) aussetzen");

        for (int i = 0; i < wait; i++)
        {
          phaser.arriveAndAwaitAdvance();
          if (phaser.isTerminated())                          
          {
            System.out.println("Spielende: " + name + " hat " + punktestand 
                               + " Punkte und hat gerade ausgesetzt");
            return;
          }
        }
      }
      else
      {
        phaser.arriveAndAwaitAdvance();                       
      }
    }

    return;
  }
}

