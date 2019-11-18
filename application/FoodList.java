/**
 * Filename: FoodList.java
 * 
 * Project: Team Project Final Submission
 * 
 * Authors: Shifan Zhou (szhou235@wisc.edu), Lixing Cheng (lcheng56@wisc.edu) Kaidong Lin
 * (klin54@wisc.edu), Yiting Wang (wang2245@wisc.edu), Jikai Zhang (jzhang726@wisc.edu)
 * 
 *
 * Semester: Fall 2018 Course: CS400
 * 
 * Due Date: due 12/12/2018
 * 
 * Version: 1.0
 * 
 * Credits: none
 * 
 * Bugs: none
 */
package application;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Foodlist, the component for the visuals and operations on the left side of the GUI. It dealed
 * with adding new food to the food item list.
 * 
 * @author Kai Lin (klin54@wisc.edu), Lixing Cheng (lcheng56@wisc.edu), Jikai Zhang
 *         (jzhang726@wisc.edu), Shifan Zhou (szhou235@wisc.edu)
 *
 */

public class FoodList {
  // initialize parameters passed and to be passed
  static FoodData data;
  static FoodItem curr;
  static FoodItem newFood;
  static ObservableList<FoodItem> foodsView;
  static ListView<FoodItem> foodsList;

  // import the FoodData instance created in main

  public FoodList(FoodData data) {
    this.data = data;
  }

  // stage for the add new food button
  private static void showPopup() {
    Stage newStage = new Stage();
    VBox comp = new VBox();

    // labels, put in a hbox
    HBox labelsBox1 = new HBox();
    HBox labelsBox2 = new HBox();
    HBox labelsBox3 = new HBox();


    Label nameLable = new Label("Name:");
    Label IDLabel = new Label("ID:");
    Label caloriesLabel = new Label("Calories:");
    Label fatLabel = new Label("Fat:");
    Label carbonLabel = new Label("Carbohydate:");
    Label fiberLabel = new Label("Fiber:");
    Label proteinLabel = new Label("Protein:");

    labelsBox1.getChildren().addAll(caloriesLabel, fatLabel);
    labelsBox2.getChildren().addAll(carbonLabel, fiberLabel);
    labelsBox3.getChildren().addAll(proteinLabel);

    labelsBox1.setSpacing(70);
    labelsBox2.setSpacing(43);
    labelsBox3.setSpacing(50);

    // Create and format text fields in pop up window
    HBox fieldsBox1 = new HBox();
    HBox fieldsBox2 = new HBox();
    HBox fieldsBox3 = new HBox();

    TextField nameField = new TextField();
    TextField IDField = new TextField();
    TextField caloriesField = new TextField();
    TextField fatField = new TextField();
    TextField carboField = new TextField();
    TextField fiberField = new TextField();
    TextField proteinField = new TextField();

    fieldsBox1.getChildren().addAll(caloriesField, fatField);
    fieldsBox2.getChildren().addAll(carboField, fiberField);
    fieldsBox3.getChildren().addAll(proteinField);

    fieldsBox1.setSpacing(63);
    fieldsBox2.setSpacing(63);
    fieldsBox3.setSpacing(63);


    nameField.setMaxWidth(170);
    IDField.setMaxWidth(170);
    caloriesField.setMaxWidth(70);
    fatField.setMaxWidth(70);
    carboField.setMaxWidth(70);
    fiberField.setMaxWidth(70);
    proteinField.setMaxWidth(70);

    HBox buttonBox = new HBox();
    HBox phBox = new HBox();
    Button confirm = new Button("Add");
    confirm.setPrefSize(50, 30);
    buttonBox.getChildren().addAll(phBox, confirm);
    buttonBox.setSpacing(150);


    comp.getChildren().addAll(nameLable, nameField, IDLabel, IDField, labelsBox1, fieldsBox1,
        labelsBox2, fieldsBox2, labelsBox3, fieldsBox3, buttonBox);
    comp.setStyle("-fx-padding: 5;");

    Scene stageScene = new Scene(comp, 220, 240);
    newStage.setScene(stageScene);
    newStage.setTitle("input");
    newStage.setResizable(false);

    newStage.show();

    // button handler,after add is clicked, add to the food list
    confirm.setOnAction(e -> {
      try {
        // check if the name is inputed
        if (nameField.getLength() == 0)
          throw new Exception("The food item must have a name.");
        // check if the id is inputed
        if (IDField.getLength() == 0)
          throw new Exception("The food item must have an ID, make sure to" + " use a unique ID.");
        // check if all the nutrients' value are filled
        if (caloriesField.getLength() == 0 || carboField.getLength() == 0
            || fatField.getLength() == 0 || proteinField.getLength() == 0
            || fiberField.getLength() == 0)
          throw new Exception("Please fill in all the nutrients' value.");
        if (Double.parseDouble(caloriesField.getText()) < 0
            || Double.parseDouble(carboField.getText()) < 0
            || Double.parseDouble(fatField.getText()) < 0
            || Double.parseDouble(proteinField.getText()) < 0
            || Double.parseDouble(fiberField.getText()) < 0)
          throw new Exception("Nutrient value must be nonnegative number. Please edit your input.");

      }

      catch (IllegalArgumentException except) {
        Alert alert = new Alert(AlertType.WARNING,
            "Nutrient value must be nonnegative number. Please edit your input.");
        alert.showAndWait();
      } catch (Exception excpt) {
        // Prints the error message in alert window
        Alert alert = new Alert(AlertType.WARNING, excpt.getMessage());
        alert.showAndWait();
      }

      // set the value of newly added Food item
      newFood = new FoodItem(IDField.getText(), nameField.getText());
      newFood.addNutrient("calories", Double.parseDouble(caloriesField.getText()));
      newFood.addNutrient("carbohydrate" + "", Double.parseDouble(carboField.getText()));
      newFood.addNutrient("fat", Double.parseDouble(fatField.getText()));
      newFood.addNutrient("protein", Double.parseDouble(proteinField.getText()));
      newFood.addNutrient("fiber", Double.parseDouble(fiberField.getText()));
      data.addFoodItem(newFood);

      QueryAndInfo.updateInfo(newFood);

      // sort the list to be alphabetical order
      Collections.sort(data.getAllFoodItems(), nameComparator);
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

      newStage.close();
    });
  }

