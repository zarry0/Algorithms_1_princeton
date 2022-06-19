import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation
{
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        while (!StdIn.isEmpty())
        {   String word = StdIn.readString();
            if (queue.size() <= k) queue.enqueue(word);
        }

        for (int i = 0; i < k; i++) StdOut.println(queue.dequeue());

    }
}