package com.romcooo.unionfind;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96d;

    private final int n;
    private final int trials;
    private final double[] threshold;
//    private final boolean[] siteWasOpened;
    private int openCount;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        this.n = n;
        this.trials = trials;
        threshold = new double[trials];
//        siteWasOpened = new boolean[n * n + 1];
        this.compute();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (stddev() * CONFIDENCE_95 / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (stddev() * CONFIDENCE_95 / Math.sqrt(trials));
    }

    private void compute() {
        for (int trial = 0; trial < trials; trial++) {
            openCount = 0;
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                openRandom(p);
            }
            threshold[trial] = (double) openCount / (n * n);
//            System.out.println("trial #" + trial + " took " + openCount + " openings to percolate.");
        }
    }

    private void openRandom(Percolation p) {
        while (true) {
            int randX = StdRandom.uniform(n) + 1;
            int randY = StdRandom.uniform(n) + 1;
            if (!p.isOpen(randX, randY)) {
                p.open(randX, randY);
                openCount++;
                break;
            }
        }

//        int numberOfClosed = (n * n - openCount);
//        int target = StdRandom.uniform(numberOfClosed);
//        int counter = 0;

//        for (int x = 1; x <= n; x++) {
//            for (int y = 1; y <= n; y++) {
//                if (!siteWasOpened[flatten(x, y)]) {
//                    if (target == counter) {
//                        p.open(x, y);
//                        siteWasOpened[flatten(x, y)] = true;
//                        openCount++;
//                        return;
//                    } else {
//                        counter++;
//                    }
//                }
//            }
//        }
    }

    private int flatten(int x, int y) {
        return (y + (x - 1) * n);
    }

    // test client (see below)
    public static void main(String[] args) {
        int gridSize;
        int numberOfTrials;
        if (args.length < 2) {
//            throw new IllegalArgumentException();
            gridSize = 20;
            numberOfTrials = 100000;
        } else {
            gridSize = Integer.parseInt(args[0]);
            numberOfTrials = Integer.parseInt(args[1]);
        }

        PercolationStats ps = new PercolationStats(gridSize, numberOfTrials);

        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }

}