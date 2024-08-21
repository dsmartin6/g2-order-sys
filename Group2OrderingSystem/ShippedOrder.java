// Deandra Martin, Jenny Goldsher, Hiroki Sato, Miriam Scheinblum
// Group Project
//CMS270
// April 24 2020

import java.util.ArrayList;

public class ShippedOrder extends Order {
	
	// *** DATA MEMBERS *** //
	private double shippingCost;
	private String shippingCompany;
	
	
	// ------------------------------------------- //
	
	// *** CONSTRUCTORS *** //
	public ShippedOrder() {
		super();
	}
	
	public ShippedOrder(ArrayList<Item> oL, String fN, String lN, int placedDate, int trackingNumber, String status, String sCompany) {
		super(oL, fN, lN, placedDate, trackingNumber, status);
		shippingCompany = sCompany;
		
	}

	// ------------------------------------------- //
	
	// *** GETTERS AND SETTERS *** //
	public double getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(double shippingCost) {
		this.shippingCost = shippingCost;
	}

	public String getShippingCompany() {
		return shippingCompany;
	}

	public void setShippingCompany(String shippingCompany) {
		this.shippingCompany = shippingCompany;
	}
	
	// ------------------------------------------- //
	
	// *** OTHER METHODS *** //
	
	/* To calculate the cost of a shipped order
	 * method should use the calculateCost() in the
	 * super class and add shipping cost - 10% of the
	 * total cost of the order
	 */
	public double calculateCost() {
		
		double finalCost = super.calculateCost();
		double sCost = finalCost * .10;
		setShippingCost(sCost);
		finalCost = finalCost + sCost;
		setTotalCost(finalCost);
		return finalCost;
	}
	
	/* Method returns a String containing information about a shipped order
	 * including the shipping cost, shipping company and tracking
	 * number
	 */
	public String toString() {
		String scost = String.format("%.2f", shippingCost);
		return "Shipped " + super.toString() + " (includes $" + scost + " shipping fee)\nShipped by " + this.shippingCompany;
				
	}
	
}
