package tutorail.java.part1.sortings;

import static tutorail.java.part1.sortings.ComparableUtils.less;
import static tutorail.java.part1.sortings.ComparableUtils.swap;

/**
 * Selection sort:
 *  - Identify smallest in the remaining array
 *  - Swap with current element with smallest
 *  - Repeat above 2 steps until finishes all the elements
 * Performance:
 *   - N + (N - 1) + (N -2) + .... + 3 + 2 + 1 = ∑N = N * (N - 1)/ 2 = O(N^2)
 *
 *    Best Case           Avg            Worst         Memory
 *    N^2                 N^2            N^2           no extra memory
 *
 * Whether array is sorted or not complexity is same for the selection sort
 *
 *
 * https://en.wikipedia.org/wiki/Selection_sort
 * https://en.wikipedia.org/wiki/Sorting_algorithm
 */
public class SelectionSort {
    // Sorts given array, so no need to return anything :)
    public static void sort(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                // is a[j] < a[i] if so swap
                if (less(a[j], a[i])) {
                    swap(a, j, i);
                }
            }

        }
    }
}
