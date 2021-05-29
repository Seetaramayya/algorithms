package tutorail.java.part1.sortings;

import static tutorail.java.part1.sortings.ComparableUtils.less;

/**
 *
 */
public class MergeSort {
    public static void sort(Comparable[] input) {
        if (input.length > 1) {
            Comparable[] temp = new Comparable[input.length];
            sort(input, temp, 0, input.length - 1);
        }
    }

    private static void sort(Comparable[] input, Comparable[] temp, int lower, int upper) {
        if (upper <= lower) return;
        else {
            int mid = lower + (upper - lower) / 2;
            sort(input, temp, lower, mid);
            sort(input, temp, mid + 1, upper);
            merge(input, temp, lower, mid, upper);
        }

    }

    private static void merge(Comparable[] input, Comparable[] temp, int lo, int mid, int hi) {
        int i = lo;
        int j = mid + 1;
        assert isSorted(input, lo, mid);
        assert isSorted(input, mid + 1, hi);

        for (int k = lo; k <= hi; k++) {
            temp[k] = input[k];
        }

        for (int k = lo; k <= hi; k++) {
            if (i > mid) input[k] = temp[j++];
            else if (j > hi) input[k] = temp[i++];
            else if (less(temp[i], temp[j])) input[k] = temp[i++];
            else input[k] = temp[j++];
        }
    }

    private static boolean isSorted(Comparable[] a, int l, int h) {
        boolean result = true;
        for (int i = l + 1; i <= h; i++) {
            if (less(a[i], a[i - 1])) {
                result = false;
                break;
            }
        }
        return result;
    }
}
