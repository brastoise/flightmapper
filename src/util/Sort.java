package util;
import java.util.Comparator;
public class Sort {

    public static void sort(Object[] a, Comparator c) {
        // If the input is small, just use insertion sort or else use mergesort
        if (a.length < 10) {
            insertionSort(a, c);
        } else {
            Object[] aux = new Object[a.length];
            sort(a, aux, 0, a.length - 1, c);
            assert isSorted(a, c);
        }
    }

    private static void insertionSort(Object[] a, Comparator c) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0 && less(a[j], a[j-1], c); j--) {
                swap(a, j, j-1);
            }
            assert isSorted(a, 0, i, c);
        }
        assert isSorted(a, c);
    }

    private static void merge(Object[] a, Object[] aux, int lo, int mid, int hi, Comparator c) {
        // Check if subarrays are sorted
        assert isSorted(a, lo, mid, c);
        assert isSorted(a, mid+1, hi, c);

        // Copy into aux
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        // Merge back into a[]
        int i = lo;
        int j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i], c)) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }

        // Check sorted status again
        assert isSorted(a, lo, hi, c);
    }

    private static void sort(Object[] a, Object[] aux, int lo, int hi, Comparator c) {
        if (hi <= lo) {
            return;
        }

        int mid = lo + (hi-lo)/2;
        sort(a, aux, lo, mid, c);
        sort(a, aux, mid+1, hi, c);
        merge(a, aux, lo, mid, hi, c);
    }

    private static boolean less(Object v, Object w, Comparator c) {
        return (c.compare(v, w) < 0);
    }

    private static void swap(Object[] a, int i, int j) {
        Object temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static boolean isSorted(Object[] a, Comparator c) {
        return isSorted(a, 0, a.length -1, c);
    }

    private static boolean isSorted(Object[] a, int lo, int hi, Comparator c) {
        for (int i = lo; i <= hi; i++) {
            if (less(a[i], a[i-1], c)) {
                return false;
            }
        }
        return true;
    }


    // Legacy Code based of java.util.Arrays.sort

    /*
    private static void sort(Object[] src, Object[] dest, int lo, int hi, int off, Comparator c) {
        int length = hi - lo;

        // If array is small, just use insertion sort
        if (length < 10) {
            for (int i = lo; i < hi; i++) {
                for (int j = i; j > lo && ((Comparable) dest[j-1]).compareTo(dest[j]) > 0; j++) {
                    swap(dest, j, j-1);
                }
            }
            return;
        }

        int destLo = lo;
        int destHi = hi;
        lo += off;
        hi += off;
        int mid = (lo + hi) >>> 1;

        sort(dest, src, lo, mid, -off, c);
        sort(dest, src, mid, hi, -off, c);

        if (c.compare(src[mid-1], src[mid]) <= 0) {
            System.arraycopy(src, lo, dest, destLo, length);
            return;
        }

        for (int i = destLo, p = lo, q = mid; i < destHi; i++) {
            if (q >= hi || q < mid && c.compare(src[p], src[q]) <= 0) {
                dest[i] = src[p++];
            } else {
                dest[i] = src[q++];
            }
        }


    }

    private static void swap(Object[] x, int a, int b) {
        Object temp = x[a];
        x[a] = x[b];
        x[b] = temp;
    }
    */

}
