// Deandra Martin, Jenny Goldsher, Hiroki Sato, Miriam Scheinblum
// Group Project
//CMS270
// April 24 2020
import java.util.ArrayList;
//took out boolean status
public class InStorePickUp extends Order{

	//data members
	private String location;
	
	
	
	//constructors	
	public InStorePickUp() {
		
	}
	
	public InStorePickUp(ArrayList<Item> orderLine, String firstName, String lastName, int orderPlacedDate, int trackingNumber, String status, String location) {
		super(orderLine, firstName, lastName, orderPlacedDate, trackingNumber, status);
		this.location = location;
	}

	//getters and setters
	
	public String getLocation() {
		return location;
	}
	
	

	public void setLocation(String location) {
		this.location = location;
	}

	
	
	//other methods
		
	public String toString() {
		String s = "In Store Pickup " + super.toString() + "\nPickup location: " + location;
		return s;
	}
	
	
	
}
