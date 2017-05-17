package p16interstellar;

import java.util.HashMap;

public class Star {
    private int energy;
    public int[] position;

    static final HashMap<String, Integer> TYPE_ENERGY = new HashMap<String, Integer>() {{
        put("M", 3);
        put("K", 4);
        put("G", 5);
        put("F", 6);
        put("A", 7);
        put("B", 8);
        put("O", 9);
    }};

    public Star(String type, int x, int y, int z) {
        this.position = new int[]{x, y, z};
        this.energy = TYPE_ENERGY.get(type);
    }

    public int[] getPosition() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }

    public String toString() {
        return "(" + position[0] + ", " + position[1] + ", " + position[2] + ") Energy: " + energy;
    }
}
