package models;

import utils.MyList;
import java.io.Serializable;

// Represents a baked good item
public class BakedGood implements Serializable {
    private String name; // Name of the baked good
    private String origin; // Country of origin
    private String description; // Description text
    private String imageUrl; // URL to image
    private MyList<RecipeEntry> recipe; // List of ingredients

    // Constructor for BakedGood
    public BakedGood(String name, String origin, String description, String imageUrl) {
        this.name = name;
        this.origin = origin;
        this.description = description;
        this.imageUrl = imageUrl;
        this.recipe = new MyList<>();
    }

    // Getter for name
    public String getName() { return name; }
    // Setter for name
    public void setName(String name) { this.name = name; }

    // Getter for origin
    public String getOrigin() { return origin; }
    // Setter for origin
    public void setOrigin(String origin) { this.origin = origin; }

    // Getter for description
    public String getDescription() { return description; }
    // Setter for description
    public void setDescription(String description) { this.description = description; }

    // Getter for imageUrl
    public String getImageUrl() { return imageUrl; }
    // Setter for imageUrl
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    // Getter for recipe list
    public MyList<RecipeEntry> getRecipe() { return recipe; }

    // Adds an ingredient to the recipe
    public void addIngredient(Ingredient ingredient, double quantity) {
        recipe.add(new RecipeEntry(ingredient, quantity));
    }

    // Calculates total calories for this baked good
    public double getTotalCalories() {
        double total = 0; // Initialize total
        for (RecipeEntry component : recipe) {
            total += component.getCalories(); // Add component calories
        }
        return total; // Return sum
    }

    // Returns a string representation
    @Override
    public String toString() {
        return name + " from " + origin + " (" + getTotalCalories() + " kcal)";
    }
}
