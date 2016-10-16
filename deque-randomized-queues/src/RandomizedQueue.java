import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

  private static final int INITIAL_SIZE = 10;
  private static final double FILL_THRESHOLD = 0.25;

  private int size;
  private Item[] items;

  @SuppressWarnings("unchecked")
  public RandomizedQueue() {
    this.size = 0;
    this.items = (Item[]) new Object[INITIAL_SIZE];
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public void enqueue(Item item) {
    validateItem(item);

    if (size == items.length)
      items = resize(items.length * 2);
    items[size++] = item;
  }

  public Item dequeue() {
    if (isEmpty())
      throw new NoSuchElementException("Queue is empty");

    int random = getRandomIndex();
    Item item = items[random];

    // Swap random item with last item, then remove it
    if (random != size - 1)
      items[random] = items[size - 1];
    items[size - 1] = null;
    size--;

    if (shouldShrinkArray())
      items = resize(items.length / 2 + 1);

    return item;
  }

  public Item sample() {
    if (isEmpty())
      throw new NoSuchElementException("Queue is empty");

    return this.items[getRandomIndex()];
  }

  public Iterator<Item> iterator() {
    return new RandomizedQueueIterator();
  }

  private void validateItem(Item item) {
    if (item == null)
      throw new NullPointerException("Item cannot be null");
  }

  private Item[] resize(int capacity) {
    @SuppressWarnings("unchecked")
    Item[] copy = (Item[]) new Object[capacity];
    for (int i = 0; i < size; i++)
      copy[i] = items[i];
    return copy;
  }

  private int getRandomIndex() {
    return StdRandom.uniform(size);
  }

  private boolean shouldShrinkArray() {
    return (double) size / items.length <= FILL_THRESHOLD;
  }

  private class RandomizedQueueIterator implements Iterator<Item> {
    private int iteratorSize;
    private Item[] iteratorItems;

    public RandomizedQueueIterator() {
      iteratorSize = size;
      iteratorItems = resize(size);
    }

    public boolean hasNext() {
      return iteratorSize > 0;
    }

    public void remove() {
      throw new UnsupportedOperationException("Remove is not allowed");
    }

    public Item next() {
      if (!this.hasNext())
        throw new NoSuchElementException();

      int random = StdRandom.uniform(iteratorSize);
      Item item = iteratorItems[random];

      // Swap random item with last item, then remove it
      if (random != iteratorSize - 1)
        iteratorItems[random] = iteratorItems[iteratorSize - 1];
      iteratorItems[iteratorSize - 1] = null;
      iteratorSize--;

      return item;
    }
  }
}