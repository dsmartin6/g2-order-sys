// Deandra Martin, Jenny Goldsher, Hiroki Sato, Miriam Scheinblum
// Group Project
//CMS270
// April 24 2020

import java.util.*;

//We need ArrayList of String because we somehow need to keep track of 
//what item or items needs to be supplied by the supplier

public class WaitlistOrder extends ShippedOrder{

	//data members------------------------------------------------
	private ArrayList<String> notInStock;
	
	//------------------------------------------------------------
	//constructors
	public WaitlistOrder() {
		super();
		notInStock = new ArrayList<String>();
	}
	
	public WaitlistOrder(ArrayList<Item> orderline,String firstName, String lastName,
			int placedDate, int trackingNumber, String status, String shippingCompany, ArrayList<String> notInStock) {
		
		super(orderline,firstName,lastName,placedDate, trackingNumber, status, shippingCompany);
		//may set shippingCost to 0
		setShippingCost(0);
		this.notInStock = notInStock;
	}
	
	//------------------------------------------------------------
	//Getters and setters-----------------------------------------
	
	public ArrayList<String> getNotInStock(){
		return notInStock;
	}
	
	public void setNotInStock(ArrayList<String> notInStock) {
		this.notInStock = notInStock;
	}
	
	//optional? depending on if superclass has their own toString
	
	public double calculateCost() {
		
		double total = 0;
		
		if(super.getOrderLine().isEmpty()) {
			return total;
		}
		else {
		for(int i = 0;i<super.getOrderLine().size();i++) {
			
			double cost = super.getOrderLine().get(i).getCost();
			total = total +cost;
		}
		}
		setTotalCost(total);
		return total;
	}
	
	//toString method: this calls the toString method in super class and add expected ship date and list of name of the item needed 
	//for this order to be complete.
	public String toString() {
		
		String cost = String.format("%.2f", getTotalCost());
		return "Waitlist Order for " + getFirstName() + " " + getLastName() + "\nOrder Status: " + getStatus() + "\nTracking Number: " + getTrackingNumber() +  "\nOrder placed on day " + getOrderPlacedDate() +
				"\nOrder includes: " + listOrderedItems() + "\nTotal cost:$" + cost + "\nItems needed to complete order: " + listNotStockedItem();
		
	}
	
	//listNotStockedItem method: this method is going to traverse the ArrayList of String that contains list of name of item,
	//if this order is complete and didn't have any names, String will be N/A, 
	//otherwise We create a String of a list of name of items by using StringBuilder
	
	public String listNotStockedItem() {
		
		String itemlisted;
		StringBuilder sb = new StringBuilder();
		
		if(notInStock.isEmpty()) {
			
			itemlisted = "N/A";
			
		}
		else {
			
			int currentIndex = 0;
		while(currentIndex < notInStock.size()-1){
			String temp = notInStock.get(currentIndex);
			sb.append(temp);
			sb.append(", ");
			currentIndex++;
		}
		
		String last = notInStock.get(currentIndex);
		sb.append(last);
		
		itemlisted = sb.toString();
		}
		return itemlisted;
		
	}
}
