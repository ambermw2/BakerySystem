package models;

import java.io.Serializable;

public class Ingredient implements Serializable {
    private String name;
    private String description;
    private double caloriesPer100; // kcal per 100g/ml

    public Ingredient(String name, String description, double caloriesPer100) {
        this.name = name;
        this.description = description;
        this.caloriesPer100 = caloriesPer100;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getCaloriesPer100() { return caloriesPer100; }
    public void setCaloriesPer100(double caloriesPer100) { this.caloriesPer100 = caloriesPer100; }

    @Override
    public String toString() {
        return name + " (" + caloriesPer100 + " kcal/100g)";
    }
}
