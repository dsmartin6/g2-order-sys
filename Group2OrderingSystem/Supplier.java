// Deandra Martin, Jenny Goldsher, Hiroki Sato, Miriam Scheinblum
// Group Project
//CMS270
// April 24 2020

public class Supplier {
	
	private static String name;
	
	public Supplier(String n) {
		name = n;
	}
	
	public static String getName() {
		return name;
	}
	
	public Item[] sendItems(Item i) {
		Item[] returning = new Item[3];
		for(int j = 0; j < 3; j++) {
			returning[j] = i;
		}
		
		return returning;
	}
	
	
	
}
	