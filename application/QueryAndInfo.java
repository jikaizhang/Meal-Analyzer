/**
 * Filename: QueryAndInfo.java
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * This is the middle part of the GUI, containing the food query and the
 * nutrient info
 */
public class QueryAndInfo {
	// the food data
	static FoodData data;
	// the list we get when filtering by nutrients
	static List<FoodItem> nutrientList;
	// the list we get when filtering by name
	static List<FoodItem> nameList;
	// the intersection of nutrientlist and the namelist, i.e. our final list
	static List<FoodItem> finalList;
	// name of the food
	static Label foodName;
	// values of the nutrients, used in the infoBox
	static Label caloriesVal; // values for calories
	static Label fatVal; // values for fat
	static Label carbonHVal; // values for carbohydrate
	static Label fiberVal; // values for fiber
	static Label proteinVal; // values for protein

	/**
	 * Constructor of QueryAndInfo
	 * 
	 * @param nutrientlist
	 * @param namelist
	 * @param finallist
	 * @param data
	 */
	public QueryAndInfo(List<FoodItem> nutrientList, List<FoodItem> nameList, 
			List<FoodItem> finalList, FoodData data) {
		this.nutrientList = nutrientList;
		this.nameList = nameList;
		this.finalList = finalList;
		this.data = data;
	}

	private List<FoodItem> foodList;
	// food list

	/**
	 * Another constructor for QueryAndInfo
	 * 
	 * @param foodList
	 */
	public QueryAndInfo(List<FoodItem> foodList) {
		this.foodList = foodList;
	}

