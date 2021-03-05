package com.romcooo.unionfind;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int TOP = 0;

    private final int n;
    private final boolean[] siteIsOpen;
    private final int bottom;
    private final WeightedQuickUnionUF uf;
//    private final WeightedQuickUnionUF ufBackwash;
    private int openedCount = 0;

    private int recursionCounter;
    private int lastRow;
    private int lastCol;

    private final boolean[] siteIsFull;
    private final boolean[] siteIsChecked;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.n = n;
        bottom = n*n+1;

        siteIsOpen = new boolean[n*n + 1];
        siteIsFull = new boolean[n*n + 1];
        siteIsChecked = new boolean[n*n + 1];

        uf = new WeightedQuickUnionUF(n*n + 2);
//        ufBackwash = new WeightedQuickUnionUF(n*n + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) throw new IllegalArgumentException();
        // if already opened, just return
        if (siteIsOpen[flatten(row, col)]) return;

        siteIsOpen[flatten(row, col)] = true;
        openedCount++;

        lastRow = row;
        lastCol = col;

        boolean floodHappened = false;
        // check all neighbours: x-1, x+1, y-1, y+1
        // above
        int current = flatten(row, col);
        int above = flatten(row - 1, col);
        if (row > 1 && siteIsOpen[above]) {
            if (isItAboutToPercolate(row, col)) {
                // about to percolate, loop through everything, then fill rest, then union
                floodBeforePercolation();
                floodHappened = true;
                while (!fill(lastRow, lastCol)) {
                    recursionCounter = 0;
                }
            }
            uf.union(current, above);
        }
        // left
        int left = flatten(row, col - 1);
        if (col > 1 && siteIsOpen[left]) {
            if (isItAboutToPercolate(row, col)) {
                if (!floodHappened) {
                    floodBeforePercolation();
                    floodHappened = true;
                }
                while (!fill(lastRow, lastCol)) {
                    recursionCounter = 0;
                }
            }
            uf.union(current, left);
        }
        // right
        int right = flatten(row, col+1);
        if (col < n && siteIsOpen[right]) {
            if (isItAboutToPercolate(row, col)) {
                if (!floodHappened) {
                    floodBeforePercolation();
                    floodHappened = true;
                }
                while (!fill(lastRow, lastCol)) {
                    recursionCounter = 0;
                }
            }
            uf.union(current, right);
        }
        // below
        int below = flatten(row+1, col);
        if (row < n && siteIsOpen[below]) {
            if (isItAboutToPercolate(row, col)) {
                if (!floodHappened) {
                    floodBeforePercolation();
                    floodHappened = true;
                }
                while (!fill(lastRow, lastCol)) {
                    recursionCounter = 0;
                }
            }
            uf.union(current, below);
        }

        // TODO this won't work properly yet
        if (row == 1) {
            uf.union(current, TOP);
            if (!percolates()) {
                siteIsFull[current] = true;
            } else {
                System.out.println("percolates: " + percolates() + ", sIF[c] = " + siteIsFull[current]);
                if (!siteIsFull[current]) {
                    while (!fill(lastRow, lastCol)) {
//                System.out.println("row == 1 while code block triggered");
                        recursionCounter = 0;
                    }
                }
            }
        }
        if (row == n) {
            uf.union(current, bottom);
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) throw new IllegalArgumentException();
        return siteIsOpen[flatten(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) throw new IllegalArgumentException();
        return siteIsFull[flatten(row, col)];
//        return isOpen(row, col) && (ufBackwash.find(flatten(row, col)) == ufBackwash.find(TOP));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openedCount;
    }

    // does the system percolate?
    public boolean percolates() {
        if (openedCount < n) return false;
        if (uf.find(TOP) == uf.find(bottom)) {
            return true;
        }
        return false;
    }

    private int flatten(int x, int y) {
        return (y + (x - 1) * n);
    }

    private boolean fill(int row, int col) {
        if (recursionCounter >= n * n / 4) {
            System.out.println("counter = " + recursionCounter);
            lastRow = row;
            lastCol = col;
            return false;
        }
        recursionCounter++;
        boolean isComplete = true;

        System.out.println("filling row " + row + ", col " + col);

        int current = flatten(row, col);
        siteIsChecked[current] = true;

        siteIsFull[current] = true;
        int above = flatten(row - 1, col);
        int left = flatten(row, col - 1);
        int right =  flatten(row, col + 1);
        int below = flatten(row + 1, col);

//        if (row > 1 && siteIsOpen[above] && !siteIsFull[above]) {
//            fill(row - 1, col);
//        }
//        if (col > 1 && siteIsOpen[left] && !siteIsFull[left]) {
//            fill(row, col -1);
//        }
//        if (col < n && siteIsOpen[right] && !siteIsFull[right]) {
//            fill(row, col + 1);
//        }
//        if (row < n && siteIsOpen[below] && !siteIsFull[below]) {
//            fill(row + 1, col);
//        }

        if (row > 1 && siteIsOpen[above] && !siteIsChecked[above]) {
            if (!fill(row - 1, col)) {
                siteIsChecked[current] = false;
                return false;
            }
        }
        if (col > 1 && siteIsOpen[left] && !siteIsChecked[left]) {
            if (!fill(row, col -1)) {
                siteIsChecked[current] = false;
                return false;
            }
        }
        if (col < n && siteIsOpen[right] && !siteIsChecked[right]) {
            if (!fill(row, col + 1)) {
                siteIsChecked[current] = false;
                return false;
            }
        }
        if (row < n && siteIsOpen[below] && !siteIsChecked[below]) {
            if (!fill(row + 1, col)) {
                siteIsChecked[current] = false;
                return false;
            }
        }
        return true;
//        if (isComplete) {
//            siteIsChecked[current] = true;
//            System.out.println("completed filling and checking row " + row + ", col " + col);
//        } else {
//            System.out.println("false: row " + row + ", col " + col);
//        }
//        return isComplete;
    }

    private boolean isAtLeastOneNeighbourFull(int row, int col) {
        int current = flatten(row, col);
        int above = flatten(row - 1, col);
        int left = flatten(row, col - 1);
        int right =  flatten(row, col + 1);
        int below = flatten(row + 1, col);

        return  (row > 1 && siteIsFull[above])
                || (col > 1 && siteIsFull[left])
                || (col < n && siteIsFull[right])
                || (row < n && siteIsFull[below]);
    }

    private boolean isAtLeastOneNeighbourConnectedToTop(int row, int col) {
        int above = flatten(row - 1, col);
        int left = flatten(row, col - 1);
        int right =  flatten(row, col + 1);
        int below = flatten(row + 1, col);
        return  (row > 1 && uf.find(above) == uf.find(TOP))
                || (col > 1 && uf.find(left) == uf.find(TOP))
                || (col < n && uf.find(right) == uf.find(TOP))
                || (row < n && uf.find(below) == uf.find(TOP));
    }

    private boolean isAtLeastOneNeighbourConnectedToBottom(int row, int col) {
        int above = flatten(row - 1, col);
        int left = flatten(row, col - 1);
        int right =  flatten(row, col + 1);
        int below = flatten(row + 1, col);
        return  (row > 1 && uf.find(above) == uf.find(bottom))
                || (col > 1 && uf.find(left) == uf.find(bottom))
                || (col < n && uf.find(right) == uf.find(bottom))
                || (row < n && uf.find(below) == uf.find(bottom));
    }

    private void floodBeforePercolation() {
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                int current = flatten(row, col);
                if (uf.find(current) == uf.find(TOP)) {
                    siteIsFull[current] = true;
                }
            }
        }
        System.out.println("flooded:");
        printIsFull();
    }

    private boolean isItAboutToPercolate(int row, int col) {
        return isAtLeastOneNeighbourConnectedToTop(row, col)
                && isAtLeastOneNeighbourConnectedToBottom(row, col);
    }

    public void printIsOpen() {
        System.out.println("printIsOpen:");
        for (int x = 1; x <= n; x++) {
            for (int y = 1; y <= n; y++) {
                System.out.print(siteIsOpen[flatten(x, y)] + ", ");
            }
            System.out.println();
        }
    }

    public void printTree() {
        for (int i = 0; i < ((n * n)  + 1); i++) {
            System.out.print(uf.find(i) + " ");
        }
        System.out.println();
    }

    public void printIsFull() {
        System.out.println("printIsFull:");
        for (int x = 1; x <= n; x++) {
            for (int y = 1; y <= n; y++) {
                System.out.print(siteIsFull[flatten(x, y)] + ", ");
            }
            System.out.println();
        }
    }

    /**
     * test client
     * @param args
     */
    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.printIsOpen();
        p.printTree();

        System.out.println("opening 1, 1");
        p.open(1, 1);
        System.out.println("opening 2, 1");
        p.open(2, 1);
        System.out.println("opening 3, 1");
        p.open(3, 1);
        System.out.println("opening 3, 3");
        p.open(3, 3);
        p.printIsOpen();
        p.printTree();

        System.out.print("percolates:");
        System.out.println(p.percolates());
        System.out.println("full:");
        p.printIsFull();

        System.out.println("opening 3, 2 - then isFull(3, 3):");
        p.open(3, 2);
        p.printIsOpen();
        p.printTree();
        p.printIsFull();

        /**
         *      1
         *   1  2  3
         *   1  5  6
         *   1  8  9
         *   1  1 12
         */
    }
}

