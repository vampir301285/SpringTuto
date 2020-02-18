package kap17_DS_In_MultiThreaded_Umgebung;

/** * Codebeispiel für Listenknoten jeweils mit einem eigenen Lock */
import java.util.concurrent.locks.Lock;import java.util.concurrent.locks.ReentrantLock;

public class NodeWithLock<T>{  final T data;  NodeWithLock<T> next;  private final Lock lock = new ReentrantLock();
  NodeWithLock(T item)  {    data = item;  }
  void lock()  {    lock.lock();  }
  void unlock()  {    lock.unlock();  }}