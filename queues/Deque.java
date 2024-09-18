import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Item[] arr;
    private int head, tail, size;
    private int capacity = 10;

    public Deque() {
        head = 0;
        size = 0;
        tail = 0;
        arr = (Item[]) new Object[capacity];
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        checkValidAddition(item);
        head = (head - 1 + capacity) % capacity;
        arr[head] = item;
        size++;
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
            newArr[i] = arr[(head + i) % capacity];
        }
        capacity = newCapacity;
        arr = newArr;
        head = 0;
        tail = size;
    }

    // add the item to the back
    public void addLast(Item item) {
        checkValidAddition(item);
        arr[tail] = item;
        tail = (tail + 1) % capacity;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        checkValidRemove();
        Item item = arr[head];
        arr[head] = null;
        head = (head + 1) % capacity;
        size--;
        if (size > 0 && size <= capacity / 4)
            resizeArr(capacity / 2);
        return item;
    }

    private void checkValidRemove() {
        if (isEmpty())
            throw new NoSuchElementException();
    }

    // remove and return the item from the back
    public Item removeLast() {
        checkValidRemove();
        tail = (tail - 1 + capacity) % capacity;
        Item item = arr[tail];
        arr[tail] = null;
        size--;
        if (size > 0 && size <= capacity / 4)
            resizeArr(capacity / 2);
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private int current;
        private int count;

        public DequeIterator() {
            current = head;
            count = 0;
        }

        public boolean hasNext() {
            return count < size;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = arr[current];
            current = (current + 1) % capacity;
            count++;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        deque.addFirst(1);
        deque.removeFirst();
        deque.addFirst(3);
        deque.removeFirst();
        deque.addFirst(5);
        deque.removeFirst();
        deque.addFirst(7);
        deque.removeFirst();
        deque.addFirst(9);
        deque.removeLast();

        StdOut.println(deque.isEmpty());
        StdOut.println(deque.size());
        deque.addFirst(10);
        deque.addFirst(9);
        deque.addFirst(8);
        deque.addFirst(7);
        deque.addFirst(6);
        deque.addFirst(5);

        Iterator<Integer> iterator = deque.iterator();
        while (iterator.hasNext()) {
            StdOut.println(iterator.next());
        }
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeLast());
        iterator = deque.iterator();
        while (iterator.hasNext()) {
            StdOut.println(iterator.next());
        }
    }

}
