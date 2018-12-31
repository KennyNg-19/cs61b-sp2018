package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int[] helperEdgeTo;
    private int endVertice = -1;
    private boolean hasCycle = false;


    public MazeCycles(Maze m) {
        super(m);
        s = maze.xyTo1D(1, 1);
        helperEdgeTo = new int[maze.V()];
        helperEdgeTo[s] = s;
    }

    @Override
    public void solve() {
        findCycle(s);
        if (endVertice == -1) {
            System.out.println("The graph doesn't have cycle");
        }
    }

    // Helper methods go here
    private void findCycle(int v) {
        marked[v] = true;
        announce();
        for (int i: maze.adj(v)) {
            if (!marked[i]) {
                helperEdgeTo[i] = v;
                findCycle(i);
            } else if (marked[i] && i != helperEdgeTo[v]) {
                edgeTo[i] = v;
                announce();
                endVertice = i;
                int lastVertice = edgeTo[endVertice];
                while (lastVertice != endVertice) {
                    edgeTo[lastVertice] = helperEdgeTo[lastVertice];
                    announce();
                    lastVertice = edgeTo[lastVertice];
                }
                hasCycle = true;
                return;
            } else {
                continue;
            }
            if (hasCycle) {
                return;
            }
        }
    }
}

