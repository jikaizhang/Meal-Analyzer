/**
 * Filename: Main.java
 * 
 * Project: Team Project Final Submission
 * 
 * Authors: Shifan Zhou (szhou235@wisc.edu), Lixing Cheng (lcheng56@wisc.edu) Kaidong Lin
 * (klin54@wisc.edu), Yiting Wang (wang2245@wisc.edu), Jikai Zhang (jzhang726@wisc.edu)
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

import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * 
 * This is the main class of our food query program, used to call main method to
 * start the GUI and related functions
 * 
 */
public class Main extends Application {

	// initialize static field
	static FoodData data;
	static List<FoodItem> nutrientList;
	static List<FoodItem> nameList;
	static List<FoodItem> finalList;

	/**
	 * 
	 * This method is the start method of our GUI to call three different GUI
	 * classes we used and set the scene and primary stage of the GUI
	 * 
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) {
		try {

			primaryStage.setTitle("Meal Analyzer");

			// main BorderPane
			BorderPane root = new BorderPane();

			// everything inside the borderPane
			StartUp.Start(root);
			FoodList.start(root);
			QueryAndInfo.start(root);
			RootRight.start(root);

			// primary scene
			Scene scene = new Scene(root, 800, 550);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			// primary stage
			primaryStage.setScene(scene);
			primaryStage.setResizable(true);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This is the main method of our Main class
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		data = new FoodData();

		// call three GUI classes
		new RootRight(data);
		new FoodList(data);
		new QueryAndInfo(nutrientList, nameList, finalList, data);

		// launch GUI
		launch(args);

	}
}
