// Deandra Martin, Jenny Goldsher, Hiroki Sato, Miriam Scheinblum
// Group Project
//CMS270
// April 24 2020

import java.util.ArrayList;

public abstract class Order {
	
	// *** DATA MEMBERS *** //
	private ArrayList<Item> orderLine;
	private String firstName;
	private String lastName ;
	private double totalCost;
	private int trackingNumber;
	private String status;
	private int orderPlacedDate;
	
	// ------------------------------------------- //
	
	// *** CONSTRUCTORS *** //
	public Order() {
		orderLine = new ArrayList<Item>();
	}
	
	public Order(ArrayList<Item> oL, String fN, String lN, int placedDate, int trackNum, String status) {
		orderLine = oL;
		firstName = fN;
		lastName = lN;
		orderPlacedDate = placedDate;
		trackingNumber = trackNum;
		this.status = status;
	}
	
	// ------------------------------------------- //
	
	// *** GETTERS AND SETTERS *** //
	public ArrayList<Item> getOrderLine() {
		return orderLine;
	}

	public void setOrderLine(ArrayList<Item> orderLine) {
		this.orderLine = orderLine;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public int getTrackingNumber() {
		return trackingNumber;
	}
	
	public void setTrackingNumber(int newTrackingNum) {
		this.trackingNumber = newTrackingNum;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String newStatus) {
		this.status = newStatus;
	}
	
	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public int getOrderPlacedDate() {
		return orderPlacedDate;
	}

	public void setOrderPlacedDate(int orderPlacedDate) {
		this.orderPlacedDate = orderPlacedDate;
	}
	
	// ------------------------------------------- //
	
	// *** OTHER METHODS *** //
	
	// To calculate the total cost of an order
	public double calculateCost() {
		double total = 0;
		for(int i = 0; i < this.orderLine.size(); i++) {
			total = total + this.orderLine.get(i).getCost();
		}
		setTotalCost(total);
		return total;
	}
	
	// To create a list of name of items in the order
	
	public String listOrderedItems() {
		
		StringBuilder sb = new StringBuilder();
		String items;
		int currentIndex = 0;
		while(currentIndex <orderLine.size()) {
			String temp = orderLine.get(currentIndex).getName();
			sb.append(temp);
			if(currentIndex != orderLine.size()-1) {
			sb.append(", ");
			}
			currentIndex++;
		}
		
		items = sb.toString();
		return items;
	}
	
	// Return a String containing information about an order
		public String toString() {
			String cost = String.format("%.2f", totalCost);
			return "Order for " + "Name: " + this.firstName + " " + this.lastName + "\nOrder Status: " +status+"\nOrder placed on day " + this.orderPlacedDate +"\nTracking number: "+trackingNumber+
					"\nOrder includes:" + listOrderedItems() + "\nTotal cost:$" + cost;
		}

	
}
