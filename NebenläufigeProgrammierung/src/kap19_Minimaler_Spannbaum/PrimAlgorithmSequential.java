package kap19_Minimaler_Spannbaum;

/**
 * Codebeispiel für den seriellen Prim-Algorithmus
 */

public class PrimAlgorithmSequential
{ 
  public static double minimalEdgeWeight(double[][] graph)
  {    
    int size = graph.length;
    
    ArrayItem[] d = new ArrayItem[graph.length];
    
    // Wähle Knoten 0 als Startknoten
    d[0] = new ArrayItem( 0, true );
    for(int i=1; i < size; i++ )
    {
      d[i] = new ArrayItem( graph[0][i], false);
    }
    
    int sum = 0;
    for(int i=1; i < size; i++ )
    {
      int index = getIndexFromMinimum(d);
      d[index].fix = true;
      sum += d[index].value;
      
      transferMinMatrixElementToArray(graph,d,index);
    }
    
    return sum;
  }
  
  
  private static void transferMinMatrixElementToArray(double[][] graph, ArrayItem[] d, int col)
  {
    for(int i=0; i < graph.length; i++)
    {
      if( d[i].fix == false )
      {
        if( graph[col][i] < d[i].value )
        {
          d[i].value = graph[col][i];
        }
      }
    }
  }
  
  
  private static int getIndexFromMinimum(ArrayItem[] array)
  {
    int index = 0;
    double min = Double.POSITIVE_INFINITY;
    for(int i=1; i<array.length; i++ )
    {
      if( array[i].fix == false )
      {
        if( array[i].value < min )
        {
          min = array[i].value;
          index = i;
        }
      }
    }
    return index;
  }
 
}
