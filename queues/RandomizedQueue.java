import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size, capacity;
    private Item[] arr;

    // construct an empty randomized queue
    public RandomizedQueue() {
        capacity = 10;
        size = 0;
        arr = (Item[]) new Object[capacity];
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
        checkValidAddition(item);
        arr[size++] = item;
    }

    private void checkValidAddition(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (size == capacity)
            resizeArr(capacity * 2);
    }

    private void resizeArr(int newCapacity) {
        Item[] newArr = (Item[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newArr[i] = arr[i];
        }
        arr = newArr;
        capacity = newCapacity;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int idx = StdRandom.uniformInt(0, size);
        Item temp = arr[idx];
        arr[idx] = arr[size - 1];
        arr[size - 1] = null;
        size--;
        if (size > 0 && size <= capacity / 4)
            resizeArr(capacity / 2);
        return temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int idx = StdRandom.uniformInt(0, size);
        return arr[idx];
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current = 0;
        private final Item[] shuffledQueue;

        public RandomizedQueueIterator() {
            shuffledQueue = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                shuffledQueue[i] = arr[i];
            }
            shuffle(shuffledQueue);
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (current >= size)
                throw new NoSuchElementException();
            Item item = shuffledQueue[current++];
            return item;
        }

        private void shuffle(Item[] array) {
            for (int i = size - 1; i > 0; i--) {
                int idx = StdRandom.uniformInt(i + 1);
                Item temp = array[idx];
                array[idx] = array[i];
                array[i] = temp;
            }
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);

        StdOut.println("Sample: " + rq.sample());
        StdOut.println("Dequeue: " + rq.dequeue());

        Iterator<Integer> iterator = rq.iterator();
        while (iterator.hasNext()) {
            StdOut.println(iterator.next());
        }
    }

}
