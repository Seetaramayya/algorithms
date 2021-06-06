import edu.princeton.cs.algs4.StdRandom;

import java.util.*;

public class Board {
    private final int rowSize;
    private final int columnSize;
    private final int totalElements;
    private final int[][] tiles;
    private int blankPositionRowIndex;
    private int blankPositionColumnIndex;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col) O(N)
    public Board(int[][] tiles) {
        rowSize = columnSize = tiles.length;
        totalElements = rowSize * columnSize;
        if (rowSize < 2 || rowSize >= 128)
            throw new IllegalArgumentException("length should be in between 2 (inclusive) and 128");

        this.tiles = new int[rowSize][columnSize];
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                int tileValue = tiles[i][j];
                if (tileValue == 0) {
                    blankPositionColumnIndex = j;
                    blankPositionRowIndex = i;
                }
                this.tiles[i][j] = tileValue;
            }
        }
    }

    // O(1)
    private void swap(int[][] a, int i1, int j1, int i2, int j2) {
        int temp = a[i1][j1];
        a[i1][j1] = a[i2][j2];
        a[i2][j2] = temp;
    }

    // string representation of this board O(N)
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(rowSize).append("\n");
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                builder.append(String.format("%2d", tiles[i][j]));
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    // board dimension n O(1)
    public int dimension() {
        return rowSize;
    }

    // number of tiles out of place O(N)
    public int hamming() {
        int distance = 0;

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                int expectedTile = (rowSize * i) + j + 1;
                if (expectedTile == totalElements) break;
                if (tiles[i][j] != expectedTile) {
                    distance += 1;
                }
            }
        }
        return distance;
    }

    // sum of Manhattan distances between tiles and goal O(N)
    public int manhattan() {
        int distance = 0;

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                int expectedTile = (rowSize * i) + j + 1;
                if (expectedTile == totalElements) break;
                int currentTile = tiles[i][j];
                if (currentTile != expectedTile) {
                    int x1 = getRowPosition(currentTile);
                    int y1 = getColumnPosition(currentTile);
                    int x2 = getRowPosition(expectedTile);
                    int y2 = getColumnPosition(expectedTile);
                    int d = Math.abs(x1 - x2) + Math.abs(y1 - y2);
                    distance += d;
                }
            }
        }
        return distance;
    }

    private int getRowPosition(int tileValue) {
        if (tileValue == 0) return rowSize - 1;
        else {
            return (tileValue - 1) / rowSize;
        }
    }

    private int getColumnPosition(int tileValue) {
        if (tileValue == 0) return columnSize - 1;
        else {
            return (tileValue - 1) % columnSize;
        }
    }

    // is this board the goal board?
    public boolean isGoal() {
        boolean result = tiles[rowSize - 1][columnSize - 1] == 0;
        if (result) {
            for (int i = 0; i < totalElements - 1; i ++) {
                int row = i / rowSize;
                int col = i % columnSize;
                if (tiles[row][col] != (i+1)) {
                    return false;
                }
            }
        }

        return result;
    }

    // does this board equal y? O(N)
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        Board board = (Board) that;
        return Arrays.deepEquals(tiles, board.tiles) && this.rowSize == board.rowSize && this.columnSize == board.columnSize;
    }

    public int hashCode() {
        return Arrays.deepHashCode(tiles) + Objects.hash(rowSize, columnSize, totalElements);
    }

    // O(N)
    private int[][] copyTiles() {
        int[][] result = new int[rowSize][columnSize];
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                result[i][j] = tiles[i][j];
            }
        }
        return result;
    }

    // all neighboring boards O(4N)
    public Iterable<Board> neighbors() {
        List<Board> boards = new ArrayList<>();
        // left
        if (blankPositionColumnIndex > 0) {
            int[][] leftBoardTiles = copyTiles();
            swap(leftBoardTiles, blankPositionRowIndex, blankPositionColumnIndex, blankPositionRowIndex, blankPositionColumnIndex - 1);
            boards.add(new Board(leftBoardTiles));
        }

        // right
        if (blankPositionColumnIndex < columnSize - 1) {
            int[][] rightBoardTiles = copyTiles();
            swap(rightBoardTiles, blankPositionRowIndex, blankPositionColumnIndex, blankPositionRowIndex, blankPositionColumnIndex + 1);
            boards.add(new Board(rightBoardTiles));
        }

        // up
        if (blankPositionRowIndex > 0) {
            int[][] upBoardTiles = copyTiles();
            swap(upBoardTiles, blankPositionRowIndex, blankPositionColumnIndex, blankPositionRowIndex - 1, blankPositionColumnIndex);
            boards.add(new Board(upBoardTiles));
        }

        // down
        if (blankPositionRowIndex < rowSize - 1) {
            int[][] downBoardTiles = copyTiles();
            swap(downBoardTiles, blankPositionRowIndex, blankPositionColumnIndex, blankPositionRowIndex + 1, blankPositionColumnIndex);
            boards.add(new Board(downBoardTiles));
        }

        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return new BoardIterator(boards);
            }
        };
    }

    // a board that is obtained by exchanging any pair of tiles O(N)
    public Board twin() {
        int[][] copiedTiles = copyTiles();
        int i1 = StdRandom.uniform(rowSize);
        int j1 = StdRandom.uniform(columnSize);
        int i2 = StdRandom.uniform(rowSize);
        int j2 = StdRandom.uniform(columnSize);
        if (i1 == i2 && j1 == j2) {
            j2 = StdRandom.uniform(columnSize);
        }
        swap(copiedTiles, i1, j1, i2, j2);
        return new Board(copiedTiles);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        for (int n = 2; n < 128; n = n * 2) {
            long start = System.nanoTime();
            int neighbourCount = createBoardAndReturnNeighbourCount(n);
            if (neighbourCount != 2) {
                System.out.println("FAILED");
            }
            System.out.printf("%3d %10s%n", n, System.nanoTime() - start);
        }
    }

    private static int createBoardAndReturnNeighbourCount(int n) {
        int[][] input = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                input[i][j] = n * i + j;
            }
        }
        int temp = input[n - 1][n - 1];
        input[n - 1][n - 1] = input[0][0];
        input[0][0] = temp;
        Iterable<Board> neighbors = new Board(input).neighbors();
        int count = 0;
        for (Board b : neighbors) {
            count += 1;
        }
        return count;
    }

    private static class BoardIterator implements Iterator<Board> {
        private int current = 0;
        private final List<Board> boards;

        public BoardIterator(List<Board> boards) {
            this.boards = boards;
        }

        @Override
        public boolean hasNext() {
            return current < boards.size();
        }

        @Override
        public Board next() {
            return boards.get(current++);
        }
    }
}