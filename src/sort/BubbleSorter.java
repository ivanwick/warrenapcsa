package sort;

import java.util.Arrays;

public class BubbleSorter<T extends Comparable<T>> implements Sorter<T>
{
    @Override
    public T[] sort(T[] a) {
        T[] b = Arrays.copyOfRange(a, 0, a.length);

        boolean didSwap;
        do {
            didSwap = false;
            for (int i = 0; i < b.length - 1; i++) {
                if ((b[i].compareTo(b[i + 1])) > 0) {
                    T temp = b[i];
                    b[i] = b[i + 1];
                    b[i + 1] = temp;
                    didSwap = true;
                }
            }
        } while (didSwap);

        return b;
    }
}
