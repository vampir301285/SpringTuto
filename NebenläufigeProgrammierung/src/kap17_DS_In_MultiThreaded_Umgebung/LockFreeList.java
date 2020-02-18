package kap17_DS_In_MultiThreaded_Umgebung;

/** * Codebeispiel für lockfreie Liste */
import java.util.Comparator;import java.util.concurrent.atomic.AtomicMarkableReference;

public class LockFreeList<T> implements IList<T>{  // Listenanfang  private LockFreeNode<T> head;
  // Zum Vergleich der Elemente  private Comparator<T> comparator;
  public LockFreeList(final Comparator<T> comp, T begin, T end)  {    head = new LockFreeNode<T>(begin);    head.next.set(new LockFreeNode<T>(end), false);    comparator = new HelpComparator<T>(comp, begin, end);  }
  private LockFreeNode<T> find(T item)  {    LockFreeNode<T> prev, curr, succ;    boolean[] marked = { false };    boolean snip;
    retry: while (true)    {      prev = head;      curr = head.next.getReference();      while (true)      {        // succ ist Nachfolger von curr        succ = curr.next.get(marked);
        // Solange der Knoten curr markiert ist, wird er        // aus der Liste gelöscht        while (marked[0])        {          // Erwartet: Referenz ist curr und prev ist noch          // nicht gelöscht => curr wird gelöscht          snip = prev.next.compareAndSet(curr, succ, false, false);
          if (!snip)          {            // fehlgeschlagen (prev wurde gelöscht oder sein Nachfolger wurde            // geändert), beginnt wieder von vorne            continue retry;          }
          curr = succ;          succ = curr.next.get(marked);        }
        // Passende Stelle gefunden?        if (comparator.compare(curr.data, item) >= 0)        {          // prev.data < curr.data && item <= curr.data          // Für das letzte Sentinel-Element last der Liste          // gilt immer item < last
          return prev;        }
        prev = curr;        curr = succ;      }    }  }

  public boolean add(T item)  {    if (item == null)      return false;
    while (true)    {      LockFreeNode<T> prev = find(item);      LockFreeNode<T> curr = prev.next.getReference();      // Element vorhanden => Verlassen      if (comparator.compare(curr.data, item) == 0)      {        return false;      }       else      {        LockFreeNode<T> e = new LockFreeNode<T>(item);        e.next.set(curr, false);
        // versucht, den neuen Knoten anzuhängen        // Falls es fehlschlägt (Grund: prev wurde gelöscht oder        // sein Nachfolger wurde verändert), wird erneut versucht        if (prev.next.compareAndSet(curr, e, false, false))          return true;      }    }  }
  public boolean remove(T item)  {    if (item == null)      return false;
    boolean snip;
    while (true)    {      LockFreeNode<T> prev = find(item);      LockFreeNode<T> curr = prev.next.getReference();
      if (comparator.compare(curr.data, item) != 0)      {        // Nicht gefunden        return false;      }       else      {        // Zeiger auf das nächste Element        LockFreeNode<T> succ = curr.next.getReference();
        // Versucht, den Knoten als markiert zu setzen        snip = curr.next.attemptMark(succ, true);
        // Fehl geschlagen => erneuter Versuch        if (!snip)          continue;
        // Versucht, den Knoten zu löschen. Es ist möglich, dass        // der Nachfolger von prev nicht mehr curr ist        prev.next.compareAndSet(curr, succ, false, false);
        return true;      }    }  }

  public boolean contains(T item)  {    if (item == null)      return false;
    AtomicMarkableReference<LockFreeNode<T>> currMRef = head.next;    LockFreeNode<T> node = currMRef.getReference();
    boolean mark = node.next.isMarked();
    while (comparator.compare(node.data, item) < 0)    {      currMRef = node.next;      node = currMRef.getReference();      mark = node.next.isMarked();    }
    // node.data ist gleich item und der Knote wurde noch    // nicht gelöscht!    return (comparator.compare(node.data, item) == 0) && (mark == false);  }}
