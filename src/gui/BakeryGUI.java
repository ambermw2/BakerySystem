package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import controllers.BakerySystem;
import models.BakedGood;
import models.Ingredient;
import utils.MyList;
import utils.MySorter;

// Main GUI for the Bakery System
public class BakeryGUI extends Application {
    private BakerySystem system; // System reference
    private static final String DATA_FILE = "bakery_data.txt"; // Persistence file
    private ScrollPane contentArea; // Main content area

    // Starts the JavaFX application
    @Override
    public void start(Stage primaryStage) {
        system = new BakerySystem(); // Initialize system

        BorderPane root = new BorderPane(); // Main layout
        root.setPadding(new Insets(10));

        VBox menu = new VBox(10); // Sidebar menu
        menu.setPadding(new Insets(10));
        Button btnViewBakedGoods = new Button("View Baked Goods");
        Button btnViewIngredients = new Button("View Ingredients");
        Button btnAddBakedGood = new Button("Add Baked Good");
        Button btnAddIngredient = new Button("Add Ingredient");
        Button btnSearch = new Button("Search");
        Button btnSave = new Button("Save Data");
        Button btnLoad = new Button("Load Data");

        // Set buttons to fill width
        btnViewBakedGoods.setMaxWidth(Double.MAX_VALUE);
        btnViewIngredients.setMaxWidth(Double.MAX_VALUE);
        btnAddBakedGood.setMaxWidth(Double.MAX_VALUE);
        btnAddIngredient.setMaxWidth(Double.MAX_VALUE);
        btnSearch.setMaxWidth(Double.MAX_VALUE);
        btnSave.setMaxWidth(Double.MAX_VALUE);
        btnLoad.setMaxWidth(Double.MAX_VALUE);

        menu.getChildren().addAll(btnViewBakedGoods, btnViewIngredients, btnAddBakedGood, btnAddIngredient, btnSearch, btnSave, btnLoad);
        root.setLeft(menu); // Place menu on left

        contentArea = new ScrollPane(); // Content scroll area
        contentArea.setFitToWidth(true);
        root.setCenter(contentArea);

        // Map buttons to actions
        btnViewBakedGoods.setOnAction(e -> showBakedGoodsList());
        btnViewIngredients.setOnAction(e -> showIngredientsList());
        btnAddBakedGood.setOnAction(e -> showAddBakedGoodForm());
        btnAddIngredient.setOnAction(e -> showAddIngredientForm());
        btnSearch.setOnAction(e -> showSearchPanel());
        btnSave.setOnAction(e -> saveData());
        btnLoad.setOnAction(e -> loadData());

        primaryStage.setTitle("Baking Information System"); // Set window title
        primaryStage.setScene(new Scene(root, 1000, 700));
        
        Label welcomeLabel = new Label("Welcome to the Baking Information System.\nPlease load data from the sidebar to begin.");
        welcomeLabel.setStyle("-fx-font-size: 16px; -fx-padding: 20px;");
        contentArea.setContent(welcomeLabel); // Set initial view

        primaryStage.show(); // Display window
    }

    // Displays the list of baked goods
    private void showBakedGoodsList() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Label title = new Label("Baked Goods");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        HBox sortBox = new HBox(10); // Sorting controls
        Button sortName = new Button("Sort by Name");
        Button sortCal = new Button("Sort by Calories");
        sortBox.getChildren().addAll(new Label("Sort: "), sortName, sortCal);

        ListView<BakedGood> listView = new ListView<>(); // List display
        updateBakedGoodsList(listView, system.getAllBakedGoods());

        // Sort by name action
        sortName.setOnAction(e -> {
            MyList<BakedGood> list = system.getAllBakedGoods();
            system.sortBakedGoodsByName(list);
            updateBakedGoodsList(listView, list);
        });

