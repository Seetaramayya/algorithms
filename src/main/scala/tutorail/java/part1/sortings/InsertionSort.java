package tutorail.java.part1.sortings;

import static tutorail.java.part1.sortings.ComparableUtils.less;
import static tutorail.java.part1.sortings.ComparableUtils.swap;

/**
 * Insertion sort: From left to right, move the current element left if it is small
 * - Identify smallest in the remaining array
 * - Swap with current element with smallest
 * - Repeat above 2 steps until finishes all the elements
 * <p>
 * Performance: Worst case : O(N2), Best case: O(N) because just N - 1 comparisons, for partially ordered array it is linear
 * <p>
 * <p>
 * Best Case           Avg            Worst         Memory
 * N                   N^2            N^2           no extra memory
 * <p>
 * - Input Array     : (4, 3, 2, 1)
 * i = 1, j = 1  : (3, 4, 2, 1)
 * i = 2, j = 2  : (3, 2, 4, 1)
 * i = 2, j = 1  : (2, 3, 4, 1)
 * i = 3, j = 3  : (2, 3, 1, 4)
 * i = 3, j = 2  : (2, 1, 3, 4)
 * i = 3, j = 1  : (1, 2, 3, 4)
 * <p>
 * https://en.wikipedia.org/wiki/Insertion_sort
 */
public class InsertionSort {
    public static int sort(Comparable[] a) {
        return sort(a, 0, a.length);
    }

    /**
     *
     * @param a
     * @param lo inclusive
     * @param hi exclusive
     * @return
     */
    public static int sort(Comparable[] a, int lo, int hi) {
        int comparisons = 0;
        for (int i = lo; i < hi; i++) {
            for (int j = i; j > lo; j--) {
                comparisons++;
                // if a[j - 1] < a[j] if so swap
                if (less(a[j], a[j - 1])) {
                    swap(a, j, j - 1);
                } else {
                    break;
                }
            }
        }
        return comparisons;
    }
}
