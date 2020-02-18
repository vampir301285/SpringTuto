package kap19_Minimaler_Spannbaum;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TestDriver
{

  public static void main(String[] args)
  {
    System.out.println("Start");
    double[][] graph = generateRandomGraph(1024*16);  
    System.out.println("Graph created");

    final int RUNS = 5;
    
    long start, dauer;
    for (int i = 0; i < RUNS; i++)
    {
      System.out.println();
      start = System.currentTimeMillis();
      double weightSum1 = PrimAlgorithmSequential.minimalEdgeWeight(graph);
      dauer = System.currentTimeMillis() - start;
      System.out.println("Minimales Gewicht Seriell   " + weightSum1 + " (" + dauer + "[ms])");
      
      start = System.currentTimeMillis();
      double weightSum2 = PrimAlgorithmParallel.minimalEdgeWeight(graph,2);
      dauer = System.currentTimeMillis() - start;
      System.out.println("Minimales Gewicht Parallel V1 (2) " + weightSum2 + " (" + dauer + "[ms])");
         
      start = System.currentTimeMillis();
      double weightSum3 = PrimAlgorithmParallel.minimalEdgeWeight(graph,4);
      dauer = System.currentTimeMillis() - start;
      System.out.println("Minimales Gewicht Parallel V1 (4) " + weightSum3 + " (" + dauer + "[ms])");
    }
    System.out.println("done");


  }
  
  public static double[][] getSampleGraph()
  {
    double[][] graph = new double[6][6];
    
    graph[0][0] = graph[1][1] = graph[2][2] = graph[3][4] = graph[4][4] = graph[5][5] = 0;
 
    graph[0][1] = graph[1][0] = 2;
    graph[0][2] = graph[2][0] = Double.POSITIVE_INFINITY;
    graph[0][3] = graph[3][0] = Double.POSITIVE_INFINITY;
    graph[0][4] = graph[4][0] = 3;
    graph[0][5] = graph[5][0] = Double.POSITIVE_INFINITY;
    
    graph[1][2] = graph[2][1] = 3;
    graph[1][3] = graph[3][1] = 2;
    graph[1][4] = graph[4][1] = Double.POSITIVE_INFINITY;
    graph[1][5] = graph[5][1] = Double.POSITIVE_INFINITY;
    
    graph[2][3] = graph[3][2] = 4;
    graph[2][4] = graph[4][2] = Double.POSITIVE_INFINITY;
    graph[2][5] = graph[5][2] = 2;
    
    graph[3][4] = graph[4][3] = 2;
    graph[3][5] = graph[5][3] = 1;
    
    graph[4][5] = graph[5][4] = 3;
    
    return graph;
  }
  
  public static double[][] generateRandomGraph(int nodes)
  {
    double[][] graph = generateRandomTree(nodes);
    
    Random rd = new Random();
    for( int k=0; k < nodes; k++)
    {
      int i = rd.nextInt(nodes);
      int j = rd.nextInt(nodes);
      
      if( i != j && graph[i][j] == Double.POSITIVE_INFINITY )
      {
        graph[i][j] = 1 + rd.nextInt(100);
        graph[j][i] = 1 + rd.nextInt(100);
      }
    }
    
    return graph;
  }
  
  public static double[][] generateRandomTree(int nodes)
  {
    double[][] graph = new double[nodes][nodes];
    
    for(int i=0; i < nodes; i++ )
      for(int j=0; j < nodes; j++ )
        graph[i][j] = Double.POSITIVE_INFINITY;
    
    // Konstruiere aufspannenden Baum mit Gewicht (n-1)*1
    List<Integer> items = new LinkedList<>();
    for (int i = 0; i < nodes; i++)
    {
      items.add(new Integer(i));
    }

    Random rd = new Random();
    Integer start = items.get( rd.nextInt( items.size() ));
    while( items.size() > 1 )
    {
      Integer end = items.get( rd.nextInt(items.size())) ;
      if( start != end )
      {
        graph[start][end] = 1;
        graph[end][start] = 1;
        items.remove(start);
        start = end;
      }
    }
    Integer end = items.get(0);
    graph[start][end] = 1;
    graph[end][start] = 1;
    
    return graph;
  }


}
