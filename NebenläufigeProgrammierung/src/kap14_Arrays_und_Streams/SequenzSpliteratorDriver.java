package kap14_Arrays_und_Streams;

import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SequenzSpliteratorDriver
{

  public static void main(String[] args)
  {
    String randStr = getRandomString(42);

    SequenzSpliterator spliterator = new SequenzSpliterator(randStr,5);
    
    Map<String, Integer> mapping = StreamSupport.stream(spliterator, true)
        .collect( Collectors.groupingBy( Function.identity(), Collectors.toList()  ) )
        .entrySet().parallelStream()
        .collect( Collectors.toMap( Map.Entry::getKey, e -> e.getValue().size()));
    
    System.out.println( mapping );
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
