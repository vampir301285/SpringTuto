package kap22_RSA_Key;

/**
 * Demonstriert den Gebrauch der Klasse RSAKeyGenerator
 */

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import kap22_RSA_Key.RSAKeyGenerator.KeyValue;

public class TestDriver
{

  public static void main(String[] args)
  {
    final Random rd = new Random();
    final int LEN = 1024 + 512;  // Länge der Primzahlen
    final int RUN = 10;
    
    long start, end;
    
    long sumParallel = 0;
    long sumSeriell  =  0;
    
    KeyValue key;
    BigInteger msg, chiffre, deChiffre;
    
    for(int i=0; i< RUN; i++ )
    {
      System.out.println("Lauf " + i );
      start = System.currentTimeMillis();
      key = RSAKeyGenerator.getKeyParallel(LEN);
      end = System.currentTimeMillis();
      sumParallel += (end -start);
      
      
      // Prüfe ob Schlüssel korrekt berechnet wurde indem
      // eine Zufallsnachricht ver- und entschlüsselt wird 
      msg = new BigInteger( key.N.bitLength()/4, rd );
      chiffre = msg.modPow(key.e, key.N);        // Verschlüsselung
      deChiffre = chiffre.modPow(key.d, key.N);  // Entschlüsselung
      if(  msg.equals(deChiffre) == false )
      {
        System.err.println("Fehler bei der Schlüsselberechnung");
      }
      
      start = System.currentTimeMillis();
      key = RSAKeyGenerator.getKeySequential(LEN);
      end = System.currentTimeMillis();
      sumSeriell += (end -start);
      
      //Prüfe ob Schlüssel korrekt berechnet wurde
      msg = new BigInteger( key.N.bitLength()/4, rd );
      chiffre = msg.modPow(key.e, key.N);
      deChiffre = chiffre.modPow(key.d, key.N);
      if(  msg.equals(deChiffre) == false )
      {
        System.err.println("Fehler bei der Schlüsselberechnung");
      }
    }
    
    System.out.println("Durchschnittliche Rechenzeit für die parallele Schlüsselerzeugnung : " + sumParallel/RUN + "[ms]");
    System.out.println("Durchschnittliche Rechenzeit für die serielle Schlüsselerzeugnung  : " + sumSeriell/RUN + "[ms]");

  
    System.out.println("Erzeuge parallel zwei Primzahlen mit Hilfe des CompletionService");
    List<BigInteger> primes = RSAKeyGenerator.getTwoPrimes(1024+512, 8 );
    primes.forEach( System.out::println );
  }

}
