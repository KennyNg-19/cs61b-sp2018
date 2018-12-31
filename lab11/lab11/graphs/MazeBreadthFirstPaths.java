package lab11.graphs;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;



    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        edgeTo[s] = s;
        distTo[s] = 0;

    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs(int v) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(v);
        marked[s] = true;
        announce();
        int ver;
        while (!queue.isEmpty()) {
            ver = queue.remove();
            for (int i: maze.adj(ver)) {
                if (!marked[i]) {
                    marked[i] = true;
                    edgeTo[i] = ver;
                    distTo[i] = distTo[ver] + 1;
                    announce();
                    if (marked[t]) {
                        return;
                    }
                    queue.add(i);
                }
            }
        }


    }


    @Override
    public void solve() {
         bfs(s);
    }
}

