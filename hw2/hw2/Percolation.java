package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    /** define necessary attribute */
    private int size = 0;
    private int dim;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufWithoutBottom;
    private boolean[][] openState;
    /** transform (x, y) into a single number */
    private int xyToNum(int x, int y, int N){
        return x * N + y + 1;
    }
    /** judge if the adjacent site is open and make union if possible*/
    private void unionNeighbour(int row, int col, int adjacentR, int adjacentC, int dim) {
        if ((adjacentR < dim && adjacentR >= 0) && (adjacentC < dim && adjacentC >= 0)) {
            if (isOpen(adjacentR, adjacentC)) {
                int idx = xyToNum(row, col, dim);
                int adjacentIdx = xyToNum(adjacentR, adjacentC, dim);
                uf.union(idx, adjacentIdx);
                ufWithoutBottom.union(idx, adjacentIdx);
            }
        }
    }

    /** constructor of the percolation*/
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("The value of N should be greater than zero");
        }
        dim = N;
        uf = new WeightedQuickUnionUF(N * N + 2); // consider two extra node representing top and bottom node.
        ufWithoutBottom = new WeightedQuickUnionUF(N * N + 2);
        openState = new boolean[N][N];
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                openState[i][j] = false;
            }
        }
    }

    /** implement open method */
    public void open(int row, int col) {
        if (row > dim || col > dim) {
            throw new java.lang.IllegalArgumentException("the index is going beyond the boundary");
        }
        if (! isOpen(row, col)) {
            openState[row][col] = true;
            int index = xyToNum(row, col, dim);
            if (row == 0) {
                uf.union(0, index);
                ufWithoutBottom.union(0, index);
//            System.out.println("the site is connected with water");
            } else if (row == dim - 1) {
                uf.union(dim * dim + 1, index);
            }
            unionNeighbour(row, col, row - 1, col, dim);
            unionNeighbour(row, col, row + 1, col, dim);
            unionNeighbour(row, col, row, col - 1, dim);
            unionNeighbour(row, col, row, col + 1, dim);
            size += 1;
        }

    }

    /** judge whether the (row, col) is open*/
    public boolean isOpen(int row, int col){
        return openState[row][col];
    }


    /** judge whether the (row, col) is full */
    public boolean isFull(int row, int col) {
        int index = xyToNum(row, col, dim);
        return ufWithoutBottom.connected(0, index);

    }

    /** count the number of open sites */
    public int numberOfOpenSites() {
        return size;
    }

    /** judge whether the system is percolate*/
    public boolean percolates() {
        return uf.connected(0, dim * dim + 1);
    }

    public static void main(String[] args) {
        Percolation per = new Percolation(10);
        per.open(0, 1);
        per.open(1, 1);
        System.out.println(per.isFull(1, 1));
        per.open(0, 2);
        per.open(0, 3);
//        System.out.println(per.numberOfOpenSites());
        System.out.println(per.isFull(0, 2));
        System.out.println(per.isOpen(0, 1));
        System.out.println(per.isOpen(0, 2));


    }

}
