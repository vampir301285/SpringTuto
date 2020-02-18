package kap17_DS_In_MultiThreaded_Umgebung;
/** * Codebeispiel für einfache verkettete Liste */
import java.util.Comparator;
public class List<T> implements IList<T>{  private final Node<T> head;  private final HelpComparator<T> comparator;
  public List(final Comparator<T> comp, T begin, T end)  {    this.head = new Node<T>(begin);    this.head.next = new Node<T>(end);    this.comparator = new HelpComparator<T>(comp, begin, end);  }
  public boolean add(T item)  {    if (item == null)    {      return false;    }
    Node<T> prev = find(item);    Node<T> curr = prev.next;
    if (comparator.compare(curr.data, item) == 0)    {      return false; // Element schon vorhanden    }     else    {      Node<T> e = new Node<T>(item);      prev.next = e;      e.next = curr;      return true;    }  }
  public boolean remove(T item)  {    if (item == null)    {      return false;    }
    Node<T> prev = find(item);    Node<T> curr = prev.next;
    if (comparator.compare(curr.data, item) == 0)    { // gefunden      prev.next = curr.next;      return true;    }
    return false;  }
  public boolean contains(T item)  {    if (item == null)    {      return false;    }
    Node<T> curr = find(item).next;    return (comparator.compare(curr.data, item) == 0);  }
  private Node<T> find(T item)  {    Node<T> prev, curr;    prev = head;    curr = head.next;
    // Die Schleife wird auf jeden Fall terminieren, da wir    // am Ende einen Sentinel-Knoten haben    while (comparator.compare(curr.data, item) < 0)    {      prev = curr;      curr = prev.next;    }
    return prev;  }}
