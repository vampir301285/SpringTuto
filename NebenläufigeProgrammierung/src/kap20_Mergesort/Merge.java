package kap20_Mergesort;

/**
 * Codebeispiel für den seriellen Mergesort-Algorithmus
 */

public class Merge
{
  private Merge() { }
  
  @SuppressWarnings("unchecked")
  public static <E> void sort(Comparable<E>[] a)
  {
    Comparable<E>[] aux = (Comparable<E>[]) new Comparable[a.length];
    sort(a, aux, 0, a.length - 1);
  }

  // mergesort a[lo..hi] unter Benutzung von aux[lo..hi]
  private static <E> void sort(Comparable<E>[] a, Comparable<E>[] aux, int lo, int hi)
  {
    if (hi <= lo)
      return;
    int mid = lo + (hi - lo) / 2;
    sort(a, aux, lo, mid);
    sort(a, aux, mid + 1, hi);
    merge(a, aux, lo, mid, hi);
  }

  
  // merge a[lo .. mid] mit a[mid+1 ..hi], nutze dabei aux[lo .. hi]
  private static <E> void merge(Comparable<E>[] a, Comparable<E>[] aux, int lo, int mid, int hi)
  {
    // kopiere Elemente in aux[]
    System.arraycopy(a, lo, aux, lo, (hi-lo)+1);

    // merge in a[]
    int i = lo, j = mid + 1;
    for (int k = lo; k <= hi; k++)
    {
      if (i > mid)
        a[k] = aux[j++];
      else if (j > hi)
        a[k] = aux[i++];
      else if ( less(aux[j], aux[i])  )
        a[k] = aux[j++];
      else
        a[k] = aux[i++];
    }
  }
  
  @SuppressWarnings("unchecked")
  private static <E> boolean less(Comparable<E> v, Comparable<E> w)
  {
    return v.compareTo((E) w) < 0;
  }
}
