/**
 * Filename: Main.java
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

import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * 
 * This is the main class of our food query program, used to call main method to start the GUI and
 * related functions
 * 
 */
public class Main extends Application {

  // initialize static field
  static FoodData data;
  static List<FoodItem> nutrientlist;
  static List<FoodItem> namelist;
  static List<FoodItem> finallist;

  /**
   * 
   * This method is the start method of our GUI to call three different GUI classes we used and set
   * the scene and primary stage of the GUI
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

    // call three GUI class 
    RootRight initialize1 = new RootRight(data);
    FoodList initialize2 = new FoodList(data);
    QueryAndInfo initialize3 = new QueryAndInfo(nutrientlist, namelist, finallist, data);
    
    // launch GUI
    launch(args);
    

  }
}
