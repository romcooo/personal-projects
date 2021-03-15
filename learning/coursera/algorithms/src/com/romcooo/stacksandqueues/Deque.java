// package com.romcooo.stacksandqueues;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int size = 0;
    private Node first;
    private Node last;

    // construct an empty deque
    public Deque() {

    }

    private class Node {
        Item item;
        Node next = null;
        Node previous = null;

        public Node(Item current) {
            this.item = current;
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (isEmpty()) {
            this.first = new Node(item);
            this.last = this.first;
        } else {
            Node oldFirst = this.first;
            this.first = new Node(item);
            this.first.next = oldFirst;
            oldFirst.previous = this.first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (isEmpty()) {
            this.first = new Node(item);
            this.last = this.first;
        } else {
            Node oldLast = this.last;
            this.last = new Node(item);
            oldLast.next = this.last;
            this.last.previous = oldLast;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        size--;
        Node oldFirst = this.first;
        this.first = oldFirst.next;
        if (this.first != null) {
            this.first.previous = null;
        } else {
            this.last = null;
        }
        return oldFirst.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        size--;
        Node oldLast = this.last;
        this.last = oldLast.previous;
        if (this.last != null) {
            this.last.next = null;
        } else {
            this.first = null;
        }
        return oldLast.item;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private void printDeque() {
        StdOut.println("Size: " + this.size() + ", isEmpty(): " + this.isEmpty());
        for (Item s : this) {
            StdOut.println(s.toString());
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> testDeque = new Deque<>();
        testDeque.addFirst("1");
        testDeque.addFirst("0");
        testDeque.addLast("2");
        testDeque.addLast("3");
        testDeque.printDeque(); // 1 2 3 4

        testDeque.removeFirst();
        testDeque.removeLast();
        testDeque.printDeque(); // 1 2

        testDeque.removeFirst();
        testDeque.removeFirst();
        testDeque.printDeque(); // empty
    }
}
