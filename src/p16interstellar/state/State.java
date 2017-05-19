package p16interstellar.state;

import java.util.List;

public interface State {
    boolean isAtDestination();
    int getPathDistance();
    List<State> getPossibleMoves();
    int[] getCurrentPosition();
    int getBatteryLevel();
}
