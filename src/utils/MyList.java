package utils;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

// Custom linked list implementation
public class MyList<T> implements Iterable<T>, Serializable {
    private Node<T> head; // Head of the list
    private int size; // Size of the list

    // Internal node class
    private static class Node<T> implements Serializable {
        T data; // Node data
        Node<T> next; // Next node reference

        // Node constructor
        Node(T data) {
            this.data = data;
        }
    }

    // Adds an element to the list
    public void add(T data) {
        Node<T> newNode = new Node<>(data); // Create new node
        if (head == null) {
            head = newNode; // Set head if empty
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next; // Find end
            }
            current.next = newNode; // Link new node
        }
        size++; // Increment size
    }

    // Removes an element from the list
    public void remove(T data) {
        if (head == null) return; // Empty list
        if (head.data.equals(data)) {
            head = head.next; // Remove head
            size--; // Decrement size
            return;
        }
        Node<T> current = head;
        while (current.next != null && !current.next.data.equals(data)) {
            current = current.next; // Find node
        }
        if (current.next != null) {
            current.next = current.next.next; // Unlink node
            size--; // Decrement size
        }
    }

    // Gets element at index
    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next; // Traverse to index
        }
        return current.data; // Return data
    }

    // Returns current size
    public int size() {
        return size;
    }

    // Checks if list is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Clears the list
    public void clear() {
        head = null;
        size = 0;
    }

    // Returns an iterator for the list
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head; // Start at head

            // Checks if next exists
            @Override
            public boolean hasNext() {
                return current != null;
            }

            // Returns next element
            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T data = current.data; // Get data
                current = current.next; // Move forward
                return data;
            }
        };
    }
}
