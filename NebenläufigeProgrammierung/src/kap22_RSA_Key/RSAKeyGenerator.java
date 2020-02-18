package kap22_RSA_Key;

/**
 * Codebeispiel für die sequenzielle und parallele RSA-Schlüsselerzeugung
 */

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;


public class RSAKeyGenerator
{
  public static class KeyValue
  {
    public final BigInteger N;
    public final BigInteger e;
    public final BigInteger d;

    private KeyValue(BigInteger N, BigInteger e, BigInteger d)
    {
      this.N = N;
      this.e = e;
      this.d = d;
    }

    @Override
    public String toString()
    {
      StringBuilder str = new StringBuilder();
      str.append(System.lineSeparator());
      str.append("N : " + N);
      str.append(System.lineSeparator());
      str.append("e : " + e);
      str.append(System.lineSeparator());
      str.append("d : " + d);

      return str.toString();
    }
  }

  public static KeyValue getKeyParallel(final int len)
  {
    // Berechne zwei Primzahlen
    CompletableFuture<BigInteger> primPFuture = getAsyncPrim(len);
    CompletableFuture<BigInteger> primQFuture = getAsyncPrim(len);

    // Berechne N und Phi
    CompletableFuture<BigInteger> NFuture = primPFuture.thenCombineAsync(primQFuture, BigInteger::multiply);

    CompletableFuture<BigInteger> PhiFuture = primPFuture.thenApplyAsync((b) -> b.subtract(BigInteger.ONE))
        .thenCombineAsync(primQFuture.thenApplyAsync((b) -> b.subtract(BigInteger.ONE)), BigInteger::multiply);

    BigInteger N = NFuture.join();
    BigInteger Phi = PhiFuture.join();

    // Wähle e
    Random rd = new Random();
    BigInteger rndBigInt = new BigInteger(Phi.bitLength() / 3, rd);
    while (rndBigInt.gcd(Phi).equals(BigInteger.ONE) == false)
    {
      rndBigInt = new BigInteger(Phi.bitLength() / 3, rd);
    }
    BigInteger e = rndBigInt;

    // Berechne d
    BigInteger d = e.modInverse(Phi);

    return new KeyValue(N, e, d);
  }

  private static CompletableFuture<BigInteger> getAsyncPrim(int len)
  {
    Supplier<BigInteger> primSupplier = () -> {
      CompletableFuture<Object> cfPrim = CompletableFuture.anyOf(
          CompletableFuture.supplyAsync(() -> BigInteger.probablePrime(len, ThreadLocalRandom.current())),
          CompletableFuture.supplyAsync(() -> BigInteger.probablePrime(len, ThreadLocalRandom.current())),
          CompletableFuture.supplyAsync(() -> BigInteger.probablePrime(len, ThreadLocalRandom.current())),
          CompletableFuture.supplyAsync(() -> BigInteger.probablePrime(len, ThreadLocalRandom.current())));
      return (BigInteger) cfPrim.join();
    };

    return CompletableFuture.supplyAsync(primSupplier);
  }

  public static KeyValue getKeySequential(int len)
  {
    // Bestimme zwei Primzahlen mit Bitlänge len
    Random rnd = new Random();
    BigInteger primP = BigInteger.probablePrime(len, rnd);
    BigInteger primQ = BigInteger.probablePrime(len, rnd);

    // Berechne phi und N
    BigInteger N = primP.multiply(primQ);
    BigInteger phi = primP.subtract(BigInteger.ONE).multiply(primQ.subtract(BigInteger.ONE));

    // Wähle e
    BigInteger rndBigInt = new BigInteger(phi.bitLength() / 3, rnd);
    while (rndBigInt.gcd(phi).equals(BigInteger.ONE) == false)
    {
      rndBigInt = new BigInteger(phi.bitLength() / 3, rnd);
    }
    BigInteger e = rndBigInt;

    // Berechne d
    BigInteger d = e.modInverse(phi);

    return new KeyValue(N, e, d);
  }

  public static List<BigInteger> getTwoPrimes(int len, int parallel)
  {
 
    List<Callable<BigInteger>> tasks = new ArrayList<>();
    for (int i = 0; i < parallel; i++)
    {
      tasks.add(() -> BigInteger.probablePrime(len, ThreadLocalRandom.current()));
    }

    CompletionService<BigInteger> completionService = new ExecutorCompletionService<>(ForkJoinPool.commonPool());
    tasks.forEach(completionService::submit);

    List<BigInteger> primes = new ArrayList<>();
    try
    {
      for (int i = 0; i < 2; i++)
      {
        primes.add(completionService.take().get());
      }
    }
    catch (InterruptedException | ExecutionException exce)
    {
      exce.printStackTrace();
    }

    return primes;
  }
}
