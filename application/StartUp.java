/**
 * Filename: StartUp.java
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

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * This is one of the GUI class that make up of our whole GUI
 * @author shifan, etc
 *
 */
public class StartUp {

  /**
   * This is the start method to call to show the GUI as implemented here
   * @param bPane
   */
	public static void Start(BorderPane bPane) {
		
		Text title = new Text(20, 10, "Meal   Analyzer");//text for the program
		title.setFont(Font.font("Georgia", 20));
		Text version = new Text(10, 20, "V. 0.10");//text for the version
		version.setFont(Font.font("Georgia", 20));
		
		
		HBox upperBand = new HBox();
		upperBand.setStyle("-fx-background-color: #BFBFBF;" +
						   "-fx-padding: 5;");
	    upperBand.setSpacing(580);
	    upperBand.setMaxHeight(100);

	    upperBand.getChildren().addAll(title, version);
	    
	    
	    //everything's done, add the items into the borderPane
	    bPane.setTop(upperBand);
	    bPane.setStyle("-fx-background-color: #DEDEDE;");
		
	}
	
}
