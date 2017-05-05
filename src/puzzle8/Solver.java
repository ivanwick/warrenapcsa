package puzzle8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


/**
 * https://www.cs.princeton.edu/courses/archive/spr10/cos226/assignments/8puzzle.html
 */
public class Solver {

    List<Board> currentState;

    List<List<Board>> openStates;
    final Board solutionBoard;

    /**
     * find a solution to the initial board
     *
     * @param initial
     */
    public Solver(Board initial) {
        this.currentState = Collections.singletonList(initial);
        this.solutionBoard = Board.solutionBoard(initial.getSize());
    }

    /**
     * is the initial board solvable?
     *
     * @return
     */
    public boolean isSolvable() {
        return currentState != null;
    }

    /**
     * @return min number of moves to solve initial board; -1 if no solution
     */
    public int moves() {
        return currentState.size() - 1;
    }

    /**
     * @return an Iterable of board positions in solution
     */
    public Iterable<Board> solution() {
        return currentState;
    }

    public void solve() {
        openStates = new ArrayList<>();
        openStates.add(currentState);

        while (!openStates.isEmpty()) {
            // Using openStates as a Queue
            currentState = openStates.remove(0);
            Board lastBoard = currentState.get(currentState.size() - 1);

            if (lastBoard.equals(solutionBoard)) {
                return;
            }

            for (Board b : lastBoard.neighbors()) {
                if (!isBacktrack(currentState, b)) {
                    List<Board> nextState = nextState(currentState, b);
                    openStates.add(nextState);
                }
            }
        }

        // If we got here without matching the solution board, we can't solve it.
        currentState = null;
    }

    private List<Board> nextState(List<Board> history, Board nextBoard) {
        List<Board> result = new ArrayList<>();
        result.addAll(history);
        result.add(nextBoard);
        return result;
    }

    private boolean isBacktrack(List<Board> history, Board nextBoard) {
        if (history.size() < 1) {
            // This is the first state with no previous boards in the history
            return false;
        }

        return history.contains(nextBoard);
    }

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        int N = inputScanner.nextInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                tiles[i][j] = inputScanner.nextInt();
        Board initial = new Board(tiles);

        Solver solver = new Solver(initial);
        solver.solve();

        if (!solver.isSolvable()) {
            System.out.println("No solution possible");
        } else {
            for (Board board : solver.solution())
                System.out.println(board);
            System.out.println("Minimum number of moves = " + solver.moves());
        }
    }
}
