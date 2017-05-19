package p16interstellar.solver;

import p16interstellar.Star;
import p16interstellar.state.ImplicitGridState;
import p16interstellar.state.State;

import java.util.ArrayList;
import java.util.List;

public class DijkstraSolver implements Solver {

    int size;
    List<Star> starList;

    State[][][] bestState; // The best state that gets us to the position [x][y][z]

    public DijkstraSolver(int size, List<Star> starList) {
        this.size = size;
        this.starList = starList;
        this.bestState = new State[size][size][size];
    }

    public State solve(State initialState) {
        ArrayList<State> openStates = new ArrayList<>();
        openStates.add(initialState);
        bestState[0][0][0] = initialState;

        while (!openStates.isEmpty()) {
            State currentState = openStates.remove(0); // use as Queue

            if (currentState.isAtDestination()) {
                return currentState;
            }

            List<State> moveStates = currentState.getPossibleMoves();

            for (State nextState : moveStates) {
                int[] coords = nextState.getCurrentPosition();
                State shortestSoFar = bestState[coords[0]][coords[1]][coords[2]];

                if (shortestSoFar == null ||
                        nextState.getBatteryLevel() > shortestSoFar.getBatteryLevel()) {
                    bestState[coords[0]][coords[1]][coords[2]] = nextState;
                    openStates.add(nextState);
                }
            }
        }

        // If we got here, we tried all states and nothing was at the destination.
        // null means no solution possible.
        return null;
    }
}
