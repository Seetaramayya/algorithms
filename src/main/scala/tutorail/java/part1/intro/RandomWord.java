package tutorail.java.part1.intro;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int count  = 0;
        String survived = null;
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            count += 1;
            boolean result = StdRandom.bernoulli(1D / count);
            if (result) {
                survived = input;
            }
        }

        StdOut.println(survived);
    }
}
