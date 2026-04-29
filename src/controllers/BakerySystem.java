package controllers;

import models.BakedGood;
import models.Ingredient;
import models.RecipeEntry;
import utils.MyHashTable;
import utils.MyList;
import utils.MySorter;

import java.io.*;

// Controller for the bakery system
public class BakerySystem implements Serializable {
    private MyHashTable<String, BakedGood> bakedGoods; // Store baked goods
    private MyHashTable<String, Ingredient> ingredients; // Store ingredients

    // System constructor
    public BakerySystem() {
        bakedGoods = new MyHashTable<>();
        ingredients = new MyHashTable<>();
    }

    // Loads sample data
    public void initializeDefaultData() {
        Ingredient flour = new Ingredient("Flour", "White flour", 100);
        Ingredient sugar = new Ingredient("Sugar", "White sugar", 100);
        Ingredient eggs = new Ingredient("Eggs", "Chicken eggs", 100);
        Ingredient butter = new Ingredient("Butter", "Unsalted butter", 100);
        Ingredient chocolate = new Ingredient("Dark Chocolate", "70%", 100);
        Ingredient banana = new Ingredient("Banana", "Fruit", 50);

        addIngredient(flour);
        addIngredient(sugar);
        addIngredient(eggs);
        addIngredient(butter);
        addIngredient(chocolate);
        addIngredient(banana);

        BakedGood cake = new BakedGood("Chocolate Cake", "France", "Milk chocolate cake", "https://ichef.bbci.co.uk/food/ic/food_16x9_1600/recipes/easy_chocolate_cake_31070_16x9.jpg");
        cake.addIngredient(flour, 250);
        cake.addIngredient(sugar, 200);
        cake.addIngredient(eggs, 150);
        cake.addIngredient(butter, 150);
        cake.addIngredient(chocolate, 140);
        addBakedGood(cake);

        BakedGood bread = new BakedGood("French Baguette", "France", "Classic baguette", "https://www.kingarthurbaking.com/sites/default/files/recipe_legacy/8-3-large.jpg");
        bread.addIngredient(flour, 500);
        addBakedGood(bread);

        BakedGood cookies = new BakedGood("Chocolate Chip Cookies", "USA", "Cookies with chocolate chips", "https://sallysbakingaddiction.com/wp-content/uploads/2013/05/classic-chocolate-chip-cookies.jpg");
        cookies.addIngredient(flour, 300);
        cookies.addIngredient(sugar, 200);
        cookies.addIngredient(butter, 150);
        cookies.addIngredient(chocolate, 140);
        addBakedGood(cookies);

        BakedGood bananaBread = new BakedGood("Banana Bread", "UK", "Cake with banana", "https://www.giallozafferano.com/images/227-22768/banana-bread_1200x800.jpg");
        bananaBread.addIngredient(flour, 150);
        bananaBread.addIngredient(sugar, 200);
        bananaBread.addIngredient(eggs, 50);
        bananaBread.addIngredient(banana, 70);
        addBakedGood(bananaBread);
    }

    // Adds an ingredient
    public void addIngredient(Ingredient ingredient) {
        ingredients.put(ingredient.getName(), ingredient);
    }

    // Gets an ingredient by name
    public Ingredient getIngredient(String name) {
        return ingredients.get(name);
    }

    // Removes an ingredient and updates recipes
    public void removeIngredient(String name) {
        ingredients.remove(name); // Remove from master list
        for (BakedGood bg : bakedGoods.values()) {
            MyList<RecipeEntry> recipe = bg.getRecipe();
            for (RecipeEntry rc : recipe) {
                if (rc.getIngredient().getName().equals(name)) {
                    recipe.remove(rc); // Remove from baked good recipe
                    break;
                }
            }
        }
    }

    // Returns all ingredients
    public MyList<Ingredient> getAllIngredients() {
        return ingredients.values();
    }

    // Adds a baked good
    public void addBakedGood(BakedGood bakedGood) {
        bakedGoods.put(bakedGood.getName(), bakedGood);
    }

    // Gets a baked good by name
    public BakedGood getBakedGood(String name) {
        return bakedGoods.get(name);
    }

    // Removes a baked good
    public void removeBakedGood(String name) {
        bakedGoods.remove(name);
    }

    // Returns all baked goods
    public MyList<BakedGood> getAllBakedGoods() {
        return bakedGoods.values();
    }

