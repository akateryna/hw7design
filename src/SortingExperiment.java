
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class SortingExperiment {

    private static final int REPEATS = 5;

    public static void main(String[] args) {

        int[] sizes = {
                1000,
                5000,
                10000,
                20000,
                50000,
                75000,
                100000
        };

        Random random = new Random(42);

        try (PrintWriter writer =
                     new PrintWriter("sorting_results.csv")) {

            writer.println(
                    "Size,MergeTimeMs,QuickTimeMs,HeapTimeMs," +
                            "MergeComparisons,QuickComparisons,HeapComparisons"
            );

            System.out.println(
                    "Size\tMerge(ms)\tQuick(ms)\tHeap(ms)"
            );

            for (int size : sizes) {

                // One original array for this size
                int[] original =
                        SortingComparison.generateArray(size, random);

                // Count comparisons separately from timing
                int[] mergeCountArray =
                        Arrays.copyOf(original, size);
                int[] quickCountArray =
                        Arrays.copyOf(original, size);
                int[] heapCountArray =
                        Arrays.copyOf(original, size);

                long mergeOperations =
                        SortingComparison.mergeSort(mergeCountArray);
                long quickOperations =
                        SortingComparison.quickSort(quickCountArray);
                long heapOperations =
                        SortingComparison.heapSort(heapCountArray);

                if (!SortingComparison.isSorted(mergeCountArray)
                        || !SortingComparison.isSorted(quickCountArray)
                        || !SortingComparison.isSorted(heapCountArray)) {
                    throw new IllegalStateException(
                            "Sorting failed for size " + size
                    );
                }

                // Measure time separately, without operation counting
                long mergeTotal = 0;
                long quickTotal = 0;
                long heapTotal = 0;

                for (int run = 0; run < REPEATS; run++) {

                    int[] array;

                    array = Arrays.copyOf(original, size);
                    long start = System.nanoTime();
                    SortingComparison.mergeSort(array);
                    mergeTotal += System.nanoTime() - start;

                    array = Arrays.copyOf(original, size);
                    start = System.nanoTime();
                    SortingComparison.quickSort(array);
                    quickTotal += System.nanoTime() - start;

                    array = Arrays.copyOf(original, size);
                    start = System.nanoTime();
                    SortingComparison.heapSort(array);
                    heapTotal += System.nanoTime() - start;
                }

                double mergeMs =
                        mergeTotal / (double) REPEATS / 1_000_000.0;
                double quickMs =
                        quickTotal / (double) REPEATS / 1_000_000.0;
                double heapMs =
                        heapTotal / (double) REPEATS / 1_000_000.0;

                System.out.printf(
                        "%d\t%.3f\t%.3f\t%.3f%n",
                        size, mergeMs, quickMs, heapMs
                );

                writer.printf(
                        java.util.Locale.US,
                        "%d,%.6f,%.6f,%.6f,%d,%d,%d%n",
                        size,
                        mergeMs, quickMs, heapMs,
                        mergeOperations, quickOperations, heapOperations
                );
            }

            System.out.println(
                    "\nResults saved to sorting_results.csv"
            );

        } catch (IOException e) {
            System.err.println(
                    "Could not write results: " + e.getMessage()
            );
        }
    }
}