package kap21_kMean_Algorithmus.v2;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import kap21_kMean_Algorithmus.datamodel.ClusterDataPoint;



public class ClusterMapCollector implements Collector<ClusterDataPoint, ClusterMap, ClusterMap>
{ 
  @Override
  public Supplier<ClusterMap> supplier()
  {
    return () -> new ClusterMap();
  }

  @Override
  public BiConsumer<ClusterMap, ClusterDataPoint> accumulator()
  {
    return (cmap, cdp) -> cmap.add( cdp.getClusterId(), cdp);
  }

  @Override
  public BinaryOperator<ClusterMap> combiner()
  {
    return (map1,map2) -> map1.combine(map2); 
  }

  @Override
  public Function<ClusterMap, ClusterMap> finisher()
  {
    return Function.identity();
  }

  @Override
  public Set<Collector.Characteristics> characteristics()
  {
    return Collections.unmodifiableSet(
        EnumSet.of( Characteristics.UNORDERED,
            Characteristics.IDENTITY_FINISH ));
  }
}
