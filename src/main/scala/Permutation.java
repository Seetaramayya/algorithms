import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("unexpected input");
        }
        int k = Integer.parseInt(args[0]);
        if ( k > 0) {
            RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
            int totalElements = 0;
            while (!StdIn.isEmpty()) {
                if (totalElements >= k) {
                    randomizedQueue.dequeue();
                }
                randomizedQueue.enqueue(StdIn.readString());
                totalElements += 1;
            }

            for (int i = 0; i < k; i++) {
                System.out.println(randomizedQueue.dequeue());
            }
        }

    }
}