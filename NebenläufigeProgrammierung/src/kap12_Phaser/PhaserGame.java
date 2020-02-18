package kap12_Phaser;

/**
 * Codebeispiel: Starten des Spiels
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;


public class PhaserGame
{
  public static void main(String[] args)
  {
    final int PLAYER = 10;
    final int RUNDEN = 8;

    ExecutorService threadpool = Executors.newCachedThreadPool();

    Phaser phaser = new Phaser()
    {
      @Override
      protected boolean onAdvance(int phase, int registeredParties)
      {
        if (phase == 0)
          System.out.println("Starte Spiel");
        else
          System.out.println("Nächste Rund mit " + registeredParties + " Spieler");

        if (phase == RUNDEN)
          return true; // Spiel ist zu Ende
        else
          return false; // Spiel läuft weiter
      }
    };

    phaser.bulkRegister(PLAYER);
    for (int i = 0; i < PLAYER; i++)
    {
      threadpool.execute(new Spieler("Spieler " + i, phaser));
    }

    threadpool.shutdown();
  }
}
