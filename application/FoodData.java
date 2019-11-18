/**
 * Filename: FoodData.java
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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

/**
 * This class represents the backend for managing all the operations associated with FoodItems
 * 
 * @author sapan (sapan@cs.wisc.edu)
 */
public class FoodData implements FoodDataADT<FoodItem> {

  // List of all the food items.
  private List<FoodItem> foodItemList;

  // Map of nutrients and their corresponding index
  private HashMap<String, HashMap<Double, FoodItem>> indexes;


  /**
   * Public constructor
   */
  public FoodData() {
    foodItemList = new ArrayList<FoodItem>();
    indexes = new HashMap<String, HashMap<Double, FoodItem>>();
    HashMap<Double, FoodItem> calories_tree = new HashMap<Double, FoodItem>(5); // choose 5
    HashMap<Double, FoodItem> fat_tree = new HashMap<Double, FoodItem>(5); // choose 5
    HashMap<Double, FoodItem> carbohydrate_tree = new HashMap<Double, FoodItem>(5); // choose 5
    HashMap<Double, FoodItem> fiber_tree = new HashMap<Double, FoodItem>(5); // choose 5
    HashMap<Double, FoodItem> protein_tree = new HashMap<Double, FoodItem>(5); // choose 5
    indexes.put("calories", calories_tree);
    indexes.put("fat", fat_tree);
    indexes.put("carbohydrate", carbohydrate_tree);
    indexes.put("fiber", fiber_tree);
    indexes.put("protein", protein_tree);
  }


  private Comparator<FoodItem> nameComparator = new Comparator<FoodItem>() {
    @Override
    public int compare(FoodItem i1, FoodItem i2) {
      if (i1.getName().compareTo(i2.getName()) != 0)
        return i1.getName().compareTo(i2.getName());
      else
        return i1.getID().compareTo(i2.getID());
    }
  };

