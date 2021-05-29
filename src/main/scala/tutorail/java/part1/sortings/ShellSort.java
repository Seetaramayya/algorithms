package tutorail.java.part1.sortings;

import static tutorail.java.part1.sortings.ComparableUtils.less;
import static tutorail.java.part1.sortings.ComparableUtils.swap;

/**
 * Page: 258 in the book.
 *
 * It sorts elements by h-sorting the array. An h-sorted array is h interleaved sorted subsequences.
 * <p>
 * Shell sort is similar to insertion sort, where insertion sort goes one element back instead of that it goes h elements
 * <p>
 *
 *    Best Case           Avg            Worst         Memory
 *    N logN              N^4/3            N^3/2       no extra memory
 *
 *                 N           N logN             N ln         N ^  4/3          N ^ 3/2            N ^ 2
 *                11          11,4553          26,3768          24,4638          36,4829            121,0
 *               110         224,5532         517,0528         527,0562        1153,6897          12100,0
 *              1100        3345,5320        7703,3720       11355,0813       36482,8727        1210000,0
 *             11000       44455,3195      102362,1561      244637,8100     1153689,7330      121000000,0
 *            110000      554553,1954     1276905,9209     5270561,8428    36482872,6939    12100000000,0
 *           1100000     6645531,9537    15301902,8115   113550812,7002  1153689732,9872  1210000000000,0
 *
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

    public static void main(String[] args) {
        int max = 10000000;
        String width = String.valueOf(String.valueOf(max).length() + 8);
        String titleFormat = " %width-s %width-s %width-s %width-s %width-s %width-s%n".replaceAll("width-", width);
        // ln means log base 2 and logN means log base 10
        System.out.printf(titleFormat,"N", "N logN", "N ln",  "N ^  4/3", "N ^ 3/2", "N ^ 2");
        for (int i = 11; i < max; i = i * 10) {
            double log  = i * Math.log10(i);
            double ln  = i * Math.log(i);
            double avg   = Math.pow(i, 4 / 3d);
            double worst = Math.pow(i, 3 / 2d);
            double square = Math.pow(i, 2);
            String format = " %widthd %width.4f %width.4f %width.4f %width.4f %width.1f%n".replaceAll("width", width);
            System.out.printf(format, i, log, ln, avg, worst, square);
        }
    }
}
