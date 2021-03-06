package tutorial.java.part1.assignment.eightpuzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A* search. Now, we describe a solution to the 8-puzzle problem that illustrates a general artificial intelligence methodology
 * known as the A* search algorithm.
 * <p>
 * We define a search node of the game to be a board, the number of moves made to reach the board,
 * and the previous search node.
 * <p>
 * - First, insert the initial search node (the initial board, 0 moves, and a null previous search node) into a priority queue.
 * - Then, delete from the priority queue the search node with the minimum priority, and insert onto the
 * priority queue all neighboring search nodes (those that can be reached in one move from the dequeued search node).
 * Repeat this procedure until the search node dequeued corresponds to the goal board.
 * <p>
 * The efficacy of this approach hinges on the choice of priority function for a search node. We consider two priority functions:
 * <p>
 * The Hamming priority function is the Hamming distance of a board plus the number of moves made so far to get to the search node. Intuitively, a search node with a small number of tiles in the wrong position is close to the goal, and we prefer a search node if has been reached using a small number of moves.
 * The Manhattan priority function is the Manhattan distance of a board plus the number of moves made so far to get to the search node.
 * To solve the puzzle from a given search node on the priority queue, the total number of moves we need to make (including those already made) is at least its priority, using either the Hamming or Manhattan priority function. Why? Consequently, when the goal board is dequeued, we have discovered not only a sequence of moves from the initial board to the goal board, but one that makes the fewest moves. (Challenge for the mathematically inclined: prove this fact.)
 */
public class Solver {
    private final boolean isSolvable;
    private final int moves;
    private final Board[] path;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("should not be null");
        }
        MinPQ<SearchNode> priorityQueue = new MinPQ<>((o1, o2) -> o1.priority - o2.priority);
        MinPQ<SearchNode> twinnedPriorityQueue = new MinPQ<>((o1, o2) -> o1.priority - o2.priority);
        // initial + moves + previous = null
        priorityQueue.insert(new SearchNode(initial, 0, null));
        twinnedPriorityQueue.insert(new SearchNode(initial.twin(), 0, null));
        SearchNode lastSearchNode = findGoal(priorityQueue, twinnedPriorityQueue);
        isSolvable = lastSearchNode.board.isGoal();
        moves = isSolvable ? lastSearchNode.moves : -1;
        path = constructPath(lastSearchNode);
    }

    private SearchNode dequeueFromPQ(MinPQ<SearchNode> pq) {
        SearchNode dequeuedNode = null;
        if (!pq.isEmpty()) {
            dequeuedNode = pq.delMin();
            for (Board neighbour : dequeuedNode.board.neighbors()) {
                SearchNode tobeEnqueued = new SearchNode(neighbour, dequeuedNode.moves + 1, dequeuedNode);
                if (dequeuedNode.previous == null || !dequeuedNode.previous.board.equals(neighbour)) {
                    pq.insert(tobeEnqueued);
                }
            }
        }

        return dequeuedNode;
    }

    private SearchNode findGoal(MinPQ<SearchNode> priorityQueue, MinPQ<SearchNode> twinnedPriorityQueue) {
        SearchNode mainDequeuedNode;
        SearchNode twinnedDequeuedNode;
        do {
            mainDequeuedNode = dequeueFromPQ(priorityQueue);
            twinnedDequeuedNode = dequeueFromPQ(twinnedPriorityQueue);
        } while ((mainDequeuedNode != null && !mainDequeuedNode.board.isGoal()) && (twinnedDequeuedNode != null && !twinnedDequeuedNode.board.isGoal()));

        return mainDequeuedNode;
    }

    private Board[] constructPath(SearchNode node) {
        Board[] pathToInitial;
        if (isSolvable) {
            pathToInitial = new Board[node.moves + 1];
            int currentElementPosition = node.moves;
            while (node != null) {
                pathToInitial[currentElementPosition--] = node.board;
                node = node.previous;
            }
        } else {
            pathToInitial = null;
        }
        return pathToInitial;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable) {
            return new Iterable<Board>() {
                @Override
                public Iterator<Board> iterator() {
                    return new Iterator<Board>() {
                        private int cursor = 0;
                        @Override
                        public boolean hasNext() {
                            return cursor < path.length;
                        }
                        @Override
                        public Board next() {
                            if (cursor >= path.length) throw new NoSuchElementException("cursor is out of the index");
                            return path[cursor++];
                        }
                    };
                }
            };
        } else {
            return null;
        }
    }

    // test client (see below)
    public static void main(String[] args) {
// create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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
    }

    private static class SearchNode {
        private final Board board;
        private final int moves;
        private final int priority;
        private final SearchNode previous;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.priority = board.manhattan() + moves;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(builder);
            builder.append(" moves = ").append(moves);
            builder.append(" previous = ");
            if (previous == null) {
                builder.append(" null;");
            } else {
                builder.append(" non-null;");
            }
            return builder.toString();
        }
    }

}