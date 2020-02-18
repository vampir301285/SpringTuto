package kap17_DS_In_MultiThreaded_Umgebung;

/** * Codebeispiel für die optimistische Synchronisierungsstrategie. * Bei find wird die Position ohne Lock-Anforderungen gesucht wird. * Wird sie gefunden, werden später prev und curr vor der durchzuführenden * Aktivität gesperrt. Danach muss allerdings deren Gültigkeit bestätigt  * werden, da sichergestellt werden muss, dass prev noch von head aus * erreichbar ist (validate-Methode) */
import java.util.Comparator;

public class OptimisticSyncList<T>{  private NodeWithLock<T> head;  private Comparator<T> comparator;
  public OptimisticSyncList(final Comparator<T> comp, T begin, T end)  {    head = new NodeWithLock<T>(begin);    head.next = new NodeWithLock<T>(end);    comparator = new HelpComparator<T>(comp, begin, end);  }
  // item wird gesucht, ohne irgendeinen Knoten zu sperren  private NodeWithLock<T> find(T item)  {    NodeWithLock<T> prev, curr;    prev = head;    curr = head.next;
    while (comparator.compare(curr.data, item) < 0)    {      prev = curr;      curr.lock();    }
    return prev;  }
  // Validierung, ob prev immer noch Vorgänger von curr  // und prev von head erreichbar ist.  // prev und curr müssen vorher gesperrt sein.  private boolean validate(NodeWithLock<T> prev, NodeWithLock<T> curr)  {    NodeWithLock<T> node = head;
    while (comparator.compare(node.data, prev.data) <= 0)    {      if (node == prev)      {        return prev.next == curr;      }      node = node.next;    }
    return false;  }

  public boolean add(T item)  {    if (item == null)      return false;
    while (true)    {      NodeWithLock<T> prev = find(item);      NodeWithLock<T> curr = prev.next;
      prev.lock();      curr.lock();
      try      {        if (validate(prev, curr))        {          if (comparator.compare(curr.data, item) == 0)          {            return false;          }           else          {            NodeWithLock<T> e = new NodeWithLock<T>(item);            prev.next = e;            e.next = curr;            return true;          }        }      }       finally      {        curr.unlock();        prev.unlock();      }    }  }

  public boolean remove(T item)  {    if (item == null)      return false;
    while (true)    {      NodeWithLock<T> prev = find(item);      NodeWithLock<T> curr = prev.next;      prev.lock();      curr.lock();
      try      {        if (validate(prev, curr))        {          if (comparator.compare(curr.data, item) == 0)          {            prev.next = curr.next;            return true;          }           else          {            return false;          }        }      }       finally      {        curr.unlock();        prev.unlock();      }    }  }

  public boolean contains(T item)  {    if (item == null)      return false;
    while (true)    {      NodeWithLock<T> prev, curr;      prev = find(item);      curr = prev.next;
      prev.lock();      curr.lock();
      try      {        if (validate(prev, curr))        {          return (comparator.compare(curr.data, item) == 0);        }      }       finally      {        curr.unlock();        prev.unlock();      }    }  }}
