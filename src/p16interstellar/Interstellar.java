package p16interstellar;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Interstellar {
    //Print array of Stars
    public static void printArray(Star[][][] in) {
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                for (int k = 0; k < in[0][0].length; k++) {
                    System.out.println(in[i][j][k].toString());
                }
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        //File path
        String file = "Prob16.in.txt";
        //New BufferedResder object
        BufferedReader br = new BufferedReader(new FileReader(file));
        //Get test cases
        int testCases = Integer.parseInt(br.readLine());
        //Loop through test cases
        for (int i = 0; i < testCases; i++) {
            ArrayList<Star> starList = new ArrayList<>();

            //Grab cube amount
            int cubeAmount = Integer.parseInt(br.readLine());

            //Grab numstars able to havest
            int numStars = Integer.parseInt(br.readLine());
            //Loop through numstars
            for (int j = 0; j < numStars; j++) {
                //Read line
                String line = br.readLine();
                //Split accordingly. the indexes should be like so: line[0] = energy, line[1] = x; line[2] = y; line[3] = z
                String[] lineSplit = line.split(",");
                //make it easier to access variables
                String energy = lineSplit[0];
                int x = Integer.parseInt(lineSplit[1]);
                int y = Integer.parseInt(lineSplit[2]);
                int z = Integer.parseInt(lineSplit[3]);
                //modify the object and change energy
                starList.add(new Star(energy, x, y, z));
            }

            BFSSolver solver = new BFSSolver(cubeAmount, starList);
            State solution = solver.solve();
            if (solution != null) {
                int[][] solutionPath = solution.getPath();
                System.out.println(solutionPath.length - 1);
            } else {
                System.out.println("No solution");
            }
        }
    }
}