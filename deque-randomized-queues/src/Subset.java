import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Subset {
  public static void main(String[] args) {
    RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
    int k = Integer.parseInt(args[0]);

    if (k > 0) {
      int count = 0;
      while (!StdIn.isEmpty()) {
        String item = StdIn.readString();
        if (++count <= k)
          randomizedQueue.enqueue(item);
        else if (StdRandom.bernoulli((double) k / count)) {
          randomizedQueue.dequeue();
          randomizedQueue.enqueue(item);
        }          
      }

      for (String item : randomizedQueue)
        StdOut.println(item);
    }
  }
}
