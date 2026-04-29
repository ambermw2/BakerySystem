package utils;

import java.io.Serializable;

// Custom hash table implementation
public class MyHashTable<K, V> implements Serializable {
    // Internal entry class
    private static class Entry<K, V> implements Serializable {
        K key; // Entry key
        V value; // Entry value
        Entry<K, V> next; // Next entry for collision handling

        // Entry constructor
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Entry<K, V>[] table; // Table array
    private int size; // Number of entries
    private static final int DEFAULT_CAPACITY = 16; // Default size

    // Table constructor
    @SuppressWarnings("unchecked")
    public MyHashTable() {
        table = new Entry[DEFAULT_CAPACITY];
    }

    // Custom hash function
    private int hash(K key) {
        if (key == null) return 0;
        String s = key.toString();
        int h = 0;
        for (int i = 0; i < s.length(); i++) {
            h = 31 * h + s.charAt(i); // Simple polynomial hash
        }
        return Math.abs(h) % table.length; // Map to table index
    }

    // Puts a key-value pair into the table
    public void put(K key, V value) {
        int index = hash(key); // Get index
        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value; // Update if key exists
                return;
            }
            current = current.next;
        }
        Entry<K, V> newEntry = new Entry<>(key, value); // Create new entry
        newEntry.next = table[index]; // Chain to existing list
        table[index] = newEntry; // Update head
        size++; // Increment size
    }

    // Gets a value by key
    public V get(K key) {
        int index = hash(key); // Get index
        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value; // Return found value
            }
            current = current.next;
        }
        return null; // Not found
    }

    // Removes an entry by key
    public void remove(K key) {
        int index = hash(key); // Get index
        Entry<K, V> current = table[index];
        Entry<K, V> prev = null;
        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    table[index] = current.next; // Remove head
                } else {
                    prev.next = current.next; // Unlink entry
                }
                size--; // Decrement size
                return;
            }
            prev = current;
            current = current.next;
        }
    }

    // Returns number of entries
    public int size() {
        return size;
    }

    // Returns all values as a list
    public MyList<V> values() {
        MyList<V> list = new MyList<>(); // Create result list
        for (Entry<K, V> entry : table) {
            Entry<K, V> current = entry;
            while (current != null) {
                list.add(current.value); // Add every value
                current = current.next;
            }
        }
        return list; // Return list
    }
}
