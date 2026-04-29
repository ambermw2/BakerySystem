package models;

import java.io.Serializable;

// Represents an entry in a recipe
public class RecipeEntry implements Serializable {
    private Ingredient ingredient; // The ingredient
    private double quantity; // The quantity in grams/ml

    // Constructor for RecipeEntry
    public RecipeEntry(Ingredient ingredient, double quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    // Getter for ingredient
    public Ingredient getIngredient() { return ingredient; }
    // Getter for quantity
    public double getQuantity() { return quantity; }

    // Calculates calories for this quantity
    public double getCalories() {
        return (ingredient.getCaloriesPer100() / 100.0) * quantity;
    }
}
