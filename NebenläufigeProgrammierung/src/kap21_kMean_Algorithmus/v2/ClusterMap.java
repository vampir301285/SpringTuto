package kap21_kMean_Algorithmus.v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kap21_kMean_Algorithmus.datamodel.ClusterCentroid;
import kap21_kMean_Algorithmus.datamodel.ClusterDataPoint;


public class ClusterMap
{
  private final Map<Integer, List<ClusterDataPoint>> clusterMap;
  
  public ClusterMap()
  {
    this.clusterMap = new HashMap<>();
  }
  
  private ClusterMap(ClusterMap map)
  {
    this.clusterMap = new HashMap<>(map.clusterMap);
  }
  
  public void add(int key, ClusterDataPoint dp )
  {
    if( clusterMap.containsKey(key) )
    {
      clusterMap.get(key).add(dp);
    }
    else
    {
      List<ClusterDataPoint> dpList = new ArrayList<>();
      dpList.add(dp);
      clusterMap.put(key, dpList);
    }
  }
  
  public List<ClusterDataPoint> get(int key)
  {
    return clusterMap.getOrDefault(key, new ArrayList<>());
  }
  
  public ClusterMap combine(ClusterMap cmap)
  {
    ClusterMap result = new ClusterMap(this);

    for( int key : cmap.clusterMap.keySet() )
    {
      List<ClusterDataPoint> tmpList = cmap.clusterMap.get(key);
      result.clusterMap.merge(key, tmpList,  (l,v) -> {l.addAll(v); return l;} );
    }
    
    return result;
  }
  
  public ClusterCentroid getCentroid(int key)
  {
    double[] identity = new double[2];
    
    List<ClusterDataPoint> list = clusterMap.get(key);
    
    double[] sum = list.parallelStream()
                       .reduce(identity, 
                               (a,dp) -> new double[]{a[0] + dp.x, a[1] + dp.y }, 
                               (p1,p2) -> new double[]{p1[0]+p2[0], p1[1]+p2[1]} );
    
    return new ClusterCentroid(sum[0]/list.size(), sum[1]/list.size());
  }
  
  
  public Map<ClusterCentroid, List<ClusterDataPoint>> getCluster()
  {
    Map<ClusterCentroid, List<ClusterDataPoint>> resultMap = new HashMap<>();
    for( int key :  clusterMap.keySet() )
    {
      resultMap.put( getCentroid(key),  get(key));
    }
    return resultMap;
  }

}
