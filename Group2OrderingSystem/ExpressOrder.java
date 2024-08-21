// Deandra Martin, Jenny Goldsher, Hiroki Sato, Miriam Scheinblum
// Group Project
//CMS270
// April 24 2020

import java.util.ArrayList;

public class ExpressOrder extends ShippedOrder{

	//data members
	//private static final double expressFee = 4.00;
	private boolean notification;
	//constructors
	public ExpressOrder() {
		
	}
	
	//expressFee is not part of the constructor parameters because it is always $4.00
	//add address?
	public ExpressOrder(ArrayList<Item> orderLine, String firstName, String lastName, int orderPlacedDate, int trackingNumber, String status, String shippingCompany, boolean notification) {
	
		super(orderLine, firstName, lastName, orderPlacedDate, trackingNumber, status, shippingCompany);
		this.notification = notification;
		
	}

	//getters and setters
	public boolean getNotification() {
		
		return notification;
		
	}
	
	public void setNotification(boolean notification) {
		
		this.notification = notification;
		
	}
	//other methods
	
	public double calculateCost() {
		
		double sum = super.calculateCost() + 4.00;
		setTotalCost(sum);
		return sum;
	}
	
	public String toString() {
		
		String cost = String.format("%.2f", getTotalCost());
		String scost = String.format("%.2f", getShippingCost());
		String s = super.toString() + "\nExpress Fee: A $4.00 Express Fee has already been added to the Total Cost.";
		return "Express Order for " + getFirstName() + " " + getLastName() + "\nOrder Status: " + getStatus() + "\nTracking Number: " + getTrackingNumber() +  "\nOrder placed on day " + getOrderPlacedDate() +
				"\nOrder includes: " + listOrderedItems() + "\nTotal cost: $" + cost + " (includes $" + scost + " shipping fee and $4.00 express fee)\nShipped by " + getShippingCompany() + "\nNotifications?: " + notification;
	}
	
	
}
