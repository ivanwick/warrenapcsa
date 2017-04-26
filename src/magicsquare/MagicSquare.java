package magicsquare;

public class MagicSquare {
    public static void main(String[] args) {
        int[][][] testSquares = {
                {
                        {8, 1, 6},
                        {3, 5, 7},
                        {4, 9, 2}
                },
                {
                        {1, 2, 3},
                        {4, 5, 6},
                        {7, 8, 9}
                },
                {
                        {1, 8, 12, 13},
                        {14, 11, 7, 2},
                        {15, 10, 6, 3},
                        {4, 5, 9, 16},
                },
                {
                        {1, 8, 12, 13},
                        {14, 11, 7},
                        {15, 10, 6, 3, 0, 4},
                        {4, 5, 9, 16},
                }
        };

        for (int i = 0; i < testSquares.length; i++) {
            System.out.print(String.format("testSquares[%d]: ", i));
            if (isMagicSquare(testSquares[i])) {
                System.out.println("✅ is a Magic Square");
            } else {
                System.out.println("❌ NOT a Magic Square");
            }
        }
    }

    static boolean isMagicSquare(int[][] square) {
        // First check shape of the matrix
        if (!isSquare(square)) {
            // If it's not square in the first place, we definitely know it's
            // not a magic square.
            return false;
        }

        // We know it's a square, so this is the number of rows or columns (same)
        int squareSize = square.length;
        int[] rowSums = new int[squareSize];
        int[] colSums = new int[squareSize];

        // Calculate row sums
        for (int i = 0; i < squareSize; i++) {
            rowSums[i] = sumRow(square, i);
        }

        if (!sameValueArray(rowSums)) {
            return false;
        }

        // Calculate column sums
        for (int i = 0; i < squareSize; i++) {
            colSums[i] = sumColumn(square, i);
        }

        if (!sameValueArray(colSums)) {
            return false;
        }

        if (sumDiagonalBLTR(square) != sumDiagonalTLBR(square)) {
            return false;
        }

        // If it passed all previous tests it is a magic square.
        return true;
    }

    static boolean sameValueArray(int[] a) {
        int val = a[0];
        for (int i = 0; i < a.length; i++) {
            if (val != a[i]) {
                return false;
            }
        }
        return true;
    }

    static boolean isSquare(int[][] matrix) {
        int rowCount = matrix.length;
        for (int[] row : matrix) {
            // The length of a row is the same as the number of columns
            if (row.length != rowCount) {
                // A row has the wrong number of columns, so we know it's not
                return false;
            }
        }

        // If we get here, we know all rows had the right number of columns
        return true;
    }

    static int sumRow(int[][] square, int rowIndex) {
        int sum = 0;
        for (int i = 0; i < square[rowIndex].length; i++) {
            sum += square[rowIndex][i];
        }
        return sum;
    }

    static int sumColumn(int[][] square, int colIndex) {
        int sum = 0;
        for (int i = 0; i < square.length; i++) {
            sum += square[i][colIndex];
        }
        return sum;
    }

    static int sumDiagonalTLBR(int[][] square) {
        int sum = 0;
        for (int i = 0, j = 0; i < square.length && j < square.length; i++, j++) {
            sum += square[i][j];
        }
        return sum;
    }

    static int sumDiagonalBLTR(int[][] square) {
        int sum = 0;
        for (int i = square.length - 1, j = 0; i >= 0 && j < square.length; i--, j++) {
            sum += square[i][j];
        }
        return sum;
    }
}
