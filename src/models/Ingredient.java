package models;

import java.io.Serializable;

// Represents an ingredient
public class Ingredient implements Serializable {
    private String name; // Ingredient name
    private String description; // Description text
    private double caloriesPer100; // Calories per 100g

    // Constructor for Ingredient
    public Ingredient(String name, String description, double caloriesPer100) {
        this.name = name;
        this.description = description;
        this.caloriesPer100 = caloriesPer100;
    }

    // Getter for name
    public String getName() { return name; }
    // Setter for name
    public void setName(String name) { this.name = name; }

    // Getter for description
    public String getDescription() { return description; }
    // Setter for description
    public void setDescription(String description) { this.description = description; }

    // Getter for calories
    public double getCaloriesPer100() { return caloriesPer100; }
    // Setter for calories
    public void setCaloriesPer100(double caloriesPer100) { this.caloriesPer100 = caloriesPer100; }

    // String representation of ingredient
    @Override
    public String toString() {
        return name + " (" + caloriesPer100 + " kcal/100g)";
    }
}
