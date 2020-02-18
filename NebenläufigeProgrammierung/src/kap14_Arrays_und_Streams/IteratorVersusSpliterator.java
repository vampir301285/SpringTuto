package kap14_Arrays_und_Streams;

/**
 * Codebeispiel : Iteration über eine Collection
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;

public class IteratorVersusSpliterator
{

  public static void main(String[] args)
  {
    List<String> list = new ArrayList<String>();
    for( int i=0; i<10; i++)
    {
      list.add("Hallo " + i );
    }
    
    
    // Klassische Iteration
    Iterator<String> itr = list.iterator();
    while( itr.hasNext() )
    {
      System.out.println( itr.next() );
    }
    
    // bzw.
    for( String str : list )
    {
      System.out.println( str );
    }
    
    
    // Iteration mit einem Spliterator
    Spliterator<String> spliterator = list.spliterator();
    while( spliterator.tryAdvance( System.out::println ) ){;}


  }

}
