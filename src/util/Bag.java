package util;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * 
 * Bag data structure
 * Based of the implementation in the Algorithms textbook
 *
 * @param <Item> - Data type of items to store inside bag
 */
public class Bag<Item> implements Iterable<Item> {
    private int N; // Number of items in bag
    private Node<Item> first; // First item in bag

    
    /**
     * 
     * Node helper class to store items
     *
     * @param <Item> - data type of items to store
     */
    private static class Node<Item> {
        private Item item;
        private Node<Item> next; // Pointer to next item in bag
    }

    /**
     * Initialises an empty bag
     */
    public Bag() {
        first = null;
        N = 0;
    }

    /**
     * Check if the bag is empty
     */
    public boolean isEmpty() {
        return first == null;
    }

    
    /**
     * Returns the number of items in the bag
     */
    public int size() {
        return N;
    }

    /**
     * Adds item into bag by making it the new first item
     * similar to a stack
     * @param item - Item to add into bag
     */
    public void add(Item item) {
        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldFirst;
        N++;
    }

    /**
     * Returns an iterator for the items in the bag
     */
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    /**
     * Helper class to return an iterable version of items in bag
     *
     * @param <Item> - type of items stored
     */
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
