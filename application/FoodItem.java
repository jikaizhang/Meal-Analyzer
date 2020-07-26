/**
 * Filename: FoodItem.java
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

import java.util.HashMap;

/**
 * This class represents a food item with all its properties.
 * 
 * @author aka
 */
public class FoodItem {

	private String name;
	private String id;
	// Map from nutrition to corresponding value.
	private HashMap<String, Double> nutrients;

	/**
	 * Constructor
	 * 
	 * @param name name of the food item
	 * @param id   unique id of the food item
	 */
	public FoodItem(String id, String name) {
		this.id = id;
		this.name = name;
		nutrients = new HashMap<String, Double>();
		nutrients.put("calories", (double) 0);
		nutrients.put("fat", (double) 0);
		nutrients.put("carbohydrate", (double) 0);
		nutrients.put("fiber", (double) 0);
		nutrients.put("protein", (double) 0);
	}

	/**
	 * Gets the name of the food item
	 * 
	 * @return name of the food item
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the unique id of the food item
	 * 
	 * @return id of the food item
	 */
	public String getID() {
		return id;
	}

	/**
	 * Gets the nutrients of the food item
	 * 
	 * @return nutrients of the food item
	 */
	public HashMap<String, Double> getNutrients() {
		return nutrients;
	}

	/**
	 * Adds a nutrient and its value to this food. If nutrient already exists,
	 * updates its value.
	 */
	public void addNutrient(String name, double value) {
		nutrients.replace(name, value);
	}

	/**
	 * Returns the value of the given nutrient for this food item. If not present,
	 * then returns 0.
	 */
	public double getNutrientValue(String name) {
		if (nutrients.containsKey(name))
			return nutrients.get(name);
		return 0.0;
	}
}
