package p16interstellar.state;

import p16interstellar.Star;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExplicitGridState implements State {

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
    public ExplicitGridState(int mapSize, List<Star> stars) {
        this.mapSize = mapSize;
        this.stars = stars;
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
    private ExplicitGridState(ExplicitGridState previousState, int[] nextPosition) {
        this.mapSize = previousState.mapSize;
        this.stars = previousState.stars;
        this.path = Arrays.copyOf(previousState.path, previousState.path.length + 1);
        this.path[this.path.length - 1] = nextPosition;
        this.batteryLevel = batteryCharge(previousState.batteryLevel, nextPosition);
    }

    /**
     * Successor function
     *
     * @return
     */
    public List<State> getPossibleMoves() {
        ArrayList<State> nextMoves = new ArrayList<>();
        int[] currentPosition = getCurrentPosition();
        for (int[] v : directionVectors) {
            int[] movePosition = addVector(currentPosition, v);
            if (legalMove(movePosition)) {
                nextMoves.add( new ExplicitGridState(this, movePosition) );

                /*
                for (int[] p : path) {
                    System.err.print(String.format("{%2d, %2d, %2d} ", p[0], p[1], p[2]));
                }
                System.err.println(String.format("{%2d, %2d, %2d}",
                        movePosition[0], movePosition[1], movePosition[2]));
                */
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

        // See if there is a star in the position we are trying to move to
        Star moveStar = starAtPosition(movePosition);

        if (moveStar == null) {
            // There is no star in the next position
            if (batteryLevel == 1) {
                // Battery is almost dead, so we can't move to an empty grid
                return false;
            } else {
                // Enough battery to move to an empty grid
                return true;
            }
        } else {
            if (alreadyVisited(moveStar)) {
                // Can't visit a star more than once
                return false;
            } else {
                return true;
            }
        }
    }

    public int[][] getPath() {
        return path;
    }

    public int getPathDistance() {
        return path.length - 1;
    }

    private int[] addVector(int[] position, int[] v) {
        return new int[]{
                position[0] + v[0],
                position[1] + v[1],
                position[2] + v[2],
        };

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

    private final int[][] directionVectors = {
            {1, 0, 0},
            {-1, 0, 0},
            {0, 1, 0},
            {0, -1, 0},
            {0, 0, 1},
            {0, 0, -1},
    };

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
    public int batteryCharge(int prevBattery, int[] position) {
        Star s = starAtPosition(position);
        int b = prevBattery - 1;  // Cost 1 unit of battery charge to move

        if (s != null) {
            b = b + s.getEnergy();
        }

        if (b > MAX_BATTERY_CHARGE) {
            b = MAX_BATTERY_CHARGE;
        }

        return b;
    }
}

