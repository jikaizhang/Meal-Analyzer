/**
 * Filename: StartUp.java
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

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * This is one of the GUI class that make up of our whole GUI
 * 
 * @author shifan, etc
 *
 */
public class StartUp {

	/**
	 * This is the start method to call to show the GUI as implemented here
	 * 
	 * @param bPane
	 */
	public static void Start(BorderPane bPane) {

		Text title = new Text(20, 10, "Meal   Analyzer");
		title.setFont(Font.font("Georgia", 20));
		Text version = new Text(10, 20, "V. 0.10");
		version.setFont(Font.font("Georgia", 20));

		HBox upperBand = new HBox();
		upperBand.setStyle("-fx-background-color: #BFBFBF;" + "-fx-padding: 5;");
		upperBand.setSpacing(580);
		upperBand.setMaxHeight(100);

		upperBand.getChildren().addAll(title, version);

		// initialization is done, add the items into the borderPane
		bPane.setTop(upperBand);
		bPane.setStyle("-fx-background-color: #DEDEDE;");

	}

}
