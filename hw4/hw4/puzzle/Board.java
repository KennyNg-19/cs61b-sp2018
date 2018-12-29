package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private final int[][] board;
    private final int[][] goal;

    public Board(int[][] tiles) {

        int s = tiles[0].length;
        goal = new int[s][s];
        board = new int[s][s];
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                if (tiles[i][j] > s * s - 1 || tiles[i][j] < 0) {
                    throw new IndexOutOfBoundsException("The index is out of bound");
                }
                board[i][j] = tiles[i][j];
                goal[i][j] = i * s + (j + 1);

            }
        }
        goal[s - 1][s - 1] = 0;
    }
    public int tileAt(int i, int j) {
        return board[i][j];
    }

    public int size() {
        return board[0].length;
    }

    /**
     * Returns neighbors of this board.
     * SPOILERZ: This is the answer.
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int d = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (board[i][j] != 0 && board[i][j] != goal[i][j]) {
                    d += 1;
                }
            }
        }
        return d;
    }

    public int manhattan() {
        int d = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j ++) {
                if (board[i][j] != 0) {
                    int expectedR = (board[i][j] - 1) / size();
                    int expectedC = (board[i][j] - 1) % size();
                    d += (Math.abs(expectedR - i) + Math.abs(expectedC - j));
                }
            }
        }
        return d;
    }

    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || this.getClass() != y.getClass()) {
            return false;
        }

        Board board1 = (Board) y;
        if (size() !=  board1.size()) {return false;}
        if (board != null && board1.board != null) {
            for (int i = 0; i < size(); i++) {
                for (int j = 0; j < size(); j++) {
                    if (board[i][j] != board1.board[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        } else {return false;}
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    @Override
    public int hashCode() {
        int result = board != null ? board.hashCode() : 0;
        result = result * 31 + goal.hashCode();
        return result;
    }
}
