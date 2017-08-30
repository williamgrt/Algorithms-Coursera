import edu.princeton.cs.algs4.*;

/**
 * Created by 55086 on 2017/8/27.
 */

public class Solver{
    private class SearchNode  implements Comparable<SearchNode>{
        private Board value;
        private int move;
        private SearchNode previous;
        private int priority;

        SearchNode(Board initial, SearchNode last) {
            this.value = initial;
            this.previous = last;
            if (this.previous == null)
                move = 0;
            else
                move = last.move + 1;
            priority = move + this.value.manhattan();
        }

        public int compareTo(SearchNode node) {
            if (this.priority < node.priority)
                return -1;
            if (this.priority > node.priority)
                return 1;
            return 0;
        }
    }

    private MinPQ<SearchNode> solver;
    private MinPQ<SearchNode> twinSolver;
    private SearchNode target = null;
    private boolean solvable = false;

    /**
     * Find the solution by using two synchronized A* searches.
     * If the initial board is unsolvable, the target solution node is null
     */
    private void findSolution() {
        while (!solver.isEmpty() && !twinSolver.isEmpty()) {
            SearchNode current = solver.delMin();
            SearchNode twinCurrent = twinSolver.delMin();

            if (current.value.isGoal()) {
                target = current;
                solvable = true;
                break;
            }
            if (twinCurrent.value.isGoal()) {
                solvable = false;
                break;
            }

            for (Board neighbor: current.value.neighbors()) {
                if (current.previous != null && current.previous.value.equals(neighbor))
                    continue;
                solver.insert(new SearchNode(neighbor, current));
            }
            for (Board neighbor: twinCurrent.value.neighbors()) {
                if (twinCurrent.previous != null && twinCurrent.previous.value.equals(neighbor))
                    continue;
                twinSolver.insert(new SearchNode(neighbor, twinCurrent));
            }
        }
    }

    /**
     * Find a solution to the initial board (using the A* algorithm)
     * @param initial a board for initializing the initial search node
     */
    public Solver(Board initial) {
        if (initial == null)
            throw new java.lang.IllegalArgumentException();
        solver = new MinPQ<>();
        twinSolver = new MinPQ<>();
        solver.insert(new SearchNode(initial, null));
        twinSolver.insert(new SearchNode(initial.twin(), null));
        findSolution();
    }

    /**
     * Is the initial board solvable?
     * @return
     */
    public boolean isSolvable() {
        return solvable;
    }

    /**
     * Min number of moves to solve initial board; -1 if unsolvable
     * @return
     */
    public int moves() {
        if (solvable) {
            return target.move;
        }
        return -1;
    }

    /**
     * sequence of boards in a shortest solution; null if unsolvable
     * @return
     */
    public Iterable<Board> solution() {
        if (solvable) {
            Stack<SearchNode> temp = new Stack<>();
            SearchNode curr = target;
            while (curr != null) {
                temp.push(curr);
                curr = curr.previous;
            }

            Queue<Board> result = new Queue<>();
            while (!temp.isEmpty())
                result.enqueue(temp.pop().value);
            return result;
        }
        return null;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
        StdOut.println(solver.solution());
        StdOut.println(solver.moves());
    }
}

