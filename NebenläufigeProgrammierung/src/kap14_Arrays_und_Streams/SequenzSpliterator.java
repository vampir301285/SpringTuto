package kap14_Arrays_und_Streams;

/**
 * Codebeispiel : Benutzerdenierter Spliterator
 */

import java.util.Spliterator;
import java.util.function.Consumer;

public class SequenzSpliterator implements Spliterator<String>
{
  private final String str;
  private final int chunkSize;
  private int currentPos = 0;

  public SequenzSpliterator(String str, int chunkSize)
  {
    assert str.length() % chunkSize == 0;
    this.str = str;
    this.chunkSize = chunkSize;
  }

  @Override
  public boolean tryAdvance(Consumer<? super String> action)
  {
    if (currentPos >= str.length())
      return false;
    action.accept(str.substring(currentPos, currentPos + chunkSize));
    currentPos += chunkSize;
    return true;
  }

  @Override
  public Spliterator<String> trySplit()
  {
    int currentSize = str.length() - currentPos;
    if (currentSize < 10 * chunkSize)
      return null;
    int splitPos = currentSize / 2 + currentPos;
    splitPos += chunkSize - splitPos % chunkSize;
    Spliterator<String> spliterator = new SequenzSpliterator(str.substring(currentPos, splitPos), chunkSize);
    currentPos = splitPos;
    return spliterator;
  }

  @Override
  public long estimateSize()
  {
    return (str.length() - currentPos) / chunkSize;
  }

  @Override
  public int characteristics()
  {
    return SIZED + SUBSIZED + NONNULL + IMMUTABLE;
  }
}
