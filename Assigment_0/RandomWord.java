import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord
{
    public static void main(String[] args) {
       
        int i = 0;
        String champion = "";
        while (!StdIn.isEmpty())
        {
            String word = StdIn.readString();
            i++;
            boolean isChampion = StdRandom.bernoulli(1.0/i);
            if (isChampion) champion = word;
        }
        StdOut.println(champion);
    }
}