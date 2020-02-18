package kap17_DS_In_MultiThreaded_Umgebung;

/** * Codebespiel für feingranulare Synchronisierung. * Die Liste wird  zu jedem Zeitpunkt als eine Sammlung  * von nicht überlappenden Arbeitsbereichen betrachtet.  * Daher können  konkurrierende Zugriffe stattfinden. */
import java.util.Comparator;

public class FineGrainedList<T>{  private NodeWithLock<T> head;  private Comparator<T> comparator;

  public FineGrainedList(final Comparator<T> comp, T begin, T end)  {    head = new NodeWithLock<T>(begin);    head.next = new NodeWithLock<T>(end);    comparator = new HelpComparator<T>(comp, begin, end);  }

  // Während des Traversierens werden die Knoten  // nacheinander ge- und entsperrt. Dabei wird der  // Vorgänger eines Knotens erst freigegeben, wenn er  // selbst erfolgreich gesperrt wurde.  // Am Ende bleiben prev und curr gesperrt.  private NodeWithLock<T> find(T item)  {    NodeWithLock<T> prev, curr;
    prev = head;    prev.lock();    curr = head.next;    curr.lock();
    while (comparator.compare(curr.data, item) < 0)    {      prev.unlock();      prev = curr;      curr = prev.next;      curr.lock();    }
    return prev;  }
  public boolean add(T item)  {    if (item == null)      return false;
    NodeWithLock<T> prev, curr;    prev = find(item);    curr = prev.next;
    try    {      if (comparator.compare(curr.data, item) == 0)      {        return false; // schon in der Liste      }
      // Einfügen      NodeWithLock<T> e = new NodeWithLock<T>(item);      prev.next = e;      e.next = curr;
      return true;    }     finally    {      curr.unlock();      prev.unlock();    }  }
  public boolean remove(T item)  {    if (item == null)      return false;
    NodeWithLock<T> prev = find(item);    NodeWithLock<T> curr = prev.next;
    try    {      if (comparator.compare(curr.data, item) == 0)      {        prev.next = curr.next; // Knoten wird entfernt        return true;      }      return false;    }     finally    {      curr.unlock();      prev.unlock();    }  }

  public boolean contains(T item)  {    if (item == null)      return false;
    NodeWithLock<T> prev = find(item);    NodeWithLock<T> curr = prev.next;
    try    {      return (comparator.compare(curr.data, item) == 0);    }     finally    {      curr.unlock();      prev.unlock();    }  }}
