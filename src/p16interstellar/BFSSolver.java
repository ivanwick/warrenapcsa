package p16interstellar;

import java.util.ArrayList;
import java.util.List;

public class BFSSolver {

    int size;
    List<Star> starList;

    BFSSolver(int size, List<Star> starList) {
        this.size = size;
        this.starList = starList;
    }

    public State solve() {
        State initialState = new State(size, starList);
        ArrayList<State> openStates = new ArrayList<>();
        openStates.add(initialState);

        while (! openStates.isEmpty()) {
            State currentState = openStates.remove(0); // use as Queue

            if (currentState.isAtDestination()) {
                return currentState;
            }

            List<State> moveStates = currentState.getPossibleMoves();
            openStates.addAll(moveStates);
        }

        // If we got here, we tried all states and nothing was at the destination.
        // null means no solution possible.
        return null;
    }
}
