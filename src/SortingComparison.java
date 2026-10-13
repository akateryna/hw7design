
import java.util.Random;

public class SortingComparison {

    // Selection Sort
    public static long selectionSort(int[] array) {
        long comparisons = 0;

        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < array.length; j++) {
                comparisons++;

                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }

            int temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }

        return comparisons;
    }

    // Insertion Sort
    public static long insertionSort(int[] array) {
        long comparisons = 0;

        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0) {
                comparisons++;

                if (array[j] > key) {
                    array[j + 1] = array[j];
                    j--;
                } else {
                    break;
                }
            }

            array[j + 1] = key;
        }

        return comparisons;
    }

    // Bubble Sort
    public static long bubbleSort(int[] array) {
        long comparisons = 0;

        for (int i = 0; i < array.length - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < array.length - 1 - i; j++) {
                comparisons++;

                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }

        return comparisons;
    }

    // Merge Sort
    public static long mergeSort(int[] array) {
        if (array.length < 2) {
            return 0;
        }

        return mergeSort(array, 0, array.length - 1);
    }

    private static long mergeSort(int[] array, int left, int right) {
        if (left >= right) {
            return 0;
        }

        int middle = left + (right - left) / 2;

        long comparisons = 0;
        comparisons += mergeSort(array, left, middle);
        comparisons += mergeSort(array, middle + 1, right);
        comparisons += merge(array, left, middle, right);

        return comparisons;
    }

    private static long merge(
            int[] array, int left, int middle, int right) {

        int[] temp = new int[right - left + 1];

        int i = left;
        int j = middle + 1;
        int k = 0;
        long comparisons = 0;

        while (i <= middle && j <= right) {
            comparisons++;

            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        while (i <= middle) {
            temp[k++] = array[i++];
        }

        while (j <= right) {
            temp[k++] = array[j++];
        }

        for (int p = 0; p < temp.length; p++) {
            array[left + p] = temp[p];
        }

        return comparisons;
    }

    // Quick Sort: last element is the pivot
    public static long quickSort(int[] array) {
        if (array.length < 2) {
            return 0;
        }

        return quickSort(array, 0, array.length - 1);
    }

    private static long quickSort(int[] array, int low, int high) {
        if (low >= high) {
            return 0;
        }

        long comparisons = 0;
        int pivotIndex;

        // Partition and count element comparisons
        int pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            comparisons++;

            if (array[j] <= pivot) {
                i++;

                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        pivotIndex = i + 1;

        comparisons += quickSort(array, low, pivotIndex - 1);
        comparisons += quickSort(array, pivotIndex + 1, high);

        return comparisons;
    }

    // Heap Sort
    public static long heapSort(int[] array) {
        int n = array.length;
        long comparisons = 0;

        // Build a max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            comparisons += heapify(array, n, i);
        }

        // Move the maximum element to the end
        for (int i = n - 1; i > 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            comparisons += heapify(array, i, 0);
        }

        return comparisons;
    }

    private static long heapify(int[] array, int size, int root) {
        long comparisons = 0;
        int largest = root;
        int left = 2 * root + 1;
        int right = 2 * root + 2;

        if (left < size) {
            comparisons++;

            if (array[left] > array[largest]) {
                largest = left;
            }
        }

        if (right < size) {
            comparisons++;

            if (array[right] > array[largest]) {
                largest = right;
            }
        }

        if (largest != root) {
            int temp = array[root];
            array[root] = array[largest];
            array[largest] = temp;

            comparisons += heapify(array, size, largest);
        }

        return comparisons;
    }

    // Generate a random array
    public static int[] generateArray(int size, Random random) {
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100000);
        }

        return array;
    }

    // Check whether the array is sorted
    public static boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }

        return true;
    }
}