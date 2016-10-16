import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

  private Node first;
  private Node last;
  private int size;

  public Deque() {
    this.first = null;
    this.last = null;
    this.size = 0;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  /**
   * Add item to the front of the deque
   * @param item
   * @throws NullPointerException if item is null
   */
  public void addFirst(Item item) {
    validateItem(item);

    if (isEmpty()) {
      first = new Node(item);
      last = first;
    }
    else {
      Node oldFirst = first;
      first = new Node(item);
      first.next = oldFirst;
      oldFirst.prev = first;
    }

    size++;
  }

  /**
   * Add item to the end of the deque
   * @param item
   * @throws NullPointerException if item is null
   */
  public void addLast(Item item) {
    validateItem(item);

    if (isEmpty()) {
      last = new Node(item);
      first = last;
    }
    else {
      Node oldLast = last;
      last = new Node(item);
      last.prev = oldLast;
      oldLast.next = last;
    }

    size++;
  }

  /**
   * Remove and return item from the front of the deque
   * @return
   * @throws NoSuchElementException of deque is empty
   */
  public Item removeFirst() {
    if (isEmpty())
      throw new NoSuchElementException("Deque is empty");

    Node node = first;
    if (size() == 1) {
      first = null;
      last = null;
    }
    else {
      first = first.next;
      first.prev = null;
    }

    node.next = null;
    size--;
    return node.item;
  }

  /**
   * Remove and return item from the end of the deque
   * @return
   * @throws NoSuchElementException of deque is empty
   */
  public Item removeLast() {
    if (isEmpty())
      throw new NoSuchElementException("Deque is empty");

    Node node = last;
    if (size() == 1) {
      first = null;
      last = null;
    }
    else {
      last = last.prev;
      last.next = null;
    }

    node.prev = null;
    size--;
    return node.item;
  }

  /**
   * Return an iterator over items in order from front to end
   */
  public Iterator<Item> iterator() {
    return new DequeIterator();
  }

  private void validateItem(Item item) {
    if (item == null)
      throw new NullPointerException("Item cannot be null");
  }

  private class Node {
    private Item item;
    private Node prev;
    private Node next;

    public Node(Item item) {
      this.item = item;
      this.prev = null;
      this.next = null;
    }
  }

  private class DequeIterator implements Iterator<Item> {
    private Node current;

    public DequeIterator() {
      this.current = first;
    }

    public boolean hasNext() {
      return current != null;
    }

    public void remove() {
      throw new UnsupportedOperationException("Remove is not allowed");
    }

    public Item next() {
      if (!this.hasNext())
        throw new NoSuchElementException("No more items to return");

      Node node = current;
      current = current.next;
      return node.item;
    }
  }
}