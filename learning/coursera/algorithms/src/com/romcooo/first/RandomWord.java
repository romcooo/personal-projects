package com.romcooo.first;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = "";
        double counter = 0d;
        while (!StdIn.isEmpty()) {
            counter++;
            String nextWord = StdIn.readString();
            if (StdRandom.bernoulli(1d / counter)) {
                champion = nextWord;
            }
        }
        System.out.println(champion);
    }
}
