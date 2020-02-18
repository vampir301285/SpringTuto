package kap17_DS_In_MultiThreaded_Umgebung;

/** * Codebeispiel für Adapterklasse für den Comparator */
import java.util.Comparator;
class HelpComparator<T> implements Comparator<T>{  final private Comparator<T> comp;  final private T begin;  final private T end;
  HelpComparator(Comparator<T> comp, T begin, T end)  {    this.comp = comp;    this.begin = begin;    this.end = end;  }

  public int compare(T a, T b)  {    if (a == begin) // begin ist der kleinste (Sentinel-)Knoten    {      return -1;    }
    if (b == end) // end ist der größte (Sentinel-)Knoten    {      return 1;    }     else    {      return comp.compare(a, b);    }  }}
