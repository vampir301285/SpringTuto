package kap21_kMean_Algorithmus.v2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

import kap21_kMean_Algorithmus.datamodel.ClusterCentroid;
import kap21_kMean_Algorithmus.datamodel.ClusterDataPoint;
import kap21_kMean_Algorithmus.datamodel.DataPoint;

public class KMean
{
  public static Map<ClusterCentroid, List<ClusterDataPoint>>  getCluster(List<DataPoint> dataset, final int k )
  {
    assert( k > 0 );
    assert( dataset != null );
    assert( dataset.size() > k );
       
    // Initialisiere Clusterdaten
    // Jeder Punkt wird zufällig einem Cluster zugeordnet
    List<ClusterDataPoint> clusterDataPoints =  dataset.parallelStream()
                                                       .map( dp -> new ClusterDataPoint(dp, ThreadLocalRandom.current().nextInt(k) ))
                                                       .collect( Collectors.toList() );
    return getKMeanCluster(clusterDataPoints, k);
  }
  
  
  private static Map<ClusterCentroid, List<ClusterDataPoint>>  getKMeanCluster(List<ClusterDataPoint> dataset, final int k )
  {  
    // Zuordnen der Punkte zu den Clustern
    ClusterMap  clusterMap = dataset.parallelStream().collect( new ClusterMapCollector() );
  
    // Zähler für Clusterwechsler
    final LongAdder adder = new LongAdder();

    do
    {      
      // Zähler zurücksetzen
      adder.reset();
      
      //Berechne die Mittelpunkte der Cluster
      HashMap<Integer, ClusterCentroid> centroids = new HashMap<>();
      for(int i = 0; i < k; i++ )
      {
        centroids.put(i, clusterMap.getCentroid(i) );
      }
          
      //Ordne Punkte dem neuen Cluster zu
      dataset.parallelStream()
             .forEach( dp -> { int id = getNearestCluster(dp, centroids);
                               if( dp.getClusterId() != id )
                               {
                                 dp.setClusterId( id );
                                 adder.increment();
                               }; 
                             } );
      
      // Neuzuordnen der Punkte zu den Clustern
      clusterMap = dataset.parallelStream().collect( new ClusterMapCollector() );
    }
    while( adder.sum() != 0 );
    
    return clusterMap.getCluster();
  }
  
  
  private static Integer getNearestCluster(ClusterDataPoint dp, Map<Integer, ClusterCentroid> centroids)
  {
    // parallelStream lohnt sich hier nicht, da es in der Regel nur wenig Cluster gibt
    return centroids.entrySet()
                    .stream()  
                    .min( (e1,e2) -> (int) Math.signum( distance(dp, e1.getValue()) - distance(dp, e2.getValue()) ) )            
                    .map(Map.Entry::getKey)
                    .get();
  }
  
  private static double distance(DataPoint p1, DataPoint p2)
  {
    return Math.sqrt( (p1.x-p2.x)*(p1.x-p2.x) + (p1.y-p2.y)*(p1.y-p2.y) );
  }
 
}
