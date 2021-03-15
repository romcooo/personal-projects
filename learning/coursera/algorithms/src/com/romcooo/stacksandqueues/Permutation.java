// package com.romcooo.stacksandqueues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {

    public static void main(String[] args) {
        int k;

        if (args.length > 0) {
            k = Integer.parseInt(args[0]);
        } else {
            return;
        }

        if (k < 1) {
            return;
        }
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        int counter = 0;
        while (!StdIn.isEmpty()) {
            counter++;
            String in = StdIn.readString();
            if (counter > k) {
                if (StdRandom.bernoulli(1d - (((double) counter - (double) k) / (double) counter))) {
                    randomizedQueue.dequeue();
                    randomizedQueue.enqueue(in);
                }
            } else {
                randomizedQueue.enqueue(in);
            }
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(randomizedQueue.dequeue());
        }

    }
}