    // Search baked goods by name
    public MyList<BakedGood> searchBakedGoodsByName(String name) {
        MyList<BakedGood> results = new MyList<>(); // List for results
        if (name == null || name.trim().isEmpty()) return results;
        String firstKeyword = name.trim().split("\\s+")[0].toLowerCase(); // Use first keyword
        for (BakedGood bg : bakedGoods.values()) {
            if (bg.getName().toLowerCase().contains(firstKeyword)) {
                results.add(bg); // Add if name matches
            }
        }
        return results;
    }

    // Search baked goods by description
    public MyList<BakedGood> searchBakedGoodsByDescription(String keyword) {
        MyList<BakedGood> results = new MyList<>();
        if (keyword == null || keyword.trim().isEmpty()) return results;
        String firstKeyword = keyword.trim().split("\\s+")[0].toLowerCase();
        for (BakedGood bg : bakedGoods.values()) {
            if (bg.getDescription().toLowerCase().contains(firstKeyword)) {
                results.add(bg); // Add if description matches
            }
        }
        return results;
    }

    // Search ingredients by name
    public MyList<Ingredient> searchIngredientsByName(String name) {
        MyList<Ingredient> results = new MyList<>();
        if (name == null || name.trim().isEmpty()) return results;
        String firstKeyword = name.trim().split("\\s+")[0].toLowerCase();
        for (Ingredient ing : ingredients.values()) {
            if (ing.getName().toLowerCase().contains(firstKeyword)) {
                results.add(ing); // Add if name matches
            }
        }
        return results;
    }

    // Search ingredients by description
    public MyList<Ingredient> searchIngredientsByDescription(String keyword) {
        MyList<Ingredient> results = new MyList<>();
        if (keyword == null || keyword.trim().isEmpty()) return results;
        String firstKeyword = keyword.trim().split("\\s+")[0].toLowerCase();
        for (Ingredient ing : ingredients.values()) {
            if (ing.getDescription().toLowerCase().contains(firstKeyword)) {
                results.add(ing); // Add if description matches
            }
        }
        return results;
    }

    // Saves data to text file
    public void saveToTextFile(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Save Ingredients
            for (Ingredient ing : ingredients.values()) {
                writer.println("INGREDIENT:" + ing.getName() + "|" + ing.getDescription() + "|" + ing.getCaloriesPer100());
            }
            // Save Baked Goods
            for (BakedGood bg : bakedGoods.values()) {
                writer.println("BAKEDGOOD:" + bg.getName() + "|" + bg.getOrigin() + "|" + bg.getDescription() + "|" + bg.getImageUrl());
                // Save Recipes
                for (RecipeEntry re : bg.getRecipe()) {
                    writer.println("RECIPE:" + bg.getName() + "|" + re.getIngredient().getName() + "|" + re.getQuantity());
                }
            }
        }
    }

    // Loads data from text file
    public static BakerySystem loadFromTextFile(String filename) throws IOException {
        BakerySystem system = new BakerySystem(); // New system instance
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) continue;
                String[] parts = line.split(":", 2);
                if (parts.length < 2) continue;
                String type = parts[0];
                String[] data = parts[1].split("\\|");

                switch (type) {
                    case "INGREDIENT": // Parse ingredient
                        if (data.length == 3) {
                            system.addIngredient(new Ingredient(data[0], data[1], Double.parseDouble(data[2])));
                        }
                        break;
                    case "BAKEDGOOD": // Parse baked good
                        if (data.length == 4) {
                            system.addBakedGood(new BakedGood(data[0], data[1], data[2], data[3]));
                        }
                        break;
                    case "RECIPE": // Parse recipe entry
                        if (data.length == 3) {
                            BakedGood bg = system.getBakedGood(data[0]);
                            Ingredient ing = system.getIngredient(data[1]);
                            if (bg != null && ing != null) {
                                bg.addIngredient(ing, Double.parseDouble(data[2]));
                            }
                        }
                        break;
                }
            }
        }
        return system;
    }

    // Sorts baked goods by name
    public void sortBakedGoodsByName(MyList<BakedGood> list) {
        MySorter.quickSort(list, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
    }

    // Sorts baked goods by calories
    public void sortBakedGoodsByCalories(MyList<BakedGood> list) {
        MySorter.quickSort(list, (a, b) -> Double.compare(a.getTotalCalories(), b.getTotalCalories()));
    }

    // Sorts ingredients by name
    public void sortIngredientsByName(MyList<Ingredient> list) {
        MySorter.quickSort(list, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
    }

    // Sorts ingredients by calories
    public void sortIngredientsByCalories(MyList<Ingredient> list) {
        MySorter.quickSort(list, (a, b) -> Double.compare(a.getCaloriesPer100(), b.getCaloriesPer100()));
    }
}
