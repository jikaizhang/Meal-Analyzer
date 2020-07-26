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
 * This class represents the backend for managing all the operations associated
 * with FoodItems
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
		this.initialize();
	}

	private Comparator<FoodItem> nameComparator = new Comparator<FoodItem>() {
		@Override
		public int compare(FoodItem item1, FoodItem item2) {
			if (item1.getName().compareTo(item2.getName()) != 0)
				return item1.getName().compareTo(item2.getName());
			else
				return item1.getID().compareTo(item2.getID());
		}
	};

	private void initialize() {
		foodItemList = new ArrayList<FoodItem>();
		indexes = new HashMap<String, HashMap<Double, FoodItem>>();
		HashMap<Double, FoodItem> calories_tree = new HashMap<>(5); // choose 5
		HashMap<Double, FoodItem> fat_tree = new HashMap<>(5); // choose 5
		HashMap<Double, FoodItem> carbohydrate_tree = new HashMap<>(5); // choose 5
		HashMap<Double, FoodItem> fiber_tree = new HashMap<>(5); // choose 5
		HashMap<Double, FoodItem> protein_tree = new HashMap<>(5); // choose 5
		indexes.put("calories", calories_tree);
		indexes.put("fat", fat_tree);
		indexes.put("carbohydrate", carbohydrate_tree);
		indexes.put("fiber", fiber_tree);
		indexes.put("protein", protein_tree);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
	 */
	@Override
	public void loadFoodItems(String filePath) {
		initialize();
		FileReader fr = null; // reference to a FileReader object
		BufferedReader br = null; // reference to a BufferedReader object
		String line = "";
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);

			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] contents = line.split(",");

				// should have 12 columns
				if (contents.length != 12)
					continue;

				String id = contents[0];
				String name = contents[1];

				if (id.isEmpty() || name.isEmpty())
					continue;

				// default value 0.0
				Double calories = contents[3] == null ? 0.0 : Double.parseDouble(contents[3]);
				Double fat = contents[5] == null ? 0.0 : Double.parseDouble(contents[5]);
				Double carbohydrate = contents[7] == null ? 0.0 : Double.parseDouble(contents[7]);
				Double fiber = contents[9] == null ? 0.0 : Double.parseDouble(contents[9]);
				Double protein = contents[11] == null ? 0.0 : Double.parseDouble(contents[11]);

				FoodItem food = new FoodItem(id, name);
				food.addNutrient("calories", calories);
				food.addNutrient("fat", fat);
				food.addNutrient("carbohydrate", carbohydrate);
				food.addNutrient("fiber", fiber);
				food.addNutrient("protein", protein);
				foodItemList.add(food);

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
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
		List<FoodItem> finalList = new ArrayList<>();
		for (FoodItem item : foodItemList) {
			if (item.getName().contains(substring))
				finalList.add(item);
		}
		return finalList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
	 */
	@Override
	public List<FoodItem> filterByNutrients(List<String> rules) {
		List<FoodItem> finalList = new ArrayList<FoodItem>(foodItemList);
		
		if (rules.size() == 0)
			return finalList;
		
		ArrayList<List<FoodItem>> outerList = new ArrayList<List<FoodItem>>();
		for (String rule : rules) {
			if (rule == null)
				continue;

			String[] ruleList = rule.split(" ");

			String nutrient = ruleList[0].toLowerCase();

			String operator = ruleList[1];

			if (!ruleList[2].matches("[0-9]*[.]{0,1}[0-9]*"))
				continue;

			Double value = Double.parseDouble(ruleList[2]);

			List<FoodItem> temp = rangeSearch(value, operator, nutrient);
			outerList.add(temp);
		}

		finalList = outerList.get(0);
//		System.out.println(finalList.size());
		for (int i = 1; i < outerList.size(); i++)
			finalList = intersection(finalList, outerList.get(i));
		return finalList;
	}

	/**
	 * This is the private helper method to help us implement range search to find
	 * the correct filtered list of food item
	 * 
	 * @param key
	 * @param comparator
	 * @param nutrient
	 * @return
	 */
	private List<FoodItem> rangeSearch(Double key, String operator, String nutrient) {
		if (!operator.contentEquals(">=") && !operator.contentEquals("==") 
				&& !operator.contentEquals("<="))
			return new ArrayList<FoodItem>();

		ArrayList<FoodItem> result = new ArrayList<FoodItem>();
		if (operator.contentEquals(">=")) {
			for (FoodItem food : foodItemList) {
				if (food.getNutrientValue(nutrient) >= key)
					result.add(food);
			}
		}

		else if (operator.contentEquals("==")) {
			for (FoodItem food : foodItemList) {
				if (food.getNutrientValue(nutrient) == key)
					result.add(food);
			}
		}

		else { // "<="
			for (FoodItem food : foodItemList) {
				if (food.getNutrientValue(nutrient) <= key)
					result.add(food);
			}
		}

		return result;
	}

	/**
	 * This is the private helper method to help find the inersection of all
	 * filtered list to gain a final list
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	private List<FoodItem> intersection(List<FoodItem> list1, List<FoodItem> list2) {
		List<FoodItem> list = new ArrayList<>();
		for (FoodItem item : list1) {
			if (list2.contains(item))
				list.add(item);
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
			for (FoodItem food : foodItemList) {
				outFS.println(food.getID() + ",");
				outFS.print(food.getName() + ",calories,");
				outFS.print(food.getNutrientValue("calories") + ",fat,");
				outFS.print(food.getNutrientValue("fat") + ",carbohydrate,");
				outFS.print(food.getNutrientValue("carbohydrate") + ",fiber,");
				outFS.print(food.getNutrientValue("fiber") + ",protein,");
				outFS.print(food.getNutrientValue("protein"));
			}
			// Done with writing, try to close it
			try {
				fileByteStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			} // close() may throw IOException if fails
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// TODO: What is this?
		try {
			PrintStream printStream = new PrintStream(filename); // open the file
			for (ListIterator<FoodItem> iter = foodItemList.listIterator(); iter.hasNext();) {
				FoodItem item = iter.next();
				printStream.println(item.getID() + "," + item.getName() + ",calories,"
						+ item.getNutrientValue("calories") + ",fat," + item.getNutrientValue("fat") + ",carbohydrate,"
						+ item.getNutrientValue("carbohydrate") + ",fiber," + item.getNutrientValue("fiber")
						+ ",protein," + item.getNutrientValue("protein"));
			}
			printStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
