package tutorial.java.part1.assignment.permutation;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first = null;
    private Node<Item> last = null;
    private int size = 0;

    private class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> previous;
        public Node(Item item) {
            this.item = item;
        }
    }

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null && last == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null is not allowed to add ");
        }

        Node<Item> current = new Node<>(item);
        if (first != null) {
            current.next = first;
            first.previous = current;
        }
        first = current;
        if (last == null) {
            last = first;
        }
        size += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null is not allowed to add ");
        }
        Node<Item> current = new Node<>(item);
        if (last != null) {
            last.next = current;
            current.previous = last;
        }
        last = current;
        size += 1;

        if (first == null) {
            first = current;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("no element");
        }

        Node<Item> element = first;
        if (size == 1) {
            first = null;
            last = null;
        } else {
            first = element.next;
            if (first != null) {
                first.previous = null;
            }
        }
        size -= 1;
        return element.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("no element");
        }
        Node<Item> element = last;
        if (size == 1) {
            first = null;
            last = null;
        } else {
            last = element.previous;
            if (last != null) {
                last.next = null;
            }
        }
        size -= 1;

        return element.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node<Item> current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                if (current == null) {
                    throw new java.util.NoSuchElementException("no element");
                }
                Node<Item> temp = current;
                current = temp.next;
                return temp.item;
            }
        };
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(0);
        deque.addLast(3);
        deque.addLast(4);
        deque.addFirst(-1);
        deque.addFirst(-2);

        for (Integer item: deque) {
            System.out.printf("%d ", item);
        }
        System.out.println("");

        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();

        for (Integer item: deque) {
            System.out.printf("%d ", item);
        }
        System.out.println("");

        deque.addFirst(0);
        deque.removeLast();
        deque.removeLast();
        for (Integer item: deque) {
            System.out.printf("%d ", item);
        }
        System.out.println("");

        deque.addFirst(-1);
        deque.removeFirst();
        deque.addLast(3);
        deque.addLast(4);
        deque.addFirst(-1);
        deque.addLast(5);

        for (Integer item: deque) {
            System.out.printf("%d ", item);
        }
    }

}