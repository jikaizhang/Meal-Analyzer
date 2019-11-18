package application;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NutrientInfo {
	public void start(Stage primaryStage) {
		try {
			HBox hbox1 = new HBox();
			HBox hbox2 = new HBox();
			HBox hbox3 = new HBox();
			HBox hbox4 = new HBox();
			HBox hbox5 = new HBox();
			VBox vbox1 = new VBox();
			VBox vbox2 = new VBox();
			Label nutrientInfo = new Label("Nutrient Info");
			Label foodName = new Label("[food name]\n\n");
			Label calories = new Label("Calories:\t\t\t");
			Label caloriesVal = new Label("0");
			Button addToMeal = new Button("Add to Meal");
			// Not textField, what?
			hbox1.getChildren().addAll(calories,caloriesVal);
			Label fat = new Label("Fat:\t\t\t\t");
			Label fatVal = new Label("0");
			//
			hbox2.getChildren().addAll(fat, fatVal);
			Label carbonH = new Label("Carbonhydrate:\t");
			Label carbonHVal = new Label("0");
			//
			hbox3.getChildren().addAll(carbonH, carbonHVal);
			Label fiber = new Label("Fiber:\t\t\t");
			Label fiberVal = new Label("0");
			//
			hbox4.getChildren().addAll(fiber, fiberVal);
			Label protein = new Label("Protein:\t\t\t");
			Label proteinVal = new Label("0");
			//
			hbox5.getChildren().addAll(protein, proteinVal);
			
			vbox1.getChildren().addAll(foodName, hbox1, hbox2, hbox3, hbox4, hbox5);
			vbox2.getChildren().addAll(nutrientInfo, vbox1, addToMeal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
