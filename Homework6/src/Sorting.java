import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Rakesh Gorrepati
 * @version 1.0
 * @userid rgorrepati2
 * @GTID 903254051
 *
 * Collaborators: N/A
 *
 * Resources: Pseudocode, Notes, Lecture
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (comparator == null || arr == null) {
            throw new IllegalArgumentException("Array or Comparator is null");
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            T curr =  arr[i];
            while (j >= 0 && comparator.compare(curr, arr[j]) < 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = curr;
        }

    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Null array or comparator");
        }
        if (arr.length > 1) {
            int lSize = (arr.length / 2);
            int rSize = arr.length - lSize;
            int mIndex = (arr.length / 2);

            T[] lArray = (T[]) new Object[lSize];
            T[] rArray = (T[]) new Object[rSize];
            int i = 0;
            while (i < lSize) {
                lArray[i] = arr[i];
                i++;
            }
            int j = lSize;
            while (j < arr.length) {
                rArray[j - lSize] = arr[j];
                j++;
            }
            mergeSort(rArray, comparator);
            mergeSort(lArray, comparator);
            int lIndex = 0;
            int rIndex = 0;
            int currIndex = 0;
            while ((lIndex < mIndex) && (rIndex < (arr.length - mIndex))) {
                if (comparator.compare(lArray[lIndex], rArray[rIndex]) < 1) {
                    lIndex++;
                    arr[currIndex] = lArray[lIndex - 1];
                } else {
                    rIndex++;
                    arr[currIndex] = rArray[rIndex - 1];
                }
                currIndex++;
            }
            while (rIndex < (arr.length - mIndex)) {
                currIndex++;
                arr[currIndex - 1] = rArray[rIndex];
                rIndex++;
            }
            while (lIndex < mIndex) {
                currIndex++;
                arr[currIndex - 1] = lArray[lIndex];
                lIndex++;

            }

        }

    }
    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null)  {
            throw new IllegalArgumentException("Null Array can't be sorted");
        }
        int maxValue = arr[0];
        int minValue = arr[0];
        LinkedList<Integer>[] buckets = new LinkedList[19];
        if (arr.length > 1) {
            int exponent = 1;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] > maxValue) {
                    maxValue = arr[i];
                }
                if (arr[i] < minValue) {
                    minValue = arr[i];
                }
            }
            int x = 0;
            while (minValue != 0 || maxValue != 0) {
                maxValue = maxValue / 10;
                minValue = minValue / 10;
                x++;
            }
            int i = 1;
            while (i <= x) {
                for (int j = 0; j <= arr.length - 1; j++) {
                    int bucket = (arr[j] / exponent) % 10;
                    bucket += 9;
                    if (buckets[bucket] == null) {
                        buckets[bucket] = new LinkedList<>();
                    }
                    buckets[bucket].add(arr[j]);
                }
                int index = 0;
                int bIndex = 0;
                while (bIndex < 19) {
                    if (buckets[bIndex] == null) {
                        buckets[bIndex] = new LinkedList<>();
                    }
                    while (!buckets[bIndex].isEmpty()) {
                        index++;
                        arr[index - 1] = buckets[bIndex].removeFirst();
                    }
                    bIndex++;
                }
                exponent *= 10;
                i++;
            }

        }
    }
    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator, Random rand) {
        if (arr == null || comparator == null || k < 1 || k > arr.length || rand == null) {
            throw new IllegalArgumentException("Array or comparator or rand is null or k is out of range");
        } else {
            return helperK(k, arr, comparator, rand, arr.length, 0);
        }


    }

    /**
     * My helper method for kthSelect
     * @param k the index to retrieve data from + 1 (due to 0-indexing) if the array was sorted;
     *          the 'k' in "kth select"; e.g. if k == 1, return the smallest element in the array
     * @param arr the array that should be modified after the method is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param right the most right index
     * @param left the most left index
     * @param <T> data type to sort
     * @return the kth smallest element
     */
    private static <T> T helperK(int k, T[] arr, Comparator<T> comparator, Random rand, int right, int left) {
        int pivotIndex = rand.nextInt(right);
        T pivot = arr[pivotIndex];
        arr[pivotIndex] = arr[left];
        arr[left] = pivot;
        int r = right - 1;
        int l = left + 1;
        while (l <= r) {
            while (l <= r && comparator.compare(pivot, arr[l]) >= 0) {
                l++;
            }
            while (l <= r && comparator.compare(pivot, arr[l]) >= 0) {
                r--;
            }
            if (l <= r) {
                T temp = arr[l];
                arr[l] = arr[r];
                arr[r] = temp;
                r--;
                l++;

            }
        }
        arr[left] = arr[r];
        arr[r] = pivot;
        if (r == k - 1) {
            return pivot;
        } else if (r < k - 1) {
            return helperK(k, arr, comparator, rand, right, r + 1);
        } else {
            return helperK(k, arr, comparator, rand, r, left);
        }
    }

}
