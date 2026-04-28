package models;

import utils.MyList;
import java.io.Serializable;

public class BakedGood implements Serializable {
    private String name;
    private String origin;
    private String description;
    private String imageUrl;
    private MyList<RecipeEntry> recipe;

    public BakedGood(String name, String origin, String description, String imageUrl) {
        this.name = name;
        this.origin = origin;
        this.description = description;
        this.imageUrl = imageUrl;
        this.recipe = new MyList<>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public MyList<RecipeEntry> getRecipe() { return recipe; }

    public void addIngredient(Ingredient ingredient, double quantity) {
        recipe.add(new RecipeEntry(ingredient, quantity));
    }

    public double getTotalCalories() {
        double total = 0;
        for (RecipeEntry component : recipe) {
            total += component.getCalories();
        }
        return total;
    }

    @Override
    public String toString() {
        return name + " from " + origin + " (" + getTotalCalories() + " kcal)";
    }
}
