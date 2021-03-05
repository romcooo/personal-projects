package com.romcooo.stacksandqueues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {
        int k = 0;

        if (args.length > 0) {
            k = Integer.parseInt(args[0]);
        } else {
            return;

        }

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        while (true) {
            String input = StdIn.readString();
            if (input.isEmpty()) {
                break;
            } else {
                randomizedQueue.enqueue(input);
            }
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(randomizedQueue.dequeue());
        }

    }
}
