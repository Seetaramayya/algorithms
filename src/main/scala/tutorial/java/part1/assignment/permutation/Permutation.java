package tutorial.java.part1.assignment.permutation;

import edu.princeton.cs.algs4.StdIn;

/**
 * Problem description is in https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php
 */
public class Permutation {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("unexpected input");
        }
        int k = Integer.parseInt(args[0]);
        // TODO: if the number of elements to keep in memory is k (instead of n) then randomness is not uniform.
        if (k > 0) {
            RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
            int totalElements = 0;
            while (!StdIn.isEmpty()) {
                randomizedQueue.enqueue(StdIn.readString());
                totalElements += 1;
                if (totalElements > k) {
                    randomizedQueue.dequeue();
                }
            }

            for (String item: randomizedQueue) {
                System.out.println(item);
            }
        }

    }
}