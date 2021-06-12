package tutorial.java.part1.assignment.eightpuzzle;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        rowSize = tiles.length;
        columnSize = tiles.length;
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
        StringBuilder s = new StringBuilder();
        s.append(rowSize + "\n");
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
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
        int distanceSum = 0;
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                int expectedTileValue = (rowSize * i) + j + 1;
                int currentTileValue = tiles[i][j];
                if (currentTileValue != expectedTileValue && currentTileValue != 0) {
                    int x2 = getRowPosition(currentTileValue);
                    int y2 = getColumnPosition(currentTileValue);
                    int d = (Math.abs(i - x2) + Math.abs(j - y2));
                    distanceSum += d;
                }
            }
        }
        return distanceSum;
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
            for (int i = 0; i < totalElements - 1; i++) {
                int row = i / rowSize;
                int col = i % columnSize;
                if (tiles[row][col] != (i + 1)) {
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

        return boards;
    }

    // a board that is obtained by exchanging any pair of tiles O(N)
    public Board twin() {
        int[][] copiedTiles = copyTiles();
        if (copiedTiles[0][0] != 0 && copiedTiles[0][1] != 0) {
            swap(copiedTiles, 0, 0, 0, 1);
        } else {
            swap(copiedTiles, 1, 0, 1, 1);
        }

        return new Board(copiedTiles);
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        System.out.println(initial);
    }
}