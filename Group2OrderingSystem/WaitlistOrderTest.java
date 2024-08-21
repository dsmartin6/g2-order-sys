// Deandra Martin, Jenny Goldsher, Hiroki Sato, Miriam Scheinblum
// Group Project
//CMS270
// April 24 2020

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class WaitlistOrderTest {

	// test for the non-arg constructor of the the waitlist class
	@Test
	void testWaitlistOrderNon() {
		
		WaitlistOrder test = new WaitlistOrder();
		
		assertTrue(test instanceof WaitlistOrder);
	}
	
	// testing for the waitlist constructor with list of parameters.
	
	@Test
	void testWaitlistOrderArgs() {
		
		Item a = new Item("Basketball",1,34.00,new Supplier("Spring"));
		Item b = new Item("Nike Kobe",2,123.00,new Supplier("Nike"));
		ArrayList<Item> orderline = new ArrayList<Item>(Arrays.asList(a,b));
		ArrayList<String> notInStock = new ArrayList<String>(Arrays.asList("Basketball Rim", "Book"));
		
		
		String firstName = "Hiroki";
		String lastName = "Sato";
		
		int placedDate = 1;
		String status = "Waitlisted";
		String shippingCompany = "Amazon";
		int trackingNumber = 1;
		
		WaitlistOrder expected = new WaitlistOrder(orderline,firstName,lastName,placedDate,trackingNumber,status,shippingCompany,notInStock);
				
		assertTrue(expected instanceof WaitlistOrder);
		
	}
	
	
	//testing if NotInStock, ArrayList of String are returned properly
	@Test 
	void testGetNotInStock() {
		
		Item a = new Item("Basketball",1,34.00,new Supplier("Spring"));
		Item b = new Item("Nike Kobe",2,123.00,new Supplier("Nike"));
		ArrayList<Item> orderline = new ArrayList<Item>(Arrays.asList(a,b));
		ArrayList<String> expected = new ArrayList<String>(Arrays.asList("LED Lightbulb", "4K Cannon Camera"));
		String firstName = "Hiroki";
		String lastName = "Sato";
		//double subTotal = a.getCost() + b.getCost();
		//double totalCost = subTotal + (subTotal*0.10);
		int placedDate = 1;
		String status = "Waitlisted";
		String shippingCompany = "Amazon";
		int trackingNumber = 1;
		WaitlistOrder test = new WaitlistOrder(orderline,firstName,lastName,placedDate,trackingNumber,status,shippingCompany,expected);
		
		ArrayList<String> actual = test.getNotInStock();
		
		assertEquals(expected,actual);
		
	}
	
	// testing if setNotInStock will set the ArrayList of String properly
	
	@Test
	void testSetNotInStock() {
		ArrayList<String> test = new ArrayList<String>(Arrays.asList("Toothbrush", "4K Cannon Camera"));
		WaitlistOrder testOrder = new WaitlistOrder();
		testOrder.setNotInStock(test);
		
		// if we were able to successfully set the ArrayList of String, what getter returns should not be empty.
		
		assertFalse(testOrder.getNotInStock().isEmpty());
	}
	
	//  when we did have names of item that needed for the order, this method test if listNotStcokedItem
	// 	will create the string of name of items needed for the order.
	@Test
	void testListNotStockedItemNotEmpty() {
		
		String item1 = "LED";
		String item2 = "4K Cannon Camera";
		String item3 = "Toshiba TV";
		WaitlistOrder test = new WaitlistOrder();
		
		String expected = item1 +", "+item2 + ", "+item3;
		ArrayList<String> temp = new ArrayList<String>(Arrays.asList(item1,item2,item3));
		test.setNotInStock(temp);
		String actual = test.listNotStockedItem();
		
		assertTrue(expected.equals(actual));
	}
	
	// if we don't have any items needed to complete the order, the list should return N/A
	// testing to see if it returns the correct value.
	@Test
	void testListNotStockedItemEmpty() {
		
		WaitlistOrder test = new WaitlistOrder();
		
		String expected = "N/A";
		
		String actual = test.listNotStockedItem();
		
		assertEquals(expected,actual);
	}
	
	
	// calculateCost in waitlist order doesn't add shipping cost to the total cost, 
	// we are testing to see if the calculateCost returns the correct value
	@Test
	void testCalculateCost() {
		
		Item a = new Item("Basketball",1,34.00,new Supplier("Spring"));
		Item b = new Item("Nike Kobe",2,123.00,new Supplier("Nike"));
		
		ArrayList<Item> orderline = new ArrayList<Item>(Arrays.asList(a,b));
		// expected is the subtotal of the cost of all items in the waitlistOrder
		double expected = 0;
		
		for(int i = 0;i<orderline.size();i++) {
			double cost = orderline.get(i).getCost();
			expected = expected +cost;
		}
		
		WaitlistOrder test = new WaitlistOrder();
		test.setOrderLine(orderline);

		double actual = test.calculateCost();
		
		assertEquals(expected,actual);
	}
		
	// Testing if toString will return the value with expected format.
	@Test
	void testToString() {
		
		Item a = new Item("Basketball",1,34.00,new Supplier("Spring"));
		Item b = new Item("Nike Kobe",2,123.00,new Supplier("Nike"));
		ArrayList<Item> orderline = new ArrayList<Item>(Arrays.asList(a,b));
		ArrayList<String> notInStock = new ArrayList<String>(Arrays.asList("Basketball Rim", "Book"));
		
		
		String firstName = "Hiroki";
		String lastName = "Sato";
		double subTotal = a.getCost() + b.getCost();
		String total = String.format("%.2f", subTotal);
		int placedDate = 1;
		String shippingCompany = "Amazon";
		int trackingNumber = 1;
		String orderedItems = a.getName() + ", " +b.getName();
		
		
		WaitlistOrder expected = new WaitlistOrder(orderline,firstName,lastName,placedDate,trackingNumber,"waitlisted",shippingCompany,notInStock);
		
		String superStr = "Waitlist Order for "+ firstName +" "+lastName +"\nOrder Status: waitlisted"+"\nTracking Number: " + trackingNumber
				+ "\nOrder placed on day "+placedDate+"\nOrder includes: "+ orderedItems
				+ "\nTotal cost:$"+ total;
		
		String expectedStr = superStr + "\nItems needed to complete order: "+ expected.listNotStockedItem();
		expected.calculateCost();
		String actual = expected.toString();
		
		assertEquals(expectedStr,actual);
		
		
	}
	
}
