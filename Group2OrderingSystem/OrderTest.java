// Deandra Martin, Jenny Goldsher, Hiroki Sato, Miriam Scheinblum
// Group Project
//CMS270
// April 24 2020

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;

class OrderTest {

	@Test
	void testOrderNoArgs() {
		Order o = new ShippedOrder(); // Order class cannot be instantiated, 
									  // only if the actual type is one of its subclass
		assertTrue(o instanceof Order);
	}
	
	// ------------------------------------------- //

	@Test
	void testOrderArgs() {
		ArrayList<Item> orderLine = new ArrayList<>();
		Supplier s = new Supplier("Spring");
		Item a = new Item("Basketball", 1, 34.00, s);
		
		orderLine.add(a);
		
		Order o = new ShippedOrder(orderLine, "Deandra", "Martin", 4, 4, "Shipped", "Spring");
		
		assertTrue(o instanceof Order);
	}
	
	// ------------------------------------------- //

	@Test
	void testGetOrderLine() {
		ArrayList<Item> orderLine = new ArrayList<>();
		Order o = new ShippedOrder(orderLine, "Deandra", "Martin", 4, 4, "Shipped", "Spring");
		ArrayList<Item> actual = o.getOrderLine();
		
		assertEquals(orderLine, actual);
	}

	// ------------------------------------------- //
	
	@Test
	void testSetOrderLine() {
		ArrayList<Item> orderLine = new ArrayList<>();
		ArrayList<Item> expected = new ArrayList<>();
		Order o = new ShippedOrder(orderLine, "Deandra", "Martin", 4, 4, "Shipped", "Spring");
		
		Supplier s = new Supplier("FedEx"); 
		Item a = new Item("Roku", 1, 134.00, s);
		
	    orderLine.add(a);
	    expected.add(a);
	    
	    assertEquals(o.getOrderLine(), expected);
		
	}
	
	// ------------------------------------------- //

	@Test
	void testGetFirstName() {
		ArrayList<Item> orderLine = new ArrayList<>();
		Order o = new ShippedOrder(orderLine, "Deandra", "Martin", 4, 4, "Shipped", "Spring");
		String fname = o.getFirstName();
		
		assertTrue(fname.equals("Deandra"));
	}
	
	// ------------------------------------------- //

	@Test
	void testSetFirstName() {
		ArrayList<Item> orderLine = new ArrayList<>();
		Order o = new ShippedOrder(orderLine, "Deandra", "Martin", 4, 4, "Shipped", "Spring");
		o.setFirstName("Miriam");
		
		assertTrue(o.getFirstName().equals("Miriam"));
	}
	
	// ------------------------------------------- //

	@Test
	void testGetLastName() {
		ArrayList<Item> orderLine = new ArrayList<>();
		Order o = new ShippedOrder(orderLine, "Deandra", "Martin", 4, 4, "Shipped", "Spring");
		String lname = o.getLastName();
		
		assertTrue(lname.equals("Martin"));
	}

	// ------------------------------------------- //
	
	@Test
	void testSetLastName() {
		ArrayList<Item> orderLine = new ArrayList<>();
		Order o = new ShippedOrder(orderLine, "Deandra", "Martin", 4, 4, "Shipped", "Spring");
		o.setLastName("Scheinblum");
		
		assertTrue(o.getLastName().equals("Scheinblum"));
	}
	
	// ------------------------------------------- //

	@Test
	void testGetTrackingNumber() {
		ArrayList<Item> orderLine = new ArrayList<>();
		Order o = new ShippedOrder(orderLine, "Deandra", "Martin", 4, 4, "Shipped", "Spring");
		int trackNo = o.getTrackingNumber();
		
		assertEquals(4, trackNo);
	}
	
	// ------------------------------------------- //

	@Test
	void testSetTrackingNumber() {
		ArrayList<Item> orderLine = new ArrayList<>();
		Order o = new ShippedOrder(orderLine, "Deandra", "Martin", 4, 4, "Shipped", "Spring");
		o.setTrackingNumber(6);
		
		assertEquals(6, o.getTrackingNumber());
	}
	
	// ------------------------------------------- //

	@Test
	void testGetStatus() {
		ArrayList<Item> orderLine = new ArrayList<>();
		Order o = new ShippedOrder(orderLine, "Deandra", "Martin", 4, 4, "Shipped", "Spring");
		String status = o.getStatus();
		
		assertTrue(status.equals("Shipped"));
	}
	
	// ------------------------------------------- //
	