	/**
	 * Start the middle part of the GUI
	 * 
	 * @param root
	 */
	public static void start(BorderPane root) {
		try {

			HBox ultraBox = new HBox();
			// highest hierarchy
			VBox hyperBox = new VBox();

			HBox ultraLeft = new HBox();
			HBox ultraRight = new HBox();
			ultraBox.setSpacing(30);

			/////////////////////////////////////
			// The upper part: food query

			// Create a VBox
			VBox outerBox = new VBox();
			outerBox.setMaxHeight(260);
			;
			outerBox.setPrefWidth(250);

			// create a horizontal box for the first line
			HBox innerBox = new HBox();
			innerBox.setPrefHeight(60);
			innerBox.setPrefWidth(200);

			// Create a new line of Text
			Text listTitle = new Text("Apply filter");
			listTitle.setFont(Font.font("Arial", 18));

			// Create a checkbox
			CheckBox checkBox1 = new CheckBox();
			checkBox1.setIndeterminate(true);
			innerBox.getChildren().addAll(listTitle, checkBox1);
			innerBox.setSpacing(5);

			// Create an inner vbox
			VBox innervBox = new VBox();
			innervBox.setPrefHeight(540);
			innervBox.setPrefWidth(240);

			// Create an first horizontal box
			HBox hBox1 = new HBox();
			hBox1.setPrefHeight(32);
			hBox1.setPrefWidth(240);

			// create a text field
			TextField text1 = new TextField("0");
			text1.setMaxWidth(50);
			Label calories = new Label("   <=     Calories   <=        ");
			TextField text11 = new TextField();
			text11.setMaxWidth(50);
			hBox1.getChildren().addAll(text1, calories, text11);

			// Create an second horizontal box
			HBox hBox2 = new HBox();
			hBox2.setPrefHeight(32);
			hBox2.setPrefWidth(240);

			// create a text field
			TextField text2 = new TextField("0");
			text2.setMaxWidth(50);
			Label fat = new Label("   <=        fat         <=        ");
			TextField text21 = new TextField();
			text21.setMaxWidth(50);
			hBox2.getChildren().addAll(text2, fat, text21);

			// Create an third horizontal box
			HBox hBox3 = new HBox();
			hBox3.setPrefHeight(32);
			hBox3.setPrefWidth(240);

			// create a text field
			TextField text3 = new TextField("0");
			text3.setMaxWidth(50);
			Label carbohydrate = new Label("    <= Carbohydrate <=    ");
			TextField text31 = new TextField();
			text31.setMaxWidth(50);
			hBox3.getChildren().addAll(text3, carbohydrate, text31);

			// Create an forth horizontal box
			HBox hBox4 = new HBox();
			hBox4.setPrefHeight(32);
			hBox4.setPrefWidth(240);

			// create a text field
			TextField text4 = new TextField("0");
			text4.setMaxWidth(50);
			Label fiber = new Label("    <=       Fiber      <=       ");
			TextField text41 = new TextField();
			text41.setMaxWidth(50);
			hBox4.getChildren().addAll(text4, fiber, text41);

			// Create the fifth horizontal box
			HBox hBox5 = new HBox();
			hBox4.setPrefHeight(32);
			hBox4.setPrefWidth(240);

			// create a text field
			TextField text5 = new TextField("0");
			text5.setMaxWidth(50);
			Label protein = new Label("     <=     Protein     <=     ");
			TextField text51 = new TextField();
			text51.setMaxWidth(50);
			hBox5.getChildren().addAll(text5, protein, text51);

			// Create a new line of Text, padded by an empty vbox
			HBox textBox = new HBox();
			textBox.setPrefHeight(10);
			Text listTitle2 = new Text("Name filter");
			listTitle2.setFont(Font.font("Arial", 18));
			listTitle2.setWrappingWidth(200);
			textBox.getChildren().addAll(listTitle2);
			// create a long text
			TextField textlong = new TextField();

			VBox empty = new VBox();
			empty.setPrefHeight(20);

			// create two buttons
			Button clearButton = new Button("clear");
			clearButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					// the original values in the text field of the nutrient filter
					text1.setText("0");
					text11.setText("");
					text2.setText("0");
					text21.setText("");
					text3.setText("0");
					text31.setText("");
					text4.setText("0");
					text41.setText("");
					text5.setText("0");
					text51.setText("");
					FoodList.foodsView = FXCollections.observableList(data.getAllFoodItems());
					FoodList.foodsList.setItems(FoodList.foodsView);
					FoodList.foodsList.setCellFactory(lv -> new ListCell<FoodItem>() {
						// show food items as String in the food list
						@Override
						public void updateItem(FoodItem item, boolean empty) {
							super.updateItem(item, empty);
							if (empty) 
								setText(null);
							else {
								String text = item.getName();
								setText(text);
							}
						}
					});
				}
			});
			Button filterButton = new Button("Filter");
			filterButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					List<String> filterList = new ArrayList<String>();
					String calorieslow = null;
					// if there's no user input and the user clicked "Filter", we simply ignore it
					if (text1.getText().equals("0") && text11.getText().equals("") && text2.getText().equals("0")
							&& text21.getText().equals("") && text3.getText().equals("0") && text31.getText().equals("")
							&& text4.getText().equals("0") && text41.getText().equals("") && text5.getText().equals("0")
							&& text51.getText().equals("")) {

					} else {
						// if the user input is null, we want to simply ignore it
						if (!text1.getText().isEmpty())
							calorieslow = "calories " + ">= " + text1.getText();
						String calorieshigh = null;
						// the String is set to null, so that the filterByNutrient method in
						// FoodData class can ignore the null input
						if (!text11.getText().equals(""))
							calorieshigh = "calories " + "<= " + text11.getText();
						String fatlow = null;
						if (!text2.getText().equals(""))
							fatlow = "fat " + ">= " + text2.getText();
						String fathigh = null;
						if (!text21.getText().equals(""))
							fathigh = "fat " + "<= " + text21.getText();
						String carbohydratelow = null;
						if (!text3.getText().equals(""))
							carbohydratelow = "carbohydrate " + ">= " + text3.getText();
						String carbohydratehigh = null;
						if (!text31.getText().equals(""))
							carbohydratehigh = "carbohydrate " + "<= " + text31.getText();
						String fiberlow = null;
						if (!text4.getText().equals(""))
							fiberlow = "fiber " + ">= " + text4.getText();
						String fiberhigh = null;
						if (!text41.getText().equals(""))
							fiberhigh = "fiber " + "<= " + text41.getText();
						String proteinlow = null;
						if (!text5.getText().equals(""))
							proteinlow = "protein " + ">= " + text5.getText();
						String proteinhigh = null;
						if (!text51.getText().equals(""))
							proteinhigh = "protein " + "<= " + text51.getText();
						filterList.add(calorieslow);
						filterList.add(calorieshigh);
						filterList.add(fatlow);
						filterList.add(fathigh);
						filterList.add(carbohydratelow);
						filterList.add(carbohydratehigh);
						filterList.add(fiberlow);
						filterList.add(fiberhigh);
						filterList.add(proteinlow);
						filterList.add(proteinhigh);
						FoodData rulehere1 = new FoodData();
						// we make a copy of data, when clicking the button "Clear",
						// we want to show the original (unfiltered) list
						for (FoodItem item : data.getAllFoodItems()) {
							rulehere1.addFoodItem(item);
						}
						String substring = textlong.getText();
						nutrientList = rulehere1.filterByNutrients(filterList);
						nameList = rulehere1.filterByName(substring);
						finalList = intersection(nutrientList, nameList);
						FoodList.foodsView = FXCollections.observableList(finalList);
						FoodList.foodsList.setItems(FoodList.foodsView);
						FoodList.foodsList.setCellFactory(lv -> new ListCell<FoodItem>() {
							// shows all food items as String in the food list
							@Override
							public void updateItem(FoodItem item, boolean empty) {
								super.updateItem(item, empty);
								if (empty)
									setText(null);
								else {
									String text = item.getName();
									setText(text);
								}
							}
						});
					}
				}
			});

			HBox buttonBox = new HBox();

			buttonBox.getChildren().addAll(clearButton, filterButton);
			buttonBox.setSpacing(60);
			// Create an third horizontal box
			HBox hBoxlast = new HBox();
			hBoxlast.setPrefHeight(20);
			hBoxlast.setPrefWidth(240);
			hBoxlast.getChildren().addAll(buttonBox);

			innervBox.getChildren().addAll(hBox1, hBox2, hBox3, hBox4, hBox5, empty, textBox, textlong, hBoxlast);
			outerBox.getChildren().addAll(innerBox, innervBox);
			outerBox.setSpacing(5);
			innervBox.setStyle("-fx-background-color: #8FAADC;" + "-fx-padding: 3;" + "-fx-border-color: #000000;"
					+ "-fx-border-width: 3;" + "-fx-border-style: solid inside;");

			////////////////////////////////////////////////////
			// lower part food info box

			HBox hbox1 = new HBox();
			HBox hbox2 = new HBox();
			HBox hbox3 = new HBox();
			HBox hbox4 = new HBox();
			HBox hbox5 = new HBox();
			VBox vbox1 = new VBox();
			VBox vbox2 = new VBox();

			Label nutrientInfo = new Label("Nutrient Info");
			nutrientInfo.setFont(Font.font("Arial", 18));

			// name of the food that will be displayed to the user
			foodName = new Label("[food name]\n\n");
			foodName.setFont(Font.font("Arial", 18));

			Label calories2 = new Label("Calories:\t\t\t\t\t");
			caloriesVal = new Label("0");
			Button addToMeal = new Button("Add to Meal");
			addToMeal.setPrefWidth(250);
			addToMeal.setPrefHeight(30);
			addToMeal.setFont(Font.font("Arial", 18));

			addToMeal.setOnAction(e -> {
				if (FoodList.curr == null) {

				} else {

					RootRight.mealView.add(FoodList.curr);
					Collections.sort(RootRight.mealView, nameComparator);
					RootRight.mealList.setItems(RootRight.mealView);
					RootRight.mealList.setCellFactory(lv -> new ListCell<FoodItem>() {
						@Override
						public void updateItem(FoodItem item, boolean empty) {
							// show food items as String in meal list
							super.updateItem(item, empty);
							if (empty)
								setText(null);
							else {
								String text = item.getName();
								setText(text);
							}
						}
					});

				}
			});

			// Not textField, what?
			hbox1.getChildren().addAll(calories2, caloriesVal);
			Label fat2 = new Label("Fat:\t\t\t\t\t\t");
			fatVal = new Label("0");
			//
			hbox2.getChildren().addAll(fat2, fatVal);
			Label carbonH = new Label("Carbohydrate:\t\t\t\t");
			carbonHVal = new Label("0");
			//
			hbox3.getChildren().addAll(carbonH, carbonHVal);
			Label fiber2 = new Label("Fiber:\t\t\t\t\t");
			fiberVal = new Label("0");
			//
			hbox4.getChildren().addAll(fiber2, fiberVal);
			Label protein2 = new Label("Protein:\t\t\t\t\t");
			proteinVal = new Label("0");
			//
			hbox5.getChildren().addAll(protein2, proteinVal);

			vbox1.getChildren().addAll(foodName, hbox1, hbox2, hbox3, hbox4, hbox5);
			vbox1.setStyle("-fx-background-color: #8FAADC;" + "-fx-border-color: #000000;" + "-fx-border-width: 3;"
					+ "-fx-border-style: solid inside;");
			vbox1.setPrefWidth(250);
			vbox1.setMaxWidth(250);
			vbox1.setPrefHeight(100);
			vbox1.setPrefHeight(100);
			HBox hbox6 = new HBox();
			Text emptyT = new Text("");
			hbox6.setSpacing(5);
			hbox6.getChildren().addAll(emptyT);

			vbox2.getChildren().addAll(nutrientInfo, vbox1, addToMeal);
			vbox2.setSpacing(5);

			///////////////

			hyperBox.getChildren().addAll(outerBox, hbox6, vbox2);

			ultraBox.getChildren().addAll(ultraLeft, hyperBox, ultraRight);
			root.setCenter(ultraBox);

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Helper method for finding the intersection of nutrientlist and namelist and
	 * return the finallist
	 * 
	 * @param list1
	 * @param list2
	 * @return finallist after intersection
	 */
	private static List<FoodItem> intersection(List<FoodItem> list1, List<FoodItem> list2) {
		List<FoodItem> list = new ArrayList<FoodItem>();
		for (int i = 0; i < list1.size(); i++) {
			FoodItem food1 = list1.get(i);
			for (int j = 0; j < list2.size(); j++) {
				if (food1 == list2.get(j))
					list.add(food1);
			}
		}
		return list;
	}

	/**
	 * Helper method for comparing food item by name; if name are the same, compare
	 * by their ID
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
	 * the method used to update the info box when the user clicks a foodItem
	 */
	public static void updateInfo(FoodItem selected) {
		foodName.setText(selected.getName());
		caloriesVal.setText(Double.toString(selected.getNutrientValue("calories")));
		fatVal.setText(Double.toString(selected.getNutrientValue("fat")));
		carbonHVal.setText(Double.toString(selected.getNutrientValue("carbohydrate")));
		fiberVal.setText(Double.toString(selected.getNutrientValue("fiber")));
		proteinVal.setText(Double.toString(selected.getNutrientValue("protein")));

	}
}
