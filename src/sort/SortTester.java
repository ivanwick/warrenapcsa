package sort;

import java.util.Random;

public class SortTester {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Random rand = new Random();

        Class sorterClass = Class.forName("sort.BubbleSorter");
        Sorter sorter = (Sorter) sorterClass.newInstance();

        for (int i = 0; i < 100; i++) {
            int clen = rand.nextInt(90000);
            Integer[] c = new Integer[clen];

            fillRandom(c, rand);

            Comparable[] sortedc = sorter.sort(c);

            // printArray(c);
            // printArray(sortedc);
/*
            if (checkSorted(sortedc)) {
                System.out.println("List with " + clen + " items OK SORTED :D");
            } else {
                System.out.println("wtf not sorted ur codes wrong try harder next time");
            }
*/
        }
    }

    public static void fillRandom(Integer[] a, Random rand) {
        for (int i = 0; i < a.length; i++) {
            a[i] = rand.nextInt();
        }
    }

    public static void printArray(int[] a) {
        System.out.print("[ ");
        for (int i : a) {
            System.out.print(i + " ");
        }
        System.out.println("]");
    }

    public static boolean checkSorted(Integer[] a) {
        if (a.length == 0) {
            return true;
        }

        for (int i = 1; i < a.length; i++) {
            if (a[i - 1].compareTo(a[i]) > 0) {
                System.out.println(a[i - 1] + " > " + a[i]);
                return false;
            }
        }

        return true;
    }
}
