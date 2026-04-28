package controllers;

import models.BakedGood;
import models.Ingredient;
import models.RecipeEntry;
import utils.MyHashTable;
import utils.MyList;
import utils.MySorter;

import java.io.*;

public class BakerySystem implements Serializable {
    private MyHashTable<String, BakedGood> bakedGoods;
    private MyHashTable<String, Ingredient> ingredients;

    public BakerySystem() {
        bakedGoods = new MyHashTable<>();
        ingredients = new MyHashTable<>();
    }

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

    public void addIngredient(Ingredient ingredient) {
        ingredients.put(ingredient.getName(), ingredient);
    }

    public Ingredient getIngredient(String name) {
        return ingredients.get(name);
    }

    public void removeIngredient(String name) {
        ingredients.remove(name);
        for (BakedGood bg : bakedGoods.values()) {
            MyList<RecipeEntry> recipe = bg.getRecipe();
            for (RecipeEntry rc : recipe) {
                if (rc.getIngredient().getName().equals(name)) {
                    recipe.remove(rc);
                    break;
                }
            }
        }
    }

    public MyList<Ingredient> getAllIngredients() {
        return ingredients.values();
    }


    public void addBakedGood(BakedGood bakedGood) {
        bakedGoods.put(bakedGood.getName(), bakedGood);
    }

    public BakedGood getBakedGood(String name) {
        return bakedGoods.get(name);
    }

    public void removeBakedGood(String name) {
        bakedGoods.remove(name);
    }

    public MyList<BakedGood> getAllBakedGoods() {
        return bakedGoods.values();
    }

    //search functions
    public MyList<BakedGood> searchBakedGoodsByName(String name) {
        MyList<BakedGood> results = new MyList<>();
        BakedGood bg = bakedGoods.get(name);
        if (bg != null) {
            results.add(bg);
        }
        return results;
    }

    public MyList<BakedGood> searchBakedGoodsByDescription(String keyword) {
        MyList<BakedGood> results = new MyList<>();
        for (BakedGood bg : bakedGoods.values()) {
            if (bg.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(bg);
            }
        }
        return results;
    }

    public MyList<Ingredient> searchIngredientsByName(String name) {
        MyList<Ingredient> results = new MyList<>();
        Ingredient ing = ingredients.get(name);
        if (ing != null) {
            results.add(ing);
        }
        return results;
    }

    public void sortBakedGoodsByName(MyList<BakedGood> list) {
        MySorter.quickSort(list, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
    }

    public void sortBakedGoodsByCalories(MyList<BakedGood> list) {
        MySorter.quickSort(list, (a, b) -> Double.compare(a.getTotalCalories(), b.getTotalCalories()));
    }
}
