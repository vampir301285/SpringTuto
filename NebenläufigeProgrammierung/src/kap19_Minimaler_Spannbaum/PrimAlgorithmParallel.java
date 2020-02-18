package kap19_Minimaler_Spannbaum;

/**
 * Codebeispiel für den parallelen Prim-Algorithmus
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;


public class PrimAlgorithmParallel
{
  public static double minimalEdgeWeight(double[][] graph, int parallel)
  {
    final int N = parallel;
    final int SIZE = graph.length;
    final AtomicInteger colIndex = new AtomicInteger(0);

    // Initialisierung von d[]
    ArrayItem[] d = new ArrayItem[SIZE];
    for(int i=0; i < SIZE; i++)
    {
      d[i] = new ArrayItem( Double.POSITIVE_INFINITY, false);
    }
    d[0].fix = true;
    d[0].value = 0;
     
    Phaser phaser = new Phaser(N)
    {
      @Override
      protected boolean onAdvance(int phase, int parties)
      {
        if (phase > SIZE )
          return true;
        else
        {
          int newRowlIndex = getIndexFromMinimum(d);
          d[newRowlIndex].fix = true;
          colIndex.set(newRowlIndex);
          return false;
        }
      }
    };
    
    // Konfiguration und Starten der Tasks
    List<PrimTask> tasks = new ArrayList<>();
    for(int i=0; i < N-1; i++)
    {
      int start = i*(SIZE/N);
      int end = start + (SIZE/N);
      tasks.add( new PrimTask(graph, start, end, colIndex, d, phaser) );
    }
    tasks.add( new PrimTask(graph, (N-1)*(SIZE/N), SIZE, colIndex, d, phaser) );
    ForkJoinPool.commonPool().invokeAll(tasks);
    
    // Bestimmung der Summe
    double sum = 0;
    for(int k=0; k < SIZE; k++ )
    {
      sum += d[k].value;
    }
    
    return sum;
  }


  private static int getIndexFromMinimum(ArrayItem[] array)
  {
    int index = 0;
    double min = Double.POSITIVE_INFINITY;
    
    for(int k=0; k < array.length; k++ )
    {
      if( array[k].fix == false )
      {
        if( array[k].value < min )
        {
          min = array[k].value;
          index = k;
        }
      }
    }
    return index;
  }
}
