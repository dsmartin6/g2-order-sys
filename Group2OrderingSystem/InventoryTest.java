// Deandra Martin, Jenny Goldsher, Hiroki Sato, Miriam Scheinblum
// Group Project
//CMS270
// April 24 2020

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class InventoryTest {

	
	//------------------------------------------------------

	@Test
	void testRecieveItem() {

		Supplier s = new Supplier("US");
		Item item = new Item("Paper towel", 1 , 3.00 , s);
		Inventory i = new Inventory();
		i.recieveItem(item);
		
		int num = i.checkCount(item);
		
		assertTrue(num == 1);
	}
	
	
	//------------------------------------------------------

	@Test
	void testConstructor() {
		
		Inventory i = new Inventory();
		
		assertNotNull(i);
	}
	
	
	//------------------------------------------------------

	@Test
	void testCheckForItemInInventory() {
		Supplier s = new Supplier("US");
		Item item = new Item("Paper towel", 1 , 3.00 , s);
		Inventory i = new Inventory();
		i.recieveItem(item);
		
		boolean value = i.checkForItem("Paper towel");
		
		assertTrue(value == true);
		
	}

	
	//------------------------------------------------------
	
	@Test
	void testCheckForItemNotInInventory() {
		Inventory i = new Inventory();
		
		boolean value = i.checkForItem("Towel");
		
		assertTrue(value == false);
		
	}
	
	//------------------------------------------------------

	@Test
	void testPutItemInOrder() {
		Supplier s = new Supplier("US");
		Item item = new Item("Paper towel", 1 , 3.00 , s);
		Inventory i = new Inventory();
		i.recieveItem(item);
		
		Item value = i.putItemInOrder(item);
				
		assertTrue(value.getStockNo() == item.getStockNo());
		
	}

	//------------------------------------------------------

	@Test
	void testPutItemInOrderNotInInventory() {
		Supplier s = new Supplier("US");
		Item item = new Item("Paper towel", 1 , 3.00 , s);
		Inventory i = new Inventory();
		
		
		Item value = i.putItemInOrder(item);
				
		assertTrue(value.getStockNo() == -1);
		
	}
	
	//------------------------------------------------------
	
	@Test
	void testSearchItemInInventory() {
		Inventory i = new Inventory();
		Supplier s = new Supplier("US");
		
		Item temp = new Item("Paper towel", 1 , 3.00 , s);
		i.recieveItem(temp);;
		
		Item temp2 = i.searchItem("Paper towel");
		
		assertEquals(temp, temp2);
	}
	
	//------------------------------------------------------
	
	@Test
	void testSearchItemNotInInventory() {
		Inventory i = new Inventory();
		Supplier s = new Supplier("US");
		
		Item temp = new Item("Paper towel", 1 , 3.00 , s);
		i.recieveItem(temp);;
		
		Item temp2 = i.searchItem("Towel");
		
		assertFalse(temp.equals(temp2));
	}
	
	//------------------------------------------------------
	
	@Test
	void testCheckCount() {
		Supplier s = new Supplier("US");
		Item item = new Item("Paper towel", 1 , 3.00 , s);
		Item item2 = new Item("Paper towel", 1 , 3.00 , s);
		Inventory i = new Inventory();
		i.recieveItem(item);
		i.recieveItem(item2);
		
		Item paperTowel = new Item("Paper towel", 1 , 3.00 , s);
		
		int count = i.checkCount(paperTowel);
		
		assertTrue(count == 2);
		
	}
	
	//------------------------------------------------------

	@Test
	void testCheckCountZero() {
		Supplier s = new Supplier("US");
		Inventory i = new Inventory();
	
		
		Item paperTowel = new Item("Paper towel", 1 , 3.00 , s);
		
		int count = i.checkCount(paperTowel);
		
		assertTrue(count == 0);
	}
	
	//------------------------------------------------------

	@Test
	void testprintInventory() {
		Supplier s = new Supplier("US");
		Item item = new Item("Paper towel", 1 , 3.00 , s);
		Item item2 = new Item("Lemon", 2 , 3.00 , s);
		Item item3 = new Item("Lemon", 2 , 3.00 , s);
		Inventory i = new Inventory();
		i.recieveItem(item);
		i.recieveItem(item2);
		i.recieveItem(item3);
		
		String value = i.printInventory();
		
		String desired = "Paper towel" + "\n" + "Lemon" + "\n" + "Lemon";
		
		assertTrue(desired.equals(value));
	}
	
	//------------------------------------------------------
	
	@Test
	void testprintInventoryEmpty() {
		Inventory i = new Inventory();
		
		String value = i.printInventory();
		
		String desired = "";
		
		assertTrue(desired.equals(value));
		
	}
	
	//------------------------------------------------------

	@Test
	void testRequestItem() {
		Inventory i = new Inventory();
		Supplier s = new Supplier("s");
		Item item = new Item("Paper towel", 1 , 3.00 , s);
		
		Item found = i.requestItem(item);
		
		assertTrue(found.equals(item));
	}
	
	
	//------------------------------------------------------

	@Test
	void testNeedRestock() {
	
		Supplier s = new Supplier("US");
		Item item = new Item("Paper towel", 1 , 3.00 , s);
		Item item2 = new Item("Lemon", 2 , 3.00 , s);
		Item item3 = new Item("Lemon", 2 , 3.00 , s);
		Inventory i = new Inventory();
		i.recieveItem(item);
		i.recieveItem(item2);
		i.recieveItem(item3);
		
		String value = i.needRestock();
		
		String desired = "&Paper towel&Lemon&";
		
		assertTrue(value.equals(desired));
	}
	
	//------------------------------------------------------

	@Test
	void testNeedRestockNONE() {
	
		Supplier s = new Supplier("US");
		Item item = new Item("Paper towel", 1 , 3.00 , s);
		Inventory i = new Inventory();
		i.recieveItem(item);
		i.recieveItem(item);
		i.recieveItem(item);
		
		String value = i.needRestock();
		
		String desired = "No items needed to be restocked";
		
		assertTrue(value.equals(desired));
	}
	
	//------------------------------------------------------
	
	@Test
	void testPrintInventory() {
		Supplier s = new Supplier("US");
		Item item = new Item("Paper towel", 1 , 3.00 , s);
		Item item2 = new Item("Lemon", 2 , 3.00 , s);
		Inventory i = new Inventory();
		i.recieveItem(item);
		i.recieveItem(item2);

		String list = i.printInventory();
		String expected = "Paper towel\nLemon";
		
		assertEquals(expected, list);
		
	}
	
	
}