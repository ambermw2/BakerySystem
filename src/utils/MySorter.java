package utils;

import java.util.Comparator;

// Utility class for sorting
public class MySorter {

    // Sorts a list using quicksort
    public static <T> void quickSort(MyList<T> list, Comparator<T> comparator) {
        if (list == null || list.size() <= 1) return; // Base case
        T[] array = toArray(list); // Convert to array
        quickSort(array, 0, array.length - 1, comparator); // Perform sort
        updateList(list, array); // Update original list
    }

    // Recursive quicksort implementation
    private static <T> void quickSort(T[] array, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pi = partition(array, low, high, comparator); // Partition index
            quickSort(array, low, pi - 1, comparator); // Left side
            quickSort(array, pi + 1, high, comparator); // Right side
        }
    }

    // Partitions the array for quicksort
    private static <T> int partition(T[] array, int low, int high, Comparator<T> comparator) {
        T pivot = array[high]; // Pivot element
        int i = (low - 1); // Index of smaller element
        for (int j = low; j < high; j++) {
            if (comparator.compare(array[j], pivot) <= 0) {
                i++; // Move index
                T temp = array[i]; // Swap
                array[i] = array[j];
                array[j] = temp;
            }
        }
        T temp = array[i + 1]; // Move pivot to correct place
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1; // Return partition index
    }

    // Converts MyList to an array
    @SuppressWarnings("unchecked")
    private static <T> T[] toArray(MyList<T> list) {
        T[] array = (T[]) new Object[list.size()]; // Create array
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i); // Copy elements
        }
        return array; // Return array
    }

    // Updates MyList from an array
    private static <T> void updateList(MyList<T> list, T[] array) {
        list.clear(); // Clear existing
        for (T item : array) {
            list.add(item); // Add all from array
        }
    }
}
