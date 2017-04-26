package sort;

import java.util.Arrays;


public class MergeSort2<T extends Comparable<T>> implements Sorter<T> {
    @Override
    public T[] sort(T[] a) {
        return mergeSort(a);
    }

    private T[] mergeSort(T[] a) {
        if (a.length < 2) {
            return a;
        } else {
            int splitIndex = a.length / 2;
            T[] split1 = Arrays.copyOfRange(a, 0, splitIndex);
            T[] split2 = Arrays.copyOfRange(a, splitIndex, a.length);

            return merge(mergeSort(split1), mergeSort(split2));
        }
    }

    private T[] merge(T[] a, T[] b) {
        return null;
    }

    /*
    private T[] merge(T[] a, T[] b) {
        T[] result = Array.<T>newInstance(a[0].getClass(), a.length + b.length);

        T ai = 0;
        int bi = 0;
        int ri = 0;

        while (ai < a.length || bi < b.length) {
            if (bi >= b.length) {
                result[ri] = a[ai];
                ai++;
            } else if (ai >= a.length) {
                result[ri] = b[bi];
                bi++;
            } else if (a[ai] < b[bi]) {
                result[ri] = a[ai];
                ai++;
            } else {
                result[ri] = b[bi];
                bi++;
            }
            ri++;
        }

        return result;
    }
    */
}
