package tutorail.java.part1.sortings;

/**
 * Page: 258 in the book.
 *
 * It sorts elements by h-sorting the array. An h-sorted array is h interleaved sorted subsequences.
 * <p>
 * Shell sort is similar to insertion sort, where insertion sort goes one element back instead of that it goes h elements
 * <p>
 * https://en.wikipedia.org/wiki/Shellsort
 */
public class ShellSort {
    public static void sort(Comparable[] a) {
        int h = 1;
        while (h < a.length / 3) h = 3 * h + 1;

        while (h >= 1) {
            for (int i = h; i < a.length; i++) {
                for (int j = i; j >= h; j -= h) {
                    if (less(a[j], a[j - h])) {
                        swap(a, j, j - h);
                    }
                }
            }
            h = h / 3;
        }
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    public static void swap(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
