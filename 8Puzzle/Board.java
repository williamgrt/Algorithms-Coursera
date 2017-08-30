import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


/**
 * Created by 55086 on 2017/8/27.
 */
public class Board {
    private int[][] puzzle;
    private final int dimension;
    private int hamming;
    private int manhattan;


    /**
     * Construct a board from an n-by-n array of blocks
     *
     * @param blocks a n-by-n array of blocks
     */
    public Board(int[][] blocks) {
        dimension = blocks.length;
        puzzle = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++)
                puzzle[i][j] = blocks[i][j];
        }
        hamming = -1;
        manhattan = -1;
    }

    /**
     * Get the dimension of this board
     *
     * @return board dimension n
     */
    public int dimension() {
        return dimension;
    }

    /**
     * Calculate the Hamming priority of this board
     *
     * @return number of blocks out of place
     */
    public int hamming() {
        if (hamming == -1) {
            hamming = 0;
            int total = dimension * dimension;
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    int trans = i * dimension + j;
                    if (puzzle[i][j] != 0 && puzzle[i][j] != (trans + 1) % total)
                        hamming++;
                }
            }
        }
        return hamming;
    }

    /**
     * Calculate the Manhattan distance of this board
     *
     * @return sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        if (manhattan == -1) {
            manhattan = 0;
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    if (puzzle[i][j] != 0) {
                        int vertical = 0;
                        int horizon = 0;
                        int currentPosition = i * dimension + j;
                        horizon = (currentPosition % dimension) - ((puzzle[i][j] - 1) % dimension);
                        vertical = (currentPosition - horizon - puzzle[i][j] + 1) / dimension;
                        manhattan = manhattan + Math.abs(horizon) + Math.abs(vertical);
                    }
                }
            }
        }
        return manhattan;
    }

    /**
     * Whether this board is the goal board
     *
     * @return If all numbers are in order, return true
     * Else return false
     */
    public boolean isGoal() {
        int total = dimension * dimension;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (puzzle[i][j] != (dimension * i + j + 1) % total)
                    return false;
            }
        }
        return true;
    }

    /**
     * Find a twin (a board that is obtained by exchanging any pair of blocks) of this board
     *
     * @return a twin of this board
     */
    public Board twin() {
        Board newTwin = new Board(this.puzzle);
        int x = -1, y = -1;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension - 1; j++) {
                if (newTwin.puzzle[i][j] != 0 && newTwin.puzzle[i][j + 1] != 0) {
                    if (x == -1 && y == -1) {
                        x = i;
                        y = j;
                        swap(newTwin.puzzle, i, j, i, j + 1);
                    }
                }
            }
        }
        return newTwin;
    }

    /**
     * Judge whether this board equal y?
     *
     * @param y the object waited for comparing
     * @return If this board doesn't equal y, return false,
     * Else return true
     */
    public boolean equals(Object y) {
        if (this == y)
            return true;
        if (y == null)
            return false;
        if (this.getClass() != y.getClass())
            return false;

        Board temp = (Board) y;
        if (temp.dimension() != dimension)
            return false;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (this.puzzle[i][j] != temp.puzzle[i][j])
                    return false;
            }
        }
        return true;
    }

    /**
     * Find all neighbours of this board
     *
     * @return all neighboring boards
     */
    public Iterable<Board> neighbors() {
        Queue<Board> allNeighbours = new Queue<>();
        int row = 0;
        int col = 0;

        //find the blank block
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (puzzle[i][j] == 0) {
                    row = i;
                    col = j;
                }
            }
        }

        if (row != 0) {
            Board newBoard = new Board(this.puzzle);
            swap(newBoard.puzzle, row, col, row - 1, col);
            allNeighbours.enqueue(newBoard);
        }
        if (row != dimension - 1) {
            Board newBoard = new Board(this.puzzle);
            swap(newBoard.puzzle, row, col, row + 1, col);
            allNeighbours.enqueue(newBoard);
        }
        if (col != 0) {
            Board newBoard = new Board(this.puzzle);
            swap(newBoard.puzzle, row, col, row, col - 1);
            allNeighbours.enqueue(newBoard);
        }
        if (col != dimension - 1) {
            Board newBoard = new Board(this.puzzle);
            swap(newBoard.puzzle, row, col, row, col + 1);
            allNeighbours.enqueue(newBoard);
        }
        return allNeighbours;
    }

    private void swap(int[][] puzzle, int x1, int y1, int x2, int y2) {
        int temp = puzzle[x1][y1];
        puzzle[x1][y1] = puzzle[x2][y2];
        puzzle[x2][y2] = temp;
    }

    /**
     * @return string representation of this board (in the output format specified below)
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                builder.append(String.format("%2d ", this.puzzle[i][j]));
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