  /**
   * This is the helper method to help us sort the list into alphabetical order
   */
  public static Comparator<FoodItem> nameComparator = new Comparator<FoodItem>() {
    @Override
    public int compare(FoodItem i1, FoodItem i2) {
      if (i1.getName().compareTo(i2.getName()) != 0)
        return i1.getName().compareTo(i2.getName());
      else
        return i1.getID().compareTo(i2.getID());
    }
  };

  /**
   * This is the start method of FoodList which is on the left of our scene and boarder pane,
   * showing the food list and add new food button
   */
  public static void start(BorderPane bPane) {

    // Create a VBox
    VBox outerBox = new VBox();
    outerBox.setPrefHeight(550);
    outerBox.setPrefWidth(240);

    // Create a new line of Text
    Text listTitle = new Text("Food list");
    listTitle.setFont(Font.font("Arial", 18));

    // the inner VBox that holds the table
    VBox innerBox = new VBox();
    innerBox.setPrefHeight(450);
    innerBox.setPrefWidth(200);

    // the list to display the foodItems

    // testing list
    // view////////////////////////////////////////////////////////////////////////////////////////
    foodsView = FXCollections.observableList(new ArrayList<FoodItem>());

    foodsList = new ListView<FoodItem>();
    foodsList.setOrientation(Orientation.VERTICAL);

    // attach the obervablelist to the listView
    foodsList.setItems(foodsView);

    // add the listView into the VBox
    innerBox.getChildren().addAll(foodsList);

    //////////////////////////////////////////////////////
    // controller for the listview
    foodsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        curr = foodsList.getSelectionModel().getSelectedItem();

        // System.out.println("Clicked " + curr);
        if (curr != null)
          QueryAndInfo.updateInfo(curr);
      }
    });
    //////////////////////////////////////////////////////

    // create the add new food button, placed in a new HBox
    HBox buttonBox = new HBox();
    HBox butPlaceHolder = new HBox();
    butPlaceHolder.prefWidth(20);
    butPlaceHolder.prefHeight(5);
    Button newFoodBut = new Button("Add new food");
    newFoodBut.setPrefWidth(240);
    newFoodBut.setPrefHeight(50);


    // button handler
    newFoodBut.setOnAction(e -> {
      showPopup();
    });

    buttonBox.getChildren().addAll(butPlaceHolder, newFoodBut);

    innerBox.getChildren().addAll(buttonBox);

    // Add the Text to the outer VBox
    outerBox.setSpacing(5);
    outerBox.getChildren().addAll(listTitle, innerBox);

    innerBox.setStyle(
        "-fx-background-color: #8FAADC;" + "-fx-padding: 10;" + "-fx-border-color: #000000;"
            + "-fx-border-width: 3;" + "-fx-border-style: solid inside;");

    // create a little space at the left of the food list
    HBox hyperBox = new HBox();

    HBox placeHolder = new HBox();
    placeHolder.setPrefWidth(5);

    hyperBox.getChildren().addAll(placeHolder, outerBox);

    // everything's done, add the items into the borderPane
    bPane.setLeft(hyperBox);
  }
}
