// Deandra Martin, Jenny Goldsher, Hiroki Sato, Miriam Scheinblum
// Group Project
//CMS270
// April 24 2020

import java.util.*;

public class Inventory {

	private static ArrayList<Item> inStock;
	private ArrayList<Item> SampleItems;
	//------------------------------------------------------
	public Inventory() {
		inStock = new ArrayList<>();
		SampleItems = new ArrayList<>();
	}

	//------------------------------------------------------
	
	public ArrayList<Item> getSampleItems(){
		return SampleItems;
	}
	
	public void setSampleItems(ArrayList<Item> sample) {
		SampleItems = sample;
	}
	
	//Takes an item as input and adds it to the inventory
	public void recieveItem(Item i) {
		inStock.add(i);
	}
	
	//------------------------------------------------------
	
	//Checks to see if an item is in the Inventory
	//Does not remove the item
	//returns true if present, false if not
	public boolean checkForItem(Item item) {
		for(int i = 0; i < inStock.size(); i++) {
			if(item.getStockNo()==inStock.get(i).getStockNo()) {
				return true;
			}
		}
		return false;
	}
	
	//-------------------------------------------------------
	// overloaded method
	public boolean checkForItem(String item) {
		for(int i = 0; i < inStock.size(); i++) {
			if(item.equals(inStock.get(i).getName()) ){
				return true;
			}
		}
		return false;
	}
	
	//------------------------------------------------------
	
	public Item searchItem(String item) {
		
		int position = 0;
		for(int i = 0;i< inStock.size();i++) {
			if(item.equals(inStock.get(i).getName())) {
				position = i;
				Item target = inStock.get(position);
				return target;
			}
		}
		Item fail = new Item();
		fail.setStockNo(-1);
		return fail;
		
	}
	
	//------------------------------------------------------
	
	//Goes through the inventory and adds the item needed to the order
	//Additionally removes that item from the inventory
	public Item putItemInOrder(Item item) {
		for(int i = 0; i < inStock.size(); i++) {
			if(item.getStockNo()== inStock.get(i).getStockNo()) {
				Item temp = inStock.get(i);
				inStock.remove(i);
				return temp;
			}
		}
			System.out.println("Item not found.");
			Item bad = new Item();
			bad.setStockNo(-1);
			return bad;
	}
	
	
	//------------------------------------------------------
	
	//Takes an item and sees how many of that item are in the inventory
	public int checkCount(Item item) {
		int count = 0;
		for(int i = 0; i < inStock.size(); i++) {
			if( item.getStockNo()== inStock.get(i).getStockNo()) {
				count++;
			}
		}
		
		return count;
	}
	
	//------------------------------------------------------
	//overloading method of checkCount
	public int checkCount(String item) {
		int count = 0;
		for(int i = 0; i < inStock.size(); i++) {
			if( item.equals(inStock.get(i).getName())) {
				count++;
			}
		}
		
		return count;
	}
	
	//------------------------------------------------------
	
	//Checks how many of each item are in the inventory
	//If the number of a particular item is less than 3
	//Adds the name of the item to a String that is returned
	//The String is the names of all the items 3 or less in the inventory
	//Separated by "&"
	public String needRestock() {
		//Create a StringBuilder that starts with the & character
		StringBuilder sb = new StringBuilder();
		sb.append("&");
		
		//Make an arraylist to store the names of checked items
		ArrayList<String> names = new ArrayList<>();
		
		//Loop through the array of items
		for(int i = 0; i < inStock.size(); i++) {
			
			//If there are less than 3 of the item in stock, make sure
			//We havent checked the item then add it to the list
			if(checkCount(inStock.get(i)) < 3) {
				
				boolean checked = false;
				int k = 0;
				while(checked == false && k < names.size()) {
					if(names.get(k).equals(inStock.get(i).getName())) {
						checked = true;
					}
					
					k++;
				}
				
				//If we haven't checked it and it's less than 3
				//Add it to the list of needed Strings
				if(checked == false) {
					sb.append(inStock.get(i).getName() + "&");
					names.add(inStock.get(i).getName());
				}
				
			}
			
			
		}
		
		if(sb.toString().equals("&")) {
			return "No items needed to be restocked";
		}
			return sb.toString();
		}
		
		//Method that identifies the name of the item and returns it
		//To be told to the supplier
		//Takes and returns the  item
		public Item requestItem(Item name) {
			System.out.println("We request 3 more " + name.getName());
			return name;
		}
		
		
		//------------------------------------------------------

		
		public String printInventory() {
			StringBuilder value = new StringBuilder();
			for(int k = 0; k < inStock.size() - 1; k++) {
				value.append(inStock.get(k).getName() + "\n");
			}
			
			if(inStock.size() > 0) {
			value.append(inStock.get(inStock.size() - 1).getName());
			
			}
			return value.toString();
		}
			
		
}

