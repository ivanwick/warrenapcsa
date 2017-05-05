package puzzle8;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private final int[][] tiles;
    private final int size;
    private final int[] spacePosition;

    /**
     * Construct a board from an N-by-N array of tiles
     *
     * @param tiles
     */
    public Board(int[][] tiles) {
        this.tiles = copyArray2D(tiles);
        this.size = tiles.length;
        this.spacePosition = findSpacePosition();
    }

    private int[][] copyArray2D(int[][] original) {
        if (original == null) {
            return null;
        }

        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    /**
     * @return number of blocks out of place
     */
    public int hamming() {
        return 0;
    }


    /**
     * @return sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        return 0;
    }

    public int[][] getTiles() {
        return tiles;
    }

    public int getSize() {
        return size;
    }

    /**
     * does this board position equal other
     *
     * @param other
     * @return
     */
    public boolean equals(Object other) {
        if (other == null || !(other instanceof Board)) {
            return false;
        }

        Board otherBoard = (Board)other;

        int[][] otherTiles = otherBoard.getTiles();

        if (this.tiles.length != otherTiles.length) {
            return false;
        }

        for (int i = 0; i < this.tiles.length; i++) {
            if (!Arrays.equals(this.tiles[i], otherTiles[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * @return an Iterable of all neighboring board positions
     */
    public Iterable<Board> neighbors() {
        ArrayList<Board> result = new ArrayList<>();

        for (Direction d : Direction.values()) {
            if (isMovePossible(d)) {
                Board b = applyMove(d);
                result.add(b);
            }
        }

        return result;
    }

    /**
     * Construct a new Board based on this one with the space moved in the
     * given direction.
     *
     * Precondition: Assume the given direction is a valid move for this board
     *
     * @param d
     * @return
     */
    private Board applyMove(Direction d) {
        int[][] movedTiles = copyArray2D(tiles);
        int[] moveVector = d.getVector();
        int[] swapPosition = addPosition2D(spacePosition, moveVector);

        // Swap tiles.
        // Normally in a swap operation you have the familiar
        //   temp = a
        //   a = b
        //   b = temp
        // but in this case we know that we are always swapping with the space
        // position, so we don't need to save a temp variable.
        movedTiles[spacePosition[0]][spacePosition[1]] = movedTiles[swapPosition[0]][swapPosition[1]];
        movedTiles[swapPosition[0]][swapPosition[1]] = 0;

        return new Board(movedTiles);
    }

    private int[] addPosition2D(int[] pos, int[] vec) {
        return new int[]{
                pos[0] + vec[0],
                pos[1] + vec[1]
        };
    }

    private boolean isMovePossible(Direction d) {
        int[] moveVector = d.getVector();
        int[] endingPosition = addPosition2D(spacePosition, moveVector);

        return isValidPosition(endingPosition);
    }

    private boolean isValidPosition(int[] pos) {
        return (pos[0] >= 0 && pos[1] >= 0 &&
                pos[0] < size && pos[1] < size);
    }

    private enum Direction {
        UP(new int[]{-1, 0}),
        DOWN(new int[]{1, 0}),
        LEFT(new int[]{0, -1}),
        RIGHT(new int[]{0, 1});

        private int[] vector;

        Direction(int[] vector) {
            this.vector = vector;
        }

        int[] getVector() {
            return vector;
        }
    }

    private int[] findSpacePosition() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("Board doesn't have a space (0) position");
    }


    /**
     * @return a string representation of the board
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int digitsWidth = squareDigitsWidth(size);
        String[] rowStrings = new String[size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] == 0) {
                    rowStrings[j] = String.format("%" + digitsWidth + "s", "");
                } else {
                    rowStrings[j] = String.format("%" + digitsWidth + "d", tiles[i][j]);
                }
            }
            stringBuilder.append(String.join(" ", rowStrings));
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    /**
     * The number of characters necessary to print the maximum number in a
     * square of size num.
     *
     * @param num
     * @return
     */
    public int squareDigitsWidth(int num) {
        int maxInSquare = (num * num) - 1;
        String strNum = String.format("%d", maxInSquare);
        return strNum.length();
    }

    static Board solutionBoard(int size) {
        int[][] solutionTiles = new int[size][size];

        int tileCounter = 1;
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                solutionTiles[r][c] = tileCounter;
                tileCounter++;
            }
        }

        solutionTiles[size-1][size-1] = 0;
        return new Board(solutionTiles);
    }
}
