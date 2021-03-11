package com.romcooo.stacksandqueues;

import com.romcooo.stacksandqueues.RandomizedQueue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {
        int k;

        if (args.length > 0) {
            k = Integer.parseInt(args[0]);
        } else {
            return;

        }

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        int counter = 0;
        while (!StdIn.isEmpty()) {
            if (counter >= k) {
                break;
            }
            counter++;
            randomizedQueue.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(randomizedQueue.dequeue());
        }

    }
}
