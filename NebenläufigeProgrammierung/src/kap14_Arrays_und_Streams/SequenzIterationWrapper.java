package kap14_Arrays_und_Streams;

/**
 * Codebeispiel : Benutzerdenierter Iterator
 */

import java.util.Iterator;

public class SequenzIterationWrapper implements Iterable<String>
{
  private final String str;
  private final int chunkSize;
  
  private class ChunkIterator implements Iterator<String>
  {
    private int pos = 0;
    
    @Override
    public boolean hasNext()
    {
      return this.pos <= str.length()- chunkSize;
    }

    @Override
    public String next()
    { 
      String chunk = str.substring(pos, pos+chunkSize);
      pos += chunkSize;
      return chunk;
    }
  }
  
  public SequenzIterationWrapper(String str, int chunkSize)
  {
    assert str.length()%chunkSize == 0;
    
    this.str = str;
    this.chunkSize = chunkSize;
  }
  
  public int size()
  {
    return this.str.length()/this.chunkSize;
  }

  @Override
  public Iterator<String> iterator()
  {
    return new ChunkIterator();
  }
}
