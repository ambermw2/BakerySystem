package utils;

import java.util.Comparator;

public class MySorter {

    public static <T> void quickSort(MyList<T> list, Comparator<T> comparator) {
        if (list == null || list.size() <= 1) return;
        T[] array = toArray(list);
        quickSort(array, 0, array.length - 1, comparator);
        updateList(list, array);
    }

    private static <T> void quickSort(T[] array, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pi = partition(array, low, high, comparator);
            quickSort(array, low, pi - 1, comparator);
            quickSort(array, pi + 1, high, comparator);
        }
    }

    private static <T> int partition(T[] array, int low, int high, Comparator<T> comparator) {
        T pivot = array[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (comparator.compare(array[j], pivot) <= 0) {
                i++;
                T temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        T temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }

    @SuppressWarnings("unchecked")
    private static <T> T[] toArray(MyList<T> list) {
        T[] array = (T[]) new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    private static <T> void updateList(MyList<T> list, T[] array) {
        list.clear();
        for (T item : array) {
            list.add(item);
        }
    }
}
