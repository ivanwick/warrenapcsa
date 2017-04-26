package sort;

public interface Sorter<T extends Comparable<T>> {
    T[] sort(T[] a);
}
