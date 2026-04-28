package models;

import java.io.Serializable;

public class RecipeEntry implements Serializable {
    private Ingredient ingredient;
    private double quantity;

    public RecipeEntry(Ingredient ingredient, double quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public Ingredient getIngredient() { return ingredient; }
    public double getQuantity() { return quantity; }

    public double getCalories() {
        return (ingredient.getCaloriesPer100() / 100.0) * quantity;
    }
}
