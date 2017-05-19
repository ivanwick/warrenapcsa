package p16interstellar.solver;

import p16interstellar.state.State;

public interface Solver {
    State solve(State initialState);
}
