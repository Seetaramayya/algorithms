package tutorial.java.part1.assignment.permutation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

/**
 * (Page: 200 Memory in the book)
 * <b>Memory calculation in the case of int:</b>
 *
 * <ul>
 *  <ol>
 *   Outer class or items memory hold ( if there are N elements then array capacity is worst case for ints : 4 * ( 4 * N) = 16N
 *      object overhead + array overhead + number of elements + primitive type + padding
 *      16              + 24             + 16N                + 4              + 4
 *      ~ 16N + 48
 *  </ol>
 *  <ol>
 *   Iterator memory foot print
 *      object overhead + array overhead + number of elements + primitive type + padding + inner anonymous class reference
 *      16              + 24             + 4N                 + 4              + 4       + 8
 *      ~ 4N + 56
 *  </ol>
 * </ul>
 * Total Memory = 20N + 104 (is it correct?) <br />
 *
 * <p>
 *  Performance requirements.  Your randomized queue implementation must support each randomized queue operation
 *  (besides creating an iterator) in constant amortized time. That is, any intermixed sequence of m randomized
 *  queue operations (starting from an empty queue) must take at most cm steps in the worst case, for some constant c.
 *  A randomized queue containing n items must use at most 48n + 192 bytes of memory. Additionally, your iterator
 *  implementation must support operations next() and hasNext() in constant worst-case time; and construction in
 *  linear time; you may (and will need to) use a linear amount of extra memory per iterator.
 * </p>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.items = (Item[]) new Object[16];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null is not allowed to add ");
        }
        increaseCapacityIfRequired();
        int index = nullRandomIndex(items);
        items[index] = item;
        size += 1;
    }

    private void increaseCapacityIfRequired() {
        if (size >= items.length / 2) {
            Item[] temp = items;
            items = (Item[]) new Object[items.length * 2];
            int counter = 0;
            for (Item item : temp) {
                if (item != null) {
                    items[counter++] = item;
                }
            }
        }
    }

    private void decreaseCapacityIfRequired() {
        if (size <= items.length / 4) {
            Item[] temp = items;
            items = (Item[]) new Object[items.length / 2];
            int counter = 0;
            for (Item item : temp) {
                if (item != null) {
                    items[counter++] = item;
                }
            }
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("empty");
        }
        int index = nonNullRandomIndex(items);
        Item item = items[index];
        items[index] = null;
        decreaseCapacityIfRequired();
        size -= 1;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("empty");
        }
        int index = nonNullRandomIndex(items);
        return items[index];
    }

    private int nonNullRandomIndex(Item[] array) {
        int temp = random(array.length);
        while (array[temp] == null) {
            temp = random(array.length);
        }
        return temp;
    }

    private int nullRandomIndex(Item[] array) {
        int temp = random(array.length);
        while (array[temp] != null) {
            temp = random(array.length);
        }
        return temp;
    }

    private int random(int max) {
        int i = StdRandom.uniform(max);
        return i == max ? i - 1 : i;
    }

    private class RandomizedIterator implements Iterator<Item> {
        private int current = 0;
        private final Item[] elements;

        public RandomizedIterator() {
            this.elements = (Item[]) new Object[size];
            fillItems();
        }

        private void fillItems() {
            for (Item item : RandomizedQueue.this.items) {
                if (item != null) {
                    int index = nullRandomIndex(elements);
                    elements[index] = item;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public Item next() {
            if (current >= size) {
                throw new java.util.NoSuchElementException("no element");
            }
            return elements[current++];
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
    }

}