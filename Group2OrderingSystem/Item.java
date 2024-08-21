// Deandra Martin, Jenny Goldsher, Hiroki Sato, Miriam Scheinblum
// Group Project
//CMS270
// April 24 2020

public class Item {
	
	//Data members
	
	private String name;
	private int stockNo;
	private double cost;
	private Supplier supplier;
	
	//------------------------------------------------------------------
	//constructors------------------------------------------------------
	
	public Item() {
		
	}
	
	public Item(String productName,int stockNo,double newCost,Supplier newSupplier) {
		
		name = productName;
		this.stockNo = stockNo;
		cost = newCost;
		supplier = newSupplier;
		
	}
	
	//getters-----------------------------------------------------------
	
	public String getName() {
		return name;
	}
	
	public int getStockNo() {
		return stockNo;
	}
	
	public double getCost() {
		return cost;
	}
	
	public Supplier getSupplier() {
		
		return supplier;
		
	}
	
	//setters-----------------------------------------------------------
	
	public void setName(String newPName) {
		name = newPName;
	}
	
	public void setStockNo(int newStock) {
		stockNo = newStock;
	}
	
	public void setCost(double newCost) {
		cost = newCost;
	}
	
	public void setSupplier(Supplier newSupplier) {
		supplier = newSupplier;
	}
	
	//toString method
	
	public String toString() {
		
		return "Product name: " + name + "\nStock number: "+stockNo + "\nCost: "+ cost
				+ "\nSupplier: " + Supplier.getName();
	}
	
}
