import org.junit.jupiter.api.Test;
import utils.MyHashTable;
import utils.MyList;
import utils.MySorter;

import static org.junit.jupiter.api.Assertions.*;


public class Tests {
    @Test
    public void testPutAndGet() {
        MyHashTable<String, Integer> map = new MyHashTable<>();
        map.put("One", 1);
        map.put("Two", 2);
        assertEquals(1, map.get("One"));
        assertEquals(2, map.get("Two"));
    }

    @Test
    public void testUpdate() {
        MyHashTable<String, Integer> map = new MyHashTable<>();
        map.put("One", 1);
        map.put("One", 11);
        assertEquals(11, map.get("One"));
    }

    @Test
    public void testRemove() {
        MyHashTable<String, Integer> map = new MyHashTable<>();
        map.put("One", 1);
        map.remove("One");
        assertNull(map.get("One"));
        assertEquals(0, map.size());
    }

    @Test
    public void testAddAndSize() {
        MyList<String> list = new MyList<>();
        list.add("Apple");
        list.add("Banana");
        assertEquals(2, list.size());
    }

    @Test
    public void testGet() {
        MyList<String> list = new MyList<>();
        list.add("Apple");
        list.add("Banana");
        assertEquals("Apple", list.get(0));
        assertEquals("Banana", list.get(1));
    }

    @Test
    public void testClear() {
        MyList<String> list = new MyList<>();
        list.add("Apple");
        list.clear();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testQuickSort() {
        MyList<Integer> list = new MyList<>();
        list.add(5);
        list.add(2);
        list.add(9);
        list.add(1);
        list.add(5);

        MySorter.quickSort(list, Integer::compareTo);

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(5, list.get(2));
        assertEquals(5, list.get(3));
        assertEquals(9, list.get(4));
    }
}
