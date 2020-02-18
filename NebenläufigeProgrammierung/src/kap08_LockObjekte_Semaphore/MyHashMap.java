package kap08_LockObjekte_Semaphore;

/**
 * Codebeispiel für die Verwendung eines ReadWriteLock
 */
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyHashMap<K, V>
{
  private final Map<K, V> hashMap;
  // non-fair wegen der Performance!
  private final ReadWriteLock lock = new ReentrantReadWriteLock();
  private final Lock readLock = lock.readLock();
  private final Lock writeLock = lock.writeLock();

  public MyHashMap(Map<K, V> map)
  {
    hashMap = map;
  }

  public void put(K key, V value)
  {
    writeLock.lock();
    try
    {
      hashMap.put(key, value);
    } finally
    {
      writeLock.unlock();
    }
  }

  public V get(K key)
  {
    readLock.lock();
    try
    {
      return hashMap.get(key);
    } finally
    {
      readLock.unlock();
    }
  }

  public V remove(K key)
  {
    writeLock.lock();
    try
    {
      return hashMap.remove(key);
    } finally
    {
      writeLock.unlock();
    }
  }

  public boolean containsKey(K key)
  {
    readLock.lock();
    try
    {
      return hashMap.containsKey(key);
    } finally
    {
      readLock.unlock();
    }
  }
}