	@Test
	void testSetStatus() {
		ArrayList<Item> orderLine = new ArrayList<>();
		Order o = new ShippedOrder(orderLine, "Deandra", "Martin", 4, 4, "Shipped", "Spring");
		o.setStatus("Processing Order");
		
		assertTrue(o.getStatus().equals("Processing Order"));
	}
	
	// ------------------------------------------- //

	@Test
	void testGetTotalCostIfOrderLineIsEmpty() {
		ArrayList<Item> orderLine = new ArrayList<>();
		Order o = new ShippedOrder(orderLine, "Deandra", "Martin", 4, 4, "Shipped", "Spring");
		double totalCost = o.getTotalCost();
		
		assertTrue(totalCost == 0.0);	
	}
	
	// ------------------------------------------- //

	@Test
	void testCalculateCost() {
		ArrayList<Item> orderLine = new ArrayList<>();
		Order o = new ShippedOrder(orderLine, "Deandra", "Martin", 4, 4, "Shipped", "Spring");
		Supplier s = new Supplier("Amazon");
		Item a = new Item("Roku", 1, 134.00, s);
		Item b = new Item("Emerson TV", 1, 500.50, s);
			  
		orderLine.add(a);
		orderLine.add(b);
			
		double cost = o.calculateCost();
			
		assertEquals(o.getTotalCost(), cost);		 
	}
		
	// ------------------------------------------- //

	@Test 
	void testGetTotalCostIfOrderLineIsNotEmpty() { 
		ArrayList<Item> orderLine = new ArrayList<>(); 
		Order o = new ShippedOrder(orderLine, "Deandra", "Martin", 4, 4, "Shipped", "Spring"); 
		Supplier s = new Supplier("Amazon");
		Item a = new Item("Roku", 1, 134.00, s);
		Item b = new Item("Emerson TV", 1, 500.50, s);
		
		orderLine.add(a);
		orderLine.add(b);
		
		double totalCost = o.calculateCost();
		double cost = o.getTotalCost();
	  
		assertEquals(cost, totalCost);
	}
	 
	// ------------------------------------------- //

	@Test
	void testSetTotalCost() {
		ArrayList<Item> orderLine = new ArrayList<>();
		Order o = new ShippedOrder(orderLine, "Deandra", "Martin", 4, 4, "Shipped", "Spring");
		o.setTotalCost(100.00);
		
		assertTrue(o.getTotalCost() == 100.00);
	}
	
	// ------------------------------------------- //

	@Test
	void testGetOrderPlacedDate() {
		ArrayList<Item> orderLine = new ArrayList<>();
		Order o = new ShippedOrder(orderLine, "Deandra", "Martin", 4, 4, "Shipped", "Spring");
		int date = o.getOrderPlacedDate();
		
		assertEquals(4, date);
	}
	
	// ------------------------------------------- //

	@Test
	void testSetOrderPlacedDate() {
		ArrayList<Item> orderLine = new ArrayList<>();
		Order o = new ShippedOrder(orderLine, "Deandra", "Martin", 4, 4, "Shipped", "Spring");
		o.setOrderPlacedDate(8);
		
		assertEquals(8, o.getOrderPlacedDate());
	}
	
	// ------------------------------------------- //

	@Test
	void testListOrderedItems() {
		ArrayList<Item> orderLine = new ArrayList<>();
		Order o = new ShippedOrder(orderLine, "Deandra", "Martin", 4, 4, "Shipped", "Spring");
		Supplier s = new Supplier("Amazon");
		Item a = new Item("Roku", 1, 134.00, s);
		Item b = new Item("Emerson TV", 1, 500.50, s);
		
		orderLine.add(a);
		orderLine.add(b);
		
		String list = o.listOrderedItems();
		String expected = "Roku, Emerson TV";
		
		assertEquals(expected, list);		
	}
	
	// ------------------------------------------- //

	@Test
	void testToString() {
		
		ArrayList<Item> orderLine = new ArrayList<>();
		Order o = new ShippedOrder(orderLine, "Deandra", "Martin", 4, 4, "Shipped", "Spring");
		Supplier s = new Supplier("Amazon");
		Item a = new Item("Roku", 1, 134.00, s);
		Item b = new Item("Emerson TV", 1, 500.50, s);
		
		orderLine.add(a);
		orderLine.add(b);
		
		String actual = o.toString();
		String expected = "Shipped Order for Name: Deandra Martin\nOrder Status: Shipped\nOrder placed on day 4\nTracking number: 4\nOrder includes:Roku, Emerson TV\n"
				+ "Total cost:$0.00 (includes $0.00 shipping fee)\nShipped by Spring";
		
		assertEquals(expected, actual);
	}
	
}