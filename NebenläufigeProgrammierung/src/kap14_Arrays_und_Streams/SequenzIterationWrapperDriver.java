package kap14_Arrays_und_Streams;

import java.util.Map;
import java.util.Random;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SequenzIterationWrapperDriver
{

  public static void main(String[] args)
  {
    String randStr = getRandomString(42);

    Map<String, Integer> mapping = getFrequencyMap(randStr);
    System.out.println(mapping);
  }

  public static Map<String, Integer> getFrequencyMap(String str)
  {
    SequenzIterationWrapper wrapper = new SequenzIterationWrapper(str, 5);
    Spliterator<String> spliterator = Spliterators.spliterator(
                           wrapper.iterator(), 
                           wrapper.size(), 
                           Spliterator.NONNULL + Spliterator.IMMUTABLE);
    
    return StreamSupport.stream(spliterator, true)
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.toList())).entrySet().parallelStream()
                        .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size()));
  }

  private static String[] tokens = { "XXXXX", "AAAAA", "BBBBB", "CCCCC", "DDDDD", "YYYYY" };
  private static Random rand = new Random();

  private static String getRandomString(int numOfChunks)
  {
    StringBuilder strBuilder = new StringBuilder();

    for (int i = 0; i < numOfChunks; i++)
    {
      int choice = rand.nextInt(tokens.length);
      strBuilder.append(tokens[choice]);
    }

    return strBuilder.toString();
  }
}
