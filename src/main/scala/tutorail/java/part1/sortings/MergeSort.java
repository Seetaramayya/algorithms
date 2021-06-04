package tutorail.java.part1.sortings;

import java.util.Random;

import static tutorail.java.part1.sortings.ComparableUtils.less;

/**
 * without insertion sort for a smaller array:
 * N  Time Taken (ms)
 * 7                0
 * 70                0
 * 700                1
 * 7000                3
 * 70000              100
 * 700000              803
 * 7000000             6605
 *
 * with insertion sort for a smaller array (size < 7):
 * N                   Time Taken (ms)
 * 7                   1
 * 70                  1
 * 700                 1
 * 7000                6
 * 70000               106
 * 700000              767 (improved a bit)
 * 7000000             5078 (improved a bit)
 */
// TODO: Bench mark merge sort
public class MergeSort {
    public static final int CUT_OFF = 7;

    public static void sort(Comparable[] input) {
        int size = input.length;
        if (size < CUT_OFF) {
            InsertionSort.sort(input);
        } else {
            Comparable[] temp = input.clone(); // no need to copy
            sort(input, temp, 0, size - 1);
        }
    }

    private static void sort(Comparable[] input, Comparable[] temp, int lo, int hi) {
        if (hi <= lo + CUT_OFF - 1) {
            InsertionSort.sort(input, lo, hi);
        } else {
            if (hi > lo) {
                int mid = lo + (hi - lo) / 2;
                sort(input, temp, lo, mid);
                sort(input, temp, mid + 1, hi);
                if (!less(input[mid + 1], input[mid])) return;
                merge(input, temp, lo, mid, hi);
            }
        }
    }

    private static void printArray(String message, Comparable[] a) {
        System.out.printf("Printing %s ", message);
        for (int i = 0; i < a.length; i++) {
            System.out.printf("%s ", a[i]);
        }
        System.out.println("");
    }

    private static void merge(Comparable[] input, Comparable[] temp, int lo, int mid, int hi) {
        int i = lo;
        int j = mid + 1;

        for (int k = lo; k <= hi; k++) temp[k] = input[k];

        for (int k = lo; k <= hi; k++) {
            if (i > mid) input[k] = temp[j++];
            else if (j > hi) input[k] = temp[i++];
            else if (less(temp[i], temp[j])) input[k] = temp[i++];
            else input[k] = temp[j++];
        }
    }

    private static Integer[] randomArray(int size) {
        Integer[] elements = new Integer[size];
        for (int i = 0; i < size; i++) {
            elements[i] = new Random().nextInt();
        }
        return elements;
    }

    public static void main(String[] args) {
        int max = 10000000;
        String width = String.valueOf(String.valueOf(max).length() + 8);
        String titleFormat = " %width-s %width-s%n".replaceAll("width-", width);
        // ln means log base 2 and logN means log base 10
        System.out.printf(titleFormat, "N", "Time Taken (ms)");
        for (int i = 7; i < max; i *= 10) {
            Integer[] elements = randomArray(i);
            long start = System.currentTimeMillis();
            MergeSort.sort(elements);
            long timeTakenInMillis = System.currentTimeMillis() - start;
            System.out.printf(" %widths %widthd%n".replaceAll("width", width), i, timeTakenInMillis);
        }
    }
}
