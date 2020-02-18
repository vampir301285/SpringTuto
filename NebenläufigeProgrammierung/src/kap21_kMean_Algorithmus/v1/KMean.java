package kap21_kMean_Algorithmus.v1;

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
    // Initialisiere Clusterdaten
    // Jeder Punkt wird zufällig einem Cluster zugeordnet
    List<ClusterDataPoint> clusterDataPoints =  dataset.parallelStream()
                                                       .map( dp -> new ClusterDataPoint(dp, ThreadLocalRandom.current().nextInt(k) ))
                                                       .collect( Collectors.toList() );
    return getKMeanCluster(clusterDataPoints, k);
  }
  
  
  private static Map<ClusterCentroid, List<ClusterDataPoint>>  getKMeanCluster(List<ClusterDataPoint> dataset, final int k )
  {
    // Map für die Cluster-Mittelpunkte
    // Cluster sind beginnend von 0 durchnummeriert
    Map<Integer, ClusterCentroid>  centroids = new HashMap<>();
    
    // Zuordnen der Punkte zu den Clustern
    Map<Integer, List<ClusterDataPoint>>  clusterMap = dataset.parallelStream()
                                                              .collect( Collectors.groupingBy( dp -> dp.getClusterId() ) );
  
    // Zähler für Clusterwechsler
    final LongAdder adder = new LongAdder();

    do
    { 
      // Zähler zurücksetzen
      adder.reset();

      clusterMap.entrySet().forEach( entry -> System.out.println( entry.getKey()+  " " + entry.getValue().size() ));
      
      //Berechne die Mittelpunkte der Cluster
      centroids.clear();
      centroids.putAll( 
             clusterMap.entrySet()
                       .parallelStream()
                       .collect( 
                           Collectors.toMap( entry -> entry.getKey(), 
                                             entry -> getCentroid( entry.getValue()) 
                                           )
                                )
                       );
          
      //Ordne jedem Punkte seinen neuen Cluster zu
      dataset.parallelStream()
             .forEach( dp -> { int id = getNearestCluster(dp, centroids);
                               if( dp.getClusterId() != id )
                               {
                                 dp.setClusterId( id );
                                 adder.increment();
                               }; 
                             } );
      
      // Neuzuordnen der Punkte zu den Clustern
      clusterMap = dataset.parallelStream()
                          .collect( Collectors.groupingBy( dp -> dp.getClusterId() ) );
    }
    while( adder.sum() != 0 );
    
    return clusterMap.entrySet()
                     .parallelStream()
                     .collect( Collectors.toMap( me -> centroids.get(me.getKey()),
                                                                             me -> me.getValue() ));
  }
  
  
  private static ClusterCentroid getCentroid(List<ClusterDataPoint> list)
  {
    double[] identity = new double[2];
    double[] sum = list.parallelStream().reduce(identity, (a,dp) -> new double[]{a[0] + dp.x, a[1] + dp.y }, (p1,p2) -> new double[]{p1[0]+p2[0], p1[1]+p2[1]} );
    
    return new ClusterCentroid(sum[0]/list.size(), sum[1]/list.size());
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
