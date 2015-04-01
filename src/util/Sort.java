package util;
import java.util.Comparator;
public class Sort {

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

}
