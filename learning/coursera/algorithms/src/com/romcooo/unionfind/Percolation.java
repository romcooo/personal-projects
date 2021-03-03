import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int TOP = 0;

    private final int n;
    private final boolean[] siteIsOpen;
    private final int bottom;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufBackwash;
    private int openedCount = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.n = n;

        siteIsOpen = new boolean[n*n + 1];
        uf = new WeightedQuickUnionUF(n*n + 2);

        bottom = n*n+1;

        ufBackwash = new WeightedQuickUnionUF(n*n + n/2 + 1 + 1);


        /* n * n + n + 1
         *    0
         *  1 2 3
         *  4 5 6
         *  7 8 9
         *  0 1 2
         */
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) throw new IllegalArgumentException();
        // if already opened, just return
        if (siteIsOpen[flatten(row, col)]) return;

        siteIsOpen[flatten(row, col)] = true;
        openedCount++;

        // check all neighbours: x-1, x+1, y-1, y+1
        // above
        if (row > 1 && siteIsOpen[flatten(row-1, col)]) {
            uf.union(flatten(row, col), flatten(row-1, col));
            ufBackwash.union(flatten(row, col), flatten(row-1, col));
        }
        // left
        if (col > 1 && siteIsOpen[flatten(row, col-1)]) {
            uf.union(flatten(row, col), flatten(row, col-1));
            ufBackwash.union(flatten(row, col), flatten(row, col-1));
        }
        // right
        if (col < n && siteIsOpen[flatten(row, col+1)]) {
            uf.union(flatten(row, col), flatten(row, col+1));
            ufBackwash.union(flatten(row, col), flatten(row, col+1));
        }
        // below
        if (row < n && siteIsOpen[flatten(row+1, col)]) {
            uf.union(flatten(row, col), flatten(row+1, col));
            ufBackwash.union(flatten(row, col), flatten(row+1, col));
        }

        if (row == 1) {
            uf.union(flatten(row, col), TOP);
            ufBackwash.union(flatten(row, col), TOP);
        }
        if (row == n) {
            uf.union(flatten(row, col), bottom);
            ufBackwash.union(flatten(row, col), flatten(row+1, col/2 + 1));
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
        return isOpen(row, col) && (ufBackwash.find(flatten(row, col)) == ufBackwash.find(TOP));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openedCount;
    }

    // does the system percolate?
    public boolean percolates() {
//        if (openedCount < n) return false;
//        int botrow = n * n + 1;
//        for (int b = botrow; b < (botrow + (n/2 + 1)); b++) {
//            if (uf.find(TOP) == uf.find(b)) {
//                return true;
//            }
//        }
//        return false;
        if (openedCount < n) return false;
        if (uf.find(TOP) == uf.find(bottom)) {
            return true;
        }
        return false;
    }

    private int flatten(int x, int y) {
        return (y + (x - 1) * n);
    }

//    private void printSite() {
//        for (int x = 1; x <= n; x++) {
//            for (int y = 1; y <= n; y++) {
//                System.out.print(siteIsOpen[flatten(x, y)] + ", ");
//            }
//            System.out.println();
//        }
//    }
//
//    public void printTree() {
//        for (int i = 0; i < ((n * n) + (n / 2) + (1 + 1)); i++) {
//            System.out.print(uf.find(i) + " ");
//        }
//        System.out.println();
//    }

    /**
     * test client
     * @param args
     */
    public static void main(String[] args) {
//        Percolation p = new Percolation(3);
//        p.printSite();
//        p.printTree();

//        System.out.println("opening 1, 1");
//        p.open(1, 1);
//        System.out.println("opening 2, 1");
//        p.open(2, 1);
//        System.out.println("opening 3, 1");
//        p.open(3, 1);
//        System.out.println("opening 3, 3");
//        p.open(3, 3);
//        p.printSite();
//        p.printTree();
//
//        System.out.print("percolates:");
//        System.out.println(p.percolates());
//        System.out.println("full:");
//        System.out.println(p.isFull(1, 1));
//        System.out.println(p.isFull(2, 1));
//        System.out.println(p.isFull(3, 1));
//        System.out.println(p.isFull(3, 3));
//
//        System.out.println("opening 3, 2 - then isFull(3, 3):");
//        p.open(3, 2);
//        System.out.println(p.isFull(3, 2));
//        System.out.println(p.isFull(3, 3));
//        p.printSite();
//        p.printTree();

        /**
         *      1
         *   1  2  3
         *   1  5  6
         *   1  8  9
         *   1  1 12
         */
    }
}

