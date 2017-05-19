package p16interstellar.state;

import p16interstellar.Star;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImplicitGridState implements State {

    private int[][] path;
    private int batteryLevel;
    private int mapSize;
    private List<Star> stars;

    private final int MAX_BATTERY_CHARGE = 20;

    /**
     * Constructor for initial state
     * @param mapSize
     * @param stars
     */
    public ImplicitGridState(int mapSize, List<Star> stars) {
        this.mapSize = mapSize;
        this.stars = stars;

        // add the destination Star
        this.stars.add(new Star(null, mapSize - 1, mapSize - 1, mapSize - 1));

        this.batteryLevel = MAX_BATTERY_CHARGE;
        this.path = new int[][]{
                {0, 0, 0}
        };
    }

    /**
     * Constructor for successor states
     * @param previousState
     * @param nextPosition
     */
    private ImplicitGridState(ImplicitGridState previousState, int[] nextPosition) {
        this.mapSize = previousState.mapSize;
        this.stars = previousState.stars;
        this.path = Arrays.copyOf(previousState.path, previousState.path.length + 1);
        this.path[this.path.length - 1] = nextPosition;
        this.batteryLevel = batteryCharge(previousState, nextPosition);
    }

    /**
     * Successor function
     *
     * @return
     */
    public List<State> getPossibleMoves() {
        ArrayList<State> nextMoves = new ArrayList<>();

        for (Star s : stars) {
            int[] movePosition = s.getPosition();
            if (legalMove(movePosition)) {
                nextMoves.add( new ImplicitGridState(this, movePosition) );
            }
        }

        return nextMoves;
    }

    private boolean legalMove(int[] movePosition) {
        // Disqualify a move that goes off the map
        if (!withinMap(movePosition)) {
            return false;
        }

        // Disqualify a move that revisits a space it's already been to
        for (int[] prevPosition : path) {
            if (Arrays.equals(movePosition, prevPosition)) {
                return false;
            }
        }

        int[] currentPosition = getCurrentPosition();
        int moveDistance = distance(currentPosition, movePosition);

        if (batteryLevel <= moveDistance) {
            // not enough battery to get there
            return false;
        } else {
            return true;
        }
    }

    public int[][] getPath() {
        return path;
    }

    public int getPathDistance() {
        int dist = 0;

        for (int i = 0; i < path.length - 1; i++) {
            dist += distance(path[i], path[i+1]);
        }

        return dist;
    }

    private int distance(int[] posA, int[] posB) {
        return Math.abs(posA[0] - posB[0]) +
                Math.abs(posA[1] - posB[1]) +
                Math.abs(posA[2] - posB[2]);
    }


    public Star starAtPosition(int[] position) {
        for (Star s : stars) {
            if (Arrays.equals(s.getPosition(), position)) {
                return s;
            }
        }
        return null;
    }

    public boolean isAtDestination() {
        int[] currentPosition = getCurrentPosition();
        return (currentPosition[0] == mapSize - 1 &&
                currentPosition[1] == mapSize - 1 &&
                currentPosition[2] == mapSize - 1);
    }

    public int[] getCurrentPosition() {
        return path[path.length - 1];
    }

    private boolean withinMap(int[] position) {
        return (0 <= position[0] && position[0] < mapSize &&
                0 <= position[1] && position[1] < mapSize &&
                0 <= position[2] && position[2] < mapSize);
    }

    private boolean alreadyVisited(Star s) {
        if (s == null) {
            return false;
        }

        for (int[] prevPosition : path) {
            if (Arrays.equals(prevPosition, s.getPosition())) {
                return true;
            }
        }
        return false;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    /**
     * Resulting charge of the battery after visiting the given position.
     * Doesn't let the battery amount go above 20.
     *
     * @param position
     * @return
     */
    public int batteryCharge(ImplicitGridState prevState, int[] position) {
        int prevBattery = prevState.getBatteryLevel();
        int b = prevBattery - distance(prevState.getCurrentPosition(), position);

        Star s = starAtPosition(position);
        if (s != null) {
            b = b + s.getEnergy();
        }

        if (b > MAX_BATTERY_CHARGE) {
            b = MAX_BATTERY_CHARGE;
        }

        return b;
    }
}

