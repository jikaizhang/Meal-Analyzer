package application;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

// K key
// V value
public class ItemList<K extends Comparable<K>, V> {
	private ArrayList<Node> list;
	public ItemList() {
		list = new ArrayList<Node>();
	}
	
	public void insert(K key, V value) {
		boolean nodeInserted = false;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).key.compareTo(key) >= 0) {
				for (int j = list.size() - 1; j >= i; j--) 	// right shift
					list.add(j+1, list.get(j));
				Node node = new Node();
				node.key = key;
				node.value = value;
				list.add(i, node);	// insert
				nodeInserted = true;
			}
		}
		if (!nodeInserted) {
			Node node = new Node();
			node.key = key;
			node.value = value;
			list.add(list.size(), node);
		}
	}
	
	public List<V> rangeSearch(K key, String comparator) {
        if (!comparator.contentEquals(">=") && 
            !comparator.contentEquals("==") && 
            !comparator.contentEquals("<=") )
            return new ArrayList<V>();
        // TODO : Complete
        int pointIndex = 0;
        ArrayList<V> newList = new ArrayList<V>();
        if (comparator.equals(">=")) {
	        for (int i = 0; i < list.size(); i++) {
	        	if (list.get(i).key.compareTo(key) >= 0)
	        		newList.add(list.get(i).value);
	        }
	        return newList;
        }
        
        else if (comparator.equals("<=")) {
	        for (int i = list.size() - 1; i > -1; i++) {
	        	if (list.get(i).key.compareTo(key) <= 0)
	        		newList.add(list.get(i).value);
	        }
	        return newList;
        }
        
        else { // "=="
	        for (int i = 0; i < list.size(); i++) {
	        	if (list.get(i).key.compareTo(key) == 0)
	        		newList.add(list.get(i).value);
	        }
	        return newList;
        }
    }
	
	public void print() {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).key);
		}
	}
	
	private class Node {
		K key;
		V value;
		
		public Node() {
			key = null;
			value = null;
		}
	}
	
	   public static void main(String[] args) {
	        // create empty BPTree with branching factor of 3
	        ItemList<Double, Double> itemList = new ItemList<>();

	        // create a pseudo random number generator
	        Random rnd1 = new Random();

	        // some value to add to the BPTree
	        Double[] dd = {0.0d, 0.5d, 0.2d, 0.8d};

	        // build an ArrayList of those value and add to BPTree also
	        // allows for comparing the contents of the ArrayList 
	        // against the contents and functionality of the BPTree
	        // does not ensure BPTree is implemented correctly
	        // just that it functions as a data structure with
	        // insert, rangeSearch, and toString() working.
	        List<Double> list = new ArrayList<>();
	        for (int i = 0; i < 7; i++) {
	            Double j = dd[rnd1.nextInt(4)];
	            list.add(j);
	            itemList.insert(j, j);
	        }
	        itemList.print();
	        List<Double> filteredValues = itemList.rangeSearch(0.2d, ">=");
	        System.out.println("Filtered values: " + filteredValues.toString());
	        
	    }
}
