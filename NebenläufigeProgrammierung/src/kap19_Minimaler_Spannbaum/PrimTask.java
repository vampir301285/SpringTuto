package kap19_Minimaler_Spannbaum;

/**
 * Codebeispiel für die Task-Implementierung für den parallelen Prim-Algorithmus
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;


public class PrimTask implements Callable<Void>
{
  private final double[][] graph;
  private final ArrayItem[]   d;
  private final AtomicInteger row;
  private final Phaser phaser;
  private final int startCol;
  private final int endCol;
  
  public PrimTask(double[][] graph, int startCol, int endCol, AtomicInteger row, ArrayItem[] d, Phaser phaser)
  {
    this.graph = graph;
    this.d = d;
    this.row = row;
    this.phaser = phaser;
    this.startCol = startCol;
    this.endCol = endCol;
  }

  @Override
  public Void call()
  {
    while( true )
    {
      this.phaser.arriveAndAwaitAdvance();
      if( phaser.isTerminated() )
        break;
       
      transferMinMatrixElementToArray( row.get() );
    }

    return (Void) null;
  }
  
  private void transferMinMatrixElementToArray(int row)
  {
    for(int i = startCol; i < endCol; i++)
    {
      if( d[i].fix == false )
      {
        if( graph[row][i] < d[i].value )
        {
          d[i].value = graph[row][i];
        }
      }
    }
  }
}
