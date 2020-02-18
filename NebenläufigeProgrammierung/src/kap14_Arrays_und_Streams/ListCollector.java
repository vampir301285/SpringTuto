package kap14_Arrays_und_Streams;

/**
 * Codebeispiel : Ein Collector für eine Liste
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ListCollector<T> implements Collector<T, List<T>, List<T>>
{
  @Override
  public Supplier<List<T>> supplier()
  {
    return () -> new ArrayList<>();
  }

  @Override
  public BiConsumer<List<T>, T> accumulator()
  {
    return (list, n) -> list.add(n);
  }

  @Override
  public BinaryOperator<List<T>> combiner()
  {
    return (left, right) -> {
      left.addAll(right);
      return left;
    };
  }

  @Override
  public Function<List<T>, List<T>> finisher()
  {
    return Function.identity();
  }

  @Override
  public Set<Characteristics> characteristics()
  {
    return Collections.unmodifiableSet(
          EnumSet.of(Characteristics.UNORDERED, 
                     Characteristics.IDENTITY_FINISH));
  }
}