        // Sort by calories action
        sortCal.setOnAction(e -> {
            MyList<BakedGood> list = system.getAllBakedGoods();
            system.sortBakedGoodsByCalories(list);
            updateBakedGoodsList(listView, list);
        });

        // Click to view details
        listView.setOnMouseClicked(e -> {
            BakedGood selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showBakedGoodDetails(selected);
            }
        });

        vbox.getChildren().addAll(title, sortBox, listView);
        contentArea.setContent(vbox);
    }

    // Updates the baked goods ListView
    private void updateBakedGoodsList(ListView<BakedGood> listView, MyList<BakedGood> list) {
        listView.getItems().clear();
        for (BakedGood bg : list) {
            listView.getItems().add(bg); // Add each item
        }
    }

    // Displays the list of ingredients
    private void showIngredientsList() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        Label title = new Label("Ingredients");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ListView<Ingredient> listView = new ListView<>();
        for (Ingredient ing : system.getAllIngredients()) {
            listView.getItems().add(ing); // Add each ingredient
        }

        // Click to view details
        listView.setOnMouseClicked(e -> {
            Ingredient selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showIngredientDetails(selected);
            }
        });

        vbox.getChildren().addAll(title, listView);
        contentArea.setContent(vbox);
    }

    // Displays details for a single ingredient
    private void showIngredientDetails(Ingredient ing) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        TextField nameField = new TextField(ing.getName());
        TextField descField = new TextField(ing.getDescription());
        TextField calField = new TextField(String.valueOf(ing.getCaloriesPer100()));

        vbox.getChildren().addAll(
                new Label("Name:"), nameField,
                new Label("Description:"), descField,
                new Label("Calories (per 100g):"), calField
        );

        Button updateBtn = new Button("Update Ingredient"); // Update action
        updateBtn.setOnAction(e -> {
            try {
                String oldName = ing.getName();
                ing.setName(nameField.getText());
                ing.setDescription(descField.getText());
                ing.setCaloriesPer100(Double.parseDouble(calField.getText()));

                if (!oldName.equals(ing.getName())) {
                    system.removeIngredient(oldName); // Re-map if name changed
                    system.addIngredient(ing);
                }
                showIngredientsList();
            } catch (Exception ex) {
                showError("Invalid input.");
            }
        });

        Button deleteBtn = new Button("Delete Ingredient"); // Delete action
        deleteBtn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
        deleteBtn.setOnAction(e -> {
            system.removeIngredient(ing.getName());
            showIngredientsList();
        });

        vbox.getChildren().addAll(updateBtn, deleteBtn);

        Label usedInLabel = new Label("Used in Baked Goods:"); // Drill down
        usedInLabel.setStyle("-fx-font-weight: bold;");
        vbox.getChildren().add(usedInLabel);

        ListView<BakedGood> usedInList = new ListView<>();
        for (BakedGood bg : system.getAllBakedGoods()) {
            for (var entry : bg.getRecipe()) {
                if (entry.getIngredient().getName().equals(ing.getName())) {
                    usedInList.getItems().add(bg); // Add if used here
                    break;
                }
            }
        }
        usedInList.setOnMouseClicked(e -> {
            BakedGood selected = usedInList.getSelectionModel().getSelectedItem();
            if (selected != null) showBakedGoodDetails(selected);
        });
        vbox.getChildren().add(usedInList);

        contentArea.setContent(vbox);
    }

    // Shows form to add a new ingredient
    private void showAddIngredientForm() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nameField = new TextField();
        TextField descField = new TextField();
        TextField calField = new TextField();

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descField, 1, 1);
        grid.add(new Label("Calories (per 100g):"), 0, 2);
        grid.add(calField, 1, 2);

        Button submit = new Button("Add Ingredient"); // Submit action
        submit.setOnAction(e -> {
            try {
                String name = nameField.getText();
                String desc = descField.getText();
                double cal = Double.parseDouble(calField.getText());
                system.addIngredient(new Ingredient(name, desc, cal)); // Add to system
                showIngredientsList();
            } catch (Exception ex) {
                showError("Invalid input. Please check your values.");
            }
        });
        grid.add(submit, 1, 3);
        contentArea.setContent(grid);
    }

    // Shows form to add a new baked good
    private void showAddBakedGoodForm() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nameField = new TextField();
        TextField originField = new TextField();
        TextField descField = new TextField();
        TextField urlField = new TextField();

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Origin:"), 0, 1);
        grid.add(originField, 1, 1);
        grid.add(new Label("Description:"), 0, 2);
        grid.add(descField, 1, 2);
        grid.add(new Label("Image URL:"), 0, 3);
        grid.add(urlField, 1, 3);

        Button submit = new Button("Add Baked Good"); // Submit action
        submit.setOnAction(e -> {
            String name = nameField.getText();
            String origin = originField.getText();
            String desc = descField.getText();
            String url = urlField.getText();
            if (!name.isEmpty()) {
                system.addBakedGood(new BakedGood(name, origin, desc, url)); // Add to system
                showBakedGoodsList();
            }
        });
        grid.add(submit, 1, 4);
        contentArea.setContent(grid);
    }

    // Displays the search panel
    private void showSearchPanel() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        TextField searchField = new TextField();
        searchField.setPromptText("Enter search term...");

        HBox searchOps = new HBox(10); // Search options
        Button btnSearchName = new Button("Search Baked Goods (Name)");
        Button btnSearchDesc = new Button("Search Baked Goods (Desc)");
        Button btnSearchIngName = new Button("Search Ingredients (Name)");
        Button btnSearchIngDesc = new Button("Search Ingredients (Desc)");
        searchOps.getChildren().addAll(btnSearchName, btnSearchDesc, btnSearchIngName, btnSearchIngDesc);

        ListView<Object> resultsList = new ListView<>(); // Results display

        // Search baked goods by name
        btnSearchName.setOnAction(e -> {
            String term = searchField.getText();
            MyList<BakedGood> results = system.searchBakedGoodsByName(term);
            system.sortBakedGoodsByName(results);
            updateResultsList(resultsList, results);
        });

        // Search baked goods by description
        btnSearchDesc.setOnAction(e -> {
            String term = searchField.getText();
            MyList<BakedGood> results = system.searchBakedGoodsByDescription(term);
            system.sortBakedGoodsByName(results);
            updateResultsList(resultsList, results);
        });

        // Search ingredients by name
        btnSearchIngName.setOnAction(e -> {
            String term = searchField.getText();
            MyList<Ingredient> results = system.searchIngredientsByName(term);
            system.sortIngredientsByName(results);
            updateResultsList(resultsList, results);
        });

        // Search ingredients by description
        btnSearchIngDesc.setOnAction(e -> {
            String term = searchField.getText();
            MyList<Ingredient> results = system.searchIngredientsByDescription(term);
            system.sortIngredientsByName(results);
            updateResultsList(resultsList, results);
        });

        // Result selection action
        resultsList.setOnMouseClicked(e -> {
            Object selected = resultsList.getSelectionModel().getSelectedItem();
            if (selected instanceof BakedGood) showBakedGoodDetails((BakedGood) selected);
            else if (selected instanceof Ingredient) showIngredientDetails((Ingredient) selected);
        });

        vbox.getChildren().addAll(new Label("Search"), searchField, searchOps, resultsList);
        contentArea.setContent(vbox);
    }

    // Updates the results ListView
    private void updateResultsList(ListView<Object> listView, MyList<?> list) {
        listView.getItems().clear();
        for (Object obj : list) {
            listView.getItems().add(obj); // Add each result
        }
    }

    // Displays details for a baked good
    private void showBakedGoodDetails(BakedGood bg) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        TextField nameField = new TextField(bg.getName());
        TextField originField = new TextField(bg.getOrigin());
        TextField descField = new TextField(bg.getDescription());
        TextField urlField = new TextField(bg.getImageUrl());

        vbox.getChildren().addAll(
                new Label("Name:"), nameField,
                new Label("Origin:"), originField,
                new Label("Description:"), descField,
                new Label("Image URL:"), urlField
        );

        Button updateBtn = new Button("Update Details"); // Update action
        updateBtn.setOnAction(e -> {
            String oldName = bg.getName();
            bg.setName(nameField.getText());
            bg.setOrigin(originField.getText());
            bg.setDescription(descField.getText());
            bg.setImageUrl(urlField.getText());
            if (!oldName.equals(bg.getName())) {
                system.removeBakedGood(oldName); // Re-map if name changed
                system.addBakedGood(bg);
            }
            showBakedGoodDetails(bg);
        });
        vbox.getChildren().add(updateBtn);

        Label caloriesLabel = new Label("Total Calories: " + bg.getTotalCalories() + " kcal");
        caloriesLabel.setStyle("-fx-font-weight: bold;");
        vbox.getChildren().add(caloriesLabel);

        Label ingredientsTitle = new Label("Ingredients:");
        ingredientsTitle.setStyle("-fx-font-weight: bold;");

        ListView<models.RecipeEntry> ingList = new ListView<>(); // Recipe items
        for (var comp : bg.getRecipe()) {
            ingList.getItems().add(comp);
        }
        ingList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(models.RecipeEntry item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(item.getIngredient().getName() + " - " + item.getQuantity() + "g (" + item.getCalories() + " kcal)");
            }
        });

        Button removeIngBtn = new Button("Remove Selected Ingredient"); // Remove from recipe
        removeIngBtn.setOnAction(e -> {
            models.RecipeEntry selected = ingList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                bg.getRecipe().remove(selected);
                showBakedGoodDetails(bg);
            }
        });

        // Add Ingredient to Recipe section
        HBox addIngBox = new HBox(5);
        ComboBox<Ingredient> ingCombo = new ComboBox<>();
        for(Ingredient i : system.getAllIngredients()) ingCombo.getItems().add(i);
        TextField qtyField = new TextField();
        qtyField.setPromptText("Qty");
        Button addBtn = new Button("Add");
        addBtn.setOnAction(e -> {
            Ingredient selected = ingCombo.getValue();
            try {
                double qty = Double.parseDouble(qtyField.getText());
                if (selected != null) {
                    bg.addIngredient(selected, qty); // Add to recipe
                    showBakedGoodDetails(bg);
                }
            } catch (Exception ex) {}
        });
        addIngBox.getChildren().addAll(ingCombo, qtyField, addBtn);

        Button deleteBtn = new Button("Delete Baked Good"); // Delete action
        deleteBtn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
        deleteBtn.setOnAction(e -> {
            system.removeBakedGood(bg.getName());
            showBakedGoodsList();
        });

        vbox.getChildren().addAll(ingredientsTitle, ingList, removeIngBtn, new Label("Add Ingredient:"), addIngBox, deleteBtn);
        contentArea.setContent(vbox);
    }

    // Saves current data
    private void saveData() {
        try {
            system.saveToTextFile(DATA_FILE); // Save to file
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save Successful");
            alert.setHeaderText(null);
            alert.setContentText("Data has been saved");
            alert.showAndWait();
        } catch (Exception e) {
            showError("Failed to save data: " + e.getMessage());
        }
    }

    // Loads data from file
    private void loadData() {
        try {
            system = BakerySystem.loadFromTextFile(DATA_FILE); // Load system
            showBakedGoodsList(); // Refresh view
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Load Successful");
            alert.setHeaderText(null);
            alert.setContentText("Data has been loaded");
            alert.showAndWait();
        } catch (Exception e) {
            showError("Failed to load data: " + e.getMessage());
        }
    }

    // Helper to show error alerts
    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // Launch point
    public static void main(String[] args) {
        launch(args);
    }
}