  /*
   * (non-Javadoc)
   * 
   * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
   */
  @Override
  public void loadFoodItems(String filePath) {
    foodItemList = new ArrayList<FoodItem>();
    indexes = new HashMap<String, HashMap<Double, FoodItem>>();
    HashMap<Double, FoodItem> calories_tree = new HashMap<Double, FoodItem>(5); // choose 5
    HashMap<Double, FoodItem> fat_tree = new HashMap<Double, FoodItem>(5); // choose 5
    HashMap<Double, FoodItem> carbohydrate_tree = new HashMap<Double, FoodItem>(5); // choose 5
    HashMap<Double, FoodItem> fiber_tree = new HashMap<Double, FoodItem>(5); // choose 5
    HashMap<Double, FoodItem> protein_tree = new HashMap<Double, FoodItem>(5); // choose 5
    indexes.put("calories", calories_tree);
    indexes.put("fat", fat_tree);
    indexes.put("carbohydrate", carbohydrate_tree);
    indexes.put("fiber", fiber_tree);
    indexes.put("protein", protein_tree);
    FileReader fr = null; // reference to a FileReader object
    BufferedReader br = null; // reference to a BufferedReader object
    String line = "";
    try {
      fr = new FileReader(filePath);
      br = new BufferedReader(fr);

      while ((line = br.readLine()) != null) {

        // use comma as separator
        String[] contents = line.split(",");
        if (contents.length != 12)
          continue;
        String id = contents[0];
        String name = contents[1];
        Double calories = Double.parseDouble(contents[3]);
        Double fat = Double.parseDouble(contents[5]);
        Double carbohydrate = Double.parseDouble(contents[7]);
        Double fiber = Double.parseDouble(contents[9]);
        Double protein = Double.parseDouble(contents[11]);

        // empty space should be skipped (haven't done yet)
        FoodItem food = new FoodItem(id, name);
        foodItemList.add(food);
        food.addNutrient("calories", calories);
        food.addNutrient("fat", fat);
        food.addNutrient("carbohydrate", carbohydrate);
        food.addNutrient("fiber", fiber);
        food.addNutrient("protein", protein);

        indexes.get("calories").put(calories, food);
        indexes.get("fat").put(fat, food);
        indexes.get("carbohydrate").put(carbohydrate, food);
        indexes.get("fiber").put(fiber, food);
        indexes.get("protein").put(protein, food);
      }
      Collections.sort(foodItemList, nameComparator);
      br.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.getMessage();
    } finally { // close open resources
    }
    if (br != null)
      try {
        br.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
  }



  /*
   * (non-Javadoc)
   * 
   * @see skeleton.FoodDataADT#filterByName(java.lang.String)
   */
  @Override
  public List<FoodItem> filterByName(String substring) {

    // Complete
    List<FoodItem> finallist = new ArrayList<FoodItem>();
    for (int i = 0; i < foodItemList.size(); i++) {
      FoodItem temp = foodItemList.get(i);
      if (temp.getName().contains(substring)) {
        finallist.add(temp);
      }
    }
    return finallist;
  }


  /*
   * (non-Javadoc)
   * 
   * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
   */
  @Override
  public List<FoodItem> filterByNutrients(List<String> rules) {
    List<FoodItem> finallist = new ArrayList<FoodItem>(foodItemList);
    if (rules.size() == 0) {
      return finallist;
    }
    ArrayList<List<FoodItem>> outterlist = new ArrayList<List<FoodItem>>();
    for (int i = 0; i < rules.size(); i++) {
      String rulei = rules.get(i);
      if (rulei == null)
        continue;

      String[] rulej = rulei.split(" ");


      String nutrientcapital = rulej[0];
      String nutrientdecap = nutrientcapital.toLowerCase();

      String comparator = rulej[1];

      if (!rulej[2].matches("[0-9]*[.]{0,1}[0-9]*"))
        continue;

      Double value = Double.parseDouble(rulej[2]);


      List<FoodItem> temp = rangeSearch(value, comparator, nutrientdecap);
      outterlist.add(temp);
    }

    finallist = outterlist.get(0);
    System.out.println(finallist.size());
    for (int j = 0; j < outterlist.size(); j++) {
      finallist = intersection(finallist, outterlist.get(j));
    }
    return finallist;
  }

  /**
   * This is the private helper method to help us implement range search to find the correct
   * filtered list of foood item
   * 
   * @param key
   * @param comparator
   * @param nutrient
   * @return
   */
  private List<FoodItem> rangeSearch(Double key, String comparator, String nutrient) {
    if (!comparator.contentEquals(">=") && !comparator.contentEquals("==")
        && !comparator.contentEquals("<="))
      return new ArrayList<FoodItem>();

    ArrayList<FoodItem> result = new ArrayList<FoodItem>();
    if (comparator.contentEquals(">=")) {
      for (int i = 0; i < foodItemList.size(); i++) {
        if (foodItemList.get(i).getNutrientValue(nutrient) >= key) {
          System.out.println(foodItemList.get(i).getNutrientValue(nutrient));
          result.add(foodItemList.get(i));
        }
      }
    }

    if (comparator.contentEquals("==")) {
      for (int i = 0; i < foodItemList.size(); i++) {
        if (foodItemList.get(i).getNutrientValue(nutrient) == key) {
          result.add(foodItemList.get(i));
        }
      }
    }

    if (comparator.contentEquals("<=")) {
      for (int i = 0; i < foodItemList.size(); i++) {
        if (foodItemList.get(i).getNutrientValue(nutrient) <= key) {
          result.add(foodItemList.get(i));
        }
      }
    }

    return result;
  }

  /**
   * This is the private helper method to help find the inersection of all filtered list to gain a
   * final list
   * 
   * @param list1
   * @param list2
   * @return
   */
  private List<FoodItem> intersection(List<FoodItem> list1, List<FoodItem> list2) {
    List<FoodItem> list = new ArrayList<FoodItem>();
    for (int i = 0; i < list1.size(); i++) {
      if (list2.contains(list1.get(i)))
        list.add(list1.get(i));
    }
    return list;
  }


  /*
   * (non-Javadoc)
   * 
   * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
   */
  @Override
  public void addFoodItem(FoodItem foodItem) {
    foodItemList.add(foodItem);
    indexes.get("calories").put(foodItem.getNutrientValue("calories"), foodItem);
    indexes.get("fat").put(foodItem.getNutrientValue("fat"), foodItem);
    indexes.get("carbohydrate").put(foodItem.getNutrientValue("carbohydrate"), foodItem);
    indexes.get("fiber").put(foodItem.getNutrientValue("fiber"), foodItem);
    indexes.get("protein").put(foodItem.getNutrientValue("protein"), foodItem);
  }

  /*
   * (non-Javadoc)
   * 
   * @see skeleton.FoodDataADT#getAllFoodItems()
   */
  @Override
  public List<FoodItem> getAllFoodItems() {
    return foodItemList;
  }

  @Override
  public void saveFoodItems(String filename) {

    FileOutputStream fileByteStream = null; // File output stream
    PrintWriter outFS = null; // Output stream

    // Try to open file
    try {
      fileByteStream = new FileOutputStream(filename);

      outFS = new PrintWriter(fileByteStream);

      // File is open and valid if we got this far (otherwise exception thrown)
      // Can now write to file
      for (int i = 0; i < foodItemList.size(); i++) {
        outFS.println(foodItemList.get(i).getID() + ",");
        outFS.print(foodItemList.get(i).getName() + ",calories,");
        outFS.print(foodItemList.get(i).getNutrientValue("calories") + ",fat,");
        outFS.print(foodItemList.get(i).getNutrientValue("fat") + ",carbohydrate,");
        outFS.print(foodItemList.get(i).getNutrientValue("carbohydrate") + ",fiber,");
        outFS.print(foodItemList.get(i).getNutrientValue("fiber") + ",protein,");
        outFS.print(foodItemList.get(i).getNutrientValue("protein"));
      }
      // Done with file, so try to close it
      try {
        fileByteStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      } // close() may throw IOException if fails
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    try {
      PrintStream printStream = new PrintStream(filename); // open the file
      for (ListIterator<FoodItem> iter = foodItemList.listIterator(); iter.hasNext();) {
        FoodItem item = iter.next();
        printStream.println(item.getID() + "," + item.getName() + ",calories,"
            + item.getNutrientValue("calories") + ",fat," + item.getNutrientValue("fat")
            + ",carbohydrate," + item.getNutrientValue("carbohydrate") + ",fiber,"
            + item.getNutrientValue("fiber") + ",protein," + item.getNutrientValue("protein"));
      }
      printStream.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
