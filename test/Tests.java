import org.junit.jupiter.api.Test;
import utils.MyHashTable;
import utils.MyList;
import utils.MySorter;

import static org.junit.jupiter.api.Assertions.*;


// Test suite for Bakery System
public class Tests {
    // Tests hash table put and get
    @Test
    public void testPutAndGet() {
        MyHashTable<String, Integer> map = new MyHashTable<>();
        map.put("One", 1);
        map.put("Two", 2);
        assertEquals(1, map.get("One"));
        assertEquals(2, map.get("Two"));
    }

    // Tests hash table value update
    @Test
    public void testUpdate() {
        MyHashTable<String, Integer> map = new MyHashTable<>();
        map.put("One", 1);
        map.put("One", 11);
        assertEquals(11, map.get("One"));
    }

    // Tests hash table element removal
    @Test
    public void testRemove() {
        MyHashTable<String, Integer> map = new MyHashTable<>();
        map.put("One", 1);
        map.remove("One");
        assertNull(map.get("One"));
        assertEquals(0, map.size());
    }

    // Tests list add and size
    @Test
    public void testAddAndSize() {
        MyList<String> list = new MyList<>();
        list.add("Apple");
        list.add("Banana");
        assertEquals(2, list.size());
    }

    // Tests list element retrieval
    @Test
    public void testGet() {
        MyList<String> list = new MyList<>();
        list.add("Apple");
        list.add("Banana");
        assertEquals("Apple", list.get(0));
        assertEquals("Banana", list.get(1));
    }

    // Tests list clearing
    @Test
    public void testClear() {
        MyList<String> list = new MyList<>();
        list.add("Apple");
        list.clear();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    // Tests quicksort utility
    @Test
    public void testQuickSort() {
        MyList<Integer> list = new MyList<>();
        list.add(5);
        list.add(2);
        list.add(9);
        list.add(1);
        list.add(5);

        MySorter.quickSort(list, Integer::compareTo); // Perform sort

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(5, list.get(2));
        assertEquals(5, list.get(3));
        assertEquals(9, list.get(4));
    }

    // Tests baked good calorie calculation
    @Test
    public void testBakedGoodCalories() {
        models.Ingredient flour = new models.Ingredient("Flour", "Test", 100);
        models.BakedGood cake = new models.BakedGood("Cake", "Test", "Test", "url");
        cake.addIngredient(flour, 200); // 200g of 100kcal/100g = 200kcal
        assertEquals(200.0, cake.getTotalCalories(), 0.01);
    }

    // Tests search functionality
    @Test
    public void testBakerySystemSearch() {
        controllers.BakerySystem system = new controllers.BakerySystem();
        models.Ingredient flour = new models.Ingredient("Flour", "White wheat flour", 100);
        system.addIngredient(flour);

        // Name search (single keyword)
        MyList<models.Ingredient> results = system.searchIngredientsByName("Flour");
        assertEquals(1, results.size());
        assertEquals("Flour", results.get(0).getName());

        // Name search (multi keyword, should only use first)
        results = system.searchIngredientsByName("Flour Power"); // uses "Flour"
        assertEquals(1, results.size());

        // Name search (case insensitive + partial)
        results = system.searchIngredientsByName("flo");
        assertEquals(1, results.size());
        
        // Description search (single keyword)
        results = system.searchIngredientsByDescription("white");
        assertEquals(1, results.size());
        assertEquals("Flour", results.get(0).getName());

        // Description search (multi keyword)
        results = system.searchIngredientsByDescription("white sugar");
        assertEquals(1, results.size());
    }

    // Tests text-based persistence
    @Test
    public void testTextPersistence() throws java.io.IOException {
        controllers.BakerySystem system = new controllers.BakerySystem();
        models.Ingredient ing = new models.Ingredient("Sugar", "White sugar", 100);
        system.addIngredient(ing);
        models.BakedGood bg = new models.BakedGood("Cookie", "USA", "Sweet", "url");
        bg.addIngredient(ing, 50);
        system.addBakedGood(bg);

        String testFile = "test_data.txt";
        system.saveToTextFile(testFile); // Save data

        controllers.BakerySystem loadedSystem = controllers.BakerySystem.loadFromTextFile(testFile); // Load data
        assertNotNull(loadedSystem.getIngredient("Sugar"));
        assertNotNull(loadedSystem.getBakedGood("Cookie"));
        assertEquals(1, loadedSystem.getBakedGood("Cookie").getRecipe().size());
        assertEquals(50.0, loadedSystem.getBakedGood("Cookie").getTotalCalories(), 0.01);

        new java.io.File(testFile).delete(); // Cleanup
    }
}
