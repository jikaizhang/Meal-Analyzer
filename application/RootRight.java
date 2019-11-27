/**
 * Filename: RootRight.java
 * 
 * Project: Meal-Analyzer
 * 
 * Authors: Shifan Zhou, Lixing Cheng, Kaidong Lin, Yiting Wang, Jikai Zhang
 *
 * Version: 1.0
 * 
 * Credits: none
 * 
 * Bugs: none
 */
package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.collections.FXCollections;
import java.util.Comparator;

/**
 * This is the class of the right part of the root of primary GUI, it implements the meal list, its
 * related functions, and load and save file function of our program
 * 
 * @author shifan, etc
 *
 */
public class RootRight {

  // initialize fields
  static FoodData data;
  static ObservableList<FoodItem> mealView;
  static ListView<FoodItem> mealList;

  /**
   * This is the constructor to help us access to the FoodData data created in main
   * 
   * @param foodData
   */
  public RootRight(FoodData foodData) {
    this.data = foodData;
  }

  /**
   * This is the start method to create the right part of the GUI
   * 
   * @param root
   */
  public static void start(BorderPane root) {
    try {

      // the ListView of Meal List
      mealList = new ListView<>();
      mealList.setPrefWidth(240);
      mealList.setPrefHeight(350);
      mealList.setOrientation(Orientation.VERTICAL);

      // an observable list for the contents of the mealList
      List<FoodItem> foodItemList = new ArrayList<FoodItem>();
      mealView = FXCollections.observableArrayList(foodItemList);
      mealList.setItems(mealView);

      // Meal list label
      Label mLabel = new Label("Meal List");
      mLabel.setFont(Font.font("Arial", 18));

      // Four buttons in the right section
      Button removeButton = new Button("clear");
      Button saveButton = new Button("Save");
      Button loadButton = new Button("Load");
      Button analyzeButton = new Button("Analyze");

      // Set the side of each box
      removeButton.setPrefWidth(120);
      removeButton.setPrefHeight(30);
      saveButton.setPrefWidth(125);
      saveButton.setPrefHeight(30);
      loadButton.setPrefWidth(125);
      loadButton.setPrefHeight(30);
      analyzeButton.setPrefWidth(120);
      analyzeButton.setPrefHeight(30);

      // Create vbox and hbox to be used in the right part of root
      VBox vbox = new VBox();
      VBox vbox2 = new VBox();

      HBox hbox1 = new HBox();
      HBox hbox2 = new HBox();
      HBox hbox3 = new HBox();
      HBox emptyBox = new HBox();
      HBox outerBox = new HBox();

      hbox1.getChildren().addAll(removeButton, analyzeButton);
      hbox3.getChildren().addAll(loadButton, saveButton);

      hbox3.setSpacing(10);
      emptyBox.setPrefWidth(5);

      // Set the relationship of each vbox and hbox, and the background color, style of the largest
      // box
      vbox.getChildren().addAll(mealList, hbox1, hbox2);
      vbox.setSpacing(5);
      vbox.setStyle("-fx-background-color: #8FAADC;" + "-fx-border-color: #000000;"
          + "-fx-padding: 10;" + "-fx-border-color: #000000;" + "-fx-border-width: 3;"
          + "-fx-border-style: solid inside;");
      vbox2.getChildren().addAll(mLabel, vbox, hbox3);
      vbox2.setSpacing(5);
      outerBox.getChildren().addAll(vbox2, emptyBox);
      outerBox.setSpacing(5);
      outerBox.setPrefWidth(240);

      // handlers of the buttons

      // pop up a new window with Meal Analysis represented by pie chart when analyze button is
      // clicked
      analyzeButton.setOnAction(e -> {
        analyze();
      });

      // Use FileChooser to choose a new file to open and load Food data in the file to our food
      // list view in alphabetical order
      loadButton.setOnAction(e -> {
        Stage fileStage = new Stage();

        // create filechooser and open file from its path
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(fileStage);
        String filename = file.getAbsolutePath();

        // load file content into our food data instance, sort it, and show on the food list
        if (file != null) {
          data.loadFoodItems(filename);
          for (FoodItem foodItem : data.getAllFoodItems()) {
            QueryAndInfo.updateInfo(foodItem);
          }

          // sort list
          Collections.sort(data.getAllFoodItems(), nameComparator);

          // show on the food list
          FoodList.foodsView = FXCollections.observableList(data.getAllFoodItems());
          FoodList.foodsList.setItems(FoodList.foodsView);
          FoodList.foodsList.setCellFactory(lv -> new ListCell<FoodItem>() {
            @Override
            public void updateItem(FoodItem item, boolean empty) {
              super.updateItem(item, empty);
              if (empty) {
                setText(null);

              } else {
                String text = item.getName();
                setText(text);
              }
            }
          });
        }
        // create an empty food item to be used when no food item is clicked
        FoodItem ph = new FoodItem("000", "[food name]");
        QueryAndInfo.updateInfo(ph);
      });

      // the saveButton is used to create a file chooser and save the file in the project folder
      saveButton.setOnAction(e -> {
        // Create a new FileChooser
        Stage fileStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Create and Save File");

        // Create file extension for our csv file
        FileChooser.ExtensionFilter extFilter =
            new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(fileStage);
        // String filename = fileChooser.getInitialFileName();
        if (file != null) {
          data.saveFoodItems(file.getName());
        }
      });

      // remove button help us remove all food item in the meal list
      removeButton.setOnAction(e -> {
        removeAll();
      });

      root.setRight(outerBox);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This class is used to help us sort the list into alphabetical order
   */
  private static Comparator<FoodItem> nameComparator = new Comparator<FoodItem>() {
    @Override
    public int compare(FoodItem i1, FoodItem i2) {
      if (i1.getName().compareTo(i2.getName()) != 0)
        return i1.getName().compareTo(i2.getName());
      else
        return i1.getID().compareTo(i2.getID());
    }
  };

  /**
   * This is the help method to help remove all food item on the meal list
   */
  private static void removeAll() {
    mealView.clear();
  }


  /**
   * the method that analyzes the foodItems in the meal list and generate a pie chart to display the
   * analysis results.
   */
  private static void analyze() {
    // initialize total nums of nutrient
    double totalCalories = 0;
    double totalFat = 0;
    double totalCarbo = 0;
    double totalFiber = 0;
    double totalProtein = 0;

    // calculate
    for (FoodItem i : mealView) {
      totalCalories += i.getNutrientValue("calories");
      totalFat += i.getNutrientValue("fat");
      totalCarbo += i.getNutrientValue("carbohydrate");
      totalFiber += i.getNutrientValue("fiber");
      totalProtein += i.getNutrientValue("protein");
    }

    Stage analyzeStage = new Stage();

    ObservableList<PieChart.Data> pieChartData =
        FXCollections.observableArrayList(new PieChart.Data("Calories", totalCalories),
            new PieChart.Data("Fat", totalFat), new PieChart.Data("Carbohydrate", totalCarbo),
            new PieChart.Data("Fiber", totalFiber), new PieChart.Data("Protein", totalProtein));
    pieChartData.forEach(data -> data.nameProperty()
        .bind(Bindings.concat(data.getName(), " ", data.pieValueProperty())));
    final PieChart chart = new PieChart(pieChartData);
    chart.setTitle("Meal Analysis");

    // put vbox in the scene and show the stage
    VBox vbox = new VBox();
    vbox.getChildren().addAll(chart);
    Scene analyzeScene = new Scene(vbox, 500, 350);
    analyzeStage.setScene(analyzeScene);
    analyzeStage.setTitle("");
    analyzeStage.show();
  }
}
