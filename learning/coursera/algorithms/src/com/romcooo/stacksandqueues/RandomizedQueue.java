// package com.romcooo.stacksandqueues;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
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
            throw new IllegalArgumentException();
        }
        if (size == items.length) {
            Item[] doubled = (Item[]) new Object[items.length * 2];
            System.arraycopy(items, 0, doubled, 0, items.length);
            items = doubled;
        }
        items[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        Item randomItem = getRandom(true);
        if (size < items.length / 4) {
            Item[] halved = (Item[]) new Object[items.length / 2];
            if (size >= 0) System.arraycopy(items, 0, halved, 0, size);
            items = halved;
        }

        return randomItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return getRandom(false);
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] iteratorItems;
        private int iteratorSize;

        RandomizedQueueIterator() {
            Item[] itemsCopy = (Item[]) new Object[items.length];
            System.arraycopy(items, 0, itemsCopy, 0, items.length);
            iteratorSize = size;
            iteratorItems = (Item[]) new Object[size];
            for (int i = 0; i < iteratorSize; i++) {
                iteratorItems[i] = dequeue();
            }
            items = itemsCopy;
            size = iteratorSize;
        }

        @Override
        public boolean hasNext() {
            return iteratorSize != 0;
        }

        @Override
        public Item next() {
            if (iteratorSize == 0) throw new NoSuchElementException();
            Item nextItem = iteratorItems[iteratorSize - 1];
            iteratorItems[iteratorSize - 1] = null;
            iteratorSize--;
            return nextItem;
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private Item getRandom(boolean removeItem) {
        Item randomItem = null;
        int i = StdRandom.uniform(0, size);
        randomItem = items[i];

        if (removeItem) {
            items[i] = items[size - 1];
            items[size - 1] = null;
            size--;
        }
        return randomItem;
    }


    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> testQ = new RandomizedQueue<>();
        for (int i = 0; i < 6; i++) {
            testQ.enqueue(i);
        }
        for (Integer i : testQ) {
            StdOut.println("iterating:" + i);
        }
        for (int i = 0; i < 5; i++) {
            StdOut.println("sample: " + testQ.sample());

        }
        while (!testQ.isEmpty()) {
            StdOut.println("dequeue: " + testQ.dequeue());

        }
        for (int i = 0; i < 10; i++) {
            testQ.enqueue(i);
        }
        for (int i = 0; i < 10; i++) {
            testQ.dequeue();
        }
    }

}
