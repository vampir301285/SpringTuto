package kap20_Mergesort;

/**
 * Codebeispiel für den parallelen Mergesort-Algorithmus
 */

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;


public class ParallelMerge
{
  private ParallelMerge() { }
  
  // Task für das ForkJoin-Framework
  @SuppressWarnings("serial")
  private static class SortTask<E> extends RecursiveAction
  {
    private final Comparable<E>[] a;
    private final Comparable<E>[] aux;
    private final int lo;
    private final int hi;
    
    private SortTask(Comparable<E>[] a, Comparable<E>[] aux, int lo, int hi)
    {
      this.a = a;
      this.aux = aux;
      this.lo = lo;
      this.hi = hi;
    }

    @Override
    protected void compute()
    {
      // Falls Array weniger als 1024 Plätze besitzt, wird 
      // mit einem Standardalgorithmus seriell sortiert
      if (hi - lo < 1024)
      {
        Arrays.sort(a, lo, hi+1);
        return;
      }
        
      // Abspaltung von zwei Tasks
      int mid = lo + (hi - lo) / 2;
      SortTask<E> leftTask = new SortTask<>(a, aux, lo, mid);
      SortTask<E> rightTask = new SortTask<>(a, aux, mid + 1, hi);
      invokeAll(leftTask,rightTask);
      merge(a, aux, lo, mid, hi);
    }
  }
  
  @SuppressWarnings("unchecked")
  public static <E> void sort(Comparable<E>[] a)
  {
    Comparable<E>[] aux = (Comparable<E>[]) new Comparable[a.length];
    
    SortTask<E>  root = new SortTask<>(a, aux, 0, a.length - 1);
    root.invoke();
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
