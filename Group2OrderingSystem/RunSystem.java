// Deandra Martin, Jenny Goldsher, Hiroki Sato, Miriam Scheinblum
// Group Project
//CMS270
// April 24 2020

import java.util.*;
import java.io.*;

public class RunSystem {
	
	private static Inventory inventory;
	private static int day;
	private static int trackingNumber;
	//----------------------------------------------------------
	
	
	public static void main(String[] args) {
		
		day=0;
		trackingNumber=1;
		Scanner userInput = null;
		PrintWriter printUpdate = null;
		PrintWriter printActivity = null;
		// all of our input/output files needed to take inputs and print output
		
		File file1 = new File("Inventory.txt");
		File file2 = new File("UserInput.txt");
		File customerOutput = new File("CustomerOutput.txt");
		File companyActivity = new File("OrderLog.txt");
		String restockRequest = "N/A";
		ArrayList<Order> orderHolder = new ArrayList<Order>();
		
		// loading inventory based on the input file, Inventory.txt
		loadInventory(file1);
		
		try {
		
			//2. read input from a customer. 
			userInput = new Scanner(file2);
			printUpdate = new PrintWriter(customerOutput);
			printActivity = new PrintWriter(companyActivity);
			
			//helper method 10;
			printWelcomeMessage(printUpdate);
			
			while(userInput.hasNext()) {
				day+=1;
				
				notify(orderHolder, printUpdate);
				//every 4 days we check if there's any thing to be restocked and restock.
				
				if(day%4 == 0){
					
					//if restockRequest is not equal to "No items needed to be restocked."
					if(!(restockRequest.equals("No items needed to be restocked"))) {
						restockInventory(restockRequest); 
					}
				}
				
				for(int i = 0; i < orderHolder.size(); i++) {
					//express order status changes each day
					if (orderHolder.get(i) instanceof ExpressOrder) {
						if (orderHolder.get(i).getStatus().equals("Order Placed")){
							orderHolder.get(i).setStatus("Shipped");
						}
						else {
							orderHolder.get(i).setStatus("Delivered");
						}
					}
					else if(orderHolder.get(i) instanceof InStorePickUp) {
						orderHolder.get(i).setStatus("Ready for pickup");
					}
					else if( orderHolder.get(i) instanceof WaitlistOrder) {
						
						WaitlistOrder check = (WaitlistOrder)orderHolder.get(i);
						
						if(!(check.listNotStockedItem().equals("N/A"))) {
							
						check = WaitlistOrderUpdate(check);
						
						if(check.getStatus().equals("Auto-Cancel")) {
						//print out the name on the customer output and tell them your waitlist order had been cancelled.
						// then remove the order
							printCancelMessage(check,printUpdate);
							orderHolder.remove(i);
						}
					}
				}
					else{
						// this represents how many days it has passed after order was placed.
						int time = day - orderHolder.get(i).getOrderPlacedDate();
						
						if(time == 2) {
							orderHolder.get(i).setStatus("Shipped");
						}
						if(time == 3 || time == 4) {
							Random r = new Random();
							int deliverSpeed = r.nextInt(1);
							if (deliverSpeed == 0) {
								orderHolder.get(i).setStatus("Delivered");
							}
						}
						else if(time == 5) {
							orderHolder.get(i).setStatus("Delivered");
						}
					}
				}
			
				String action = userInput.nextLine();
				ArrayList<Order> tempOrder;
			
				if(action.equals("MO")) {
					//call create order
					String customerInfo = userInput.nextLine();
			
					//read the next line that contains information of Item 
					String orderedItem = userInput.nextLine();
					//String[]items = orderedItem.split(",");
					tempOrder = createOrder(customerInfo,orderedItem,printUpdate);
					
					if(tempOrder.get(0).getTrackingNumber()!= -1) {
					for(int i = 0;i < tempOrder.size();i++) {
						Order temp = tempOrder.get(i);
						orderHolder.add(temp);
					}
				}
			}
				else if(action.equals("T")) {
					//call trackOrder
					String data = userInput.nextLine();
					int trackingNumber = Integer.parseInt(data);
					TrackOrder(orderHolder,printUpdate,trackingNumber);
				}
				
				restockRequest = inventory.needRestock();
				
				updateCompanyActivity(orderHolder, printActivity);
				//removes orders that have been delivered already before today
				for(int i = 0; i < orderHolder.size(); i++) {
					String status = orderHolder.get(i).getStatus();
					
					if (status.equals("Delivered")) {
						//we should let the company know that this order had been delivered and will be deleted from the systen.
						printWarningOrderDelivered(orderHolder.get(i),printActivity);
						orderHolder.remove(i);
					}
					else if (status.contentEquals("Ready for pickup")) {
						Random r = new Random();
						int n = r.nextInt(2);
						
						//if random integer is 1, the customer has picked up the order
						//otherwise, we leave the order in the system and check again tomorrow
						if (n == 1) {
							printWarningOrderPickedUp(orderHolder.get(i), printActivity);
							orderHolder.remove(i);	
						}
					}
				}
			}
		}
		catch(FileNotFoundException e) {
		
			System.out.println("File could not be found.");
		}
		finally {
			if(userInput != null) {
				userInput.close();
			}
			if(printUpdate != null) {
				printUpdate.close();
			}
			if(printActivity != null) {
				printActivity.close();
			}
		}
	}
	
	//----------------------------------------------------------------------------------------
	
public static void loadInventory(File file)  {
		
		inventory = new Inventory();
		ArrayList<Item>sample = new ArrayList<>();
		try {	
		Scanner in = new Scanner(file);
		String supplierName = in.nextLine();
		
		while(in.hasNextLine()) {
			
			
			String line = in.nextLine();
			String[] tokens = line.split(",");
			int quantity = Integer.parseInt(tokens[0]);
			String name = tokens[1];
			double cost = Double.parseDouble(tokens[3]);
		
			int sNum = Integer.parseInt(tokens[2]);
			Supplier s = new Supplier(supplierName);
			for(int i = 0;i<quantity;i++) {
			
				Item temp = new Item(name,sNum,cost,s);
				inventory.recieveItem(temp);
			}
			Item Itemsample = new Item(name,sNum,cost,s);
			sample.add(Itemsample);
			
		}
		inventory.setSampleItems(sample);
	}
		catch(FileNotFoundException e) {
			System.out.println("File could not be found.");
		}
	}
	
	//----------------------------------------------------------------------------------------
	// Read the line from scanner that we pas from the argument. 
	//determines what type of order should be created and returns the order
	//this is possible by definition of inclusion polymorphism.
	
	public static ArrayList<Order> createOrder(String customerInfo,String ordered,PrintWriter out) {
		
		ArrayList<Order> order = new ArrayList<Order>();
		
		//read the line for customer information and broke the string into array of string
		
		String[] token = customerInfo.split(",");
		//read the next line that contains information of Item 
		//String orderedItem = scan.nextLine();
		String[]items = ordered.split(",");
		
		if(token[0].equals("SO")) {
			//call create shipped order. 
			order = createShippedOrder(token,items);
			//also, for when item is not available take last index of array that contains whether to merge order or split. 
		
		}
		else if(token[0].equals("EXPO")) {
			//call createExpress order
			order = createExpressOrder(token,items);
			//also, for when item is not available take last index of array that contains whether to merge order or split
			
		}
		else if(token[0].equals("ISPU")) {
			//call create InStorePickUp
			InStorePickUp ISPU = createInStorePickUp(token,items,out);
			order.add(ISPU);
		}
		return order;
	}
	
	//----------------------------------------------------------------------------------------
	//TrackOrder method: This is one of the functionality that user have access to, it takes the tracking number
	// that customer provided and search through the orderHolder, if the tracking number matches it 
	//it returns the name of customer and status of the order. 
	
	public static void TrackOrder(ArrayList<Order> orderHolder,PrintWriter printUpdate, int trackNum) {
		//first check if orderHolder was empty or not
		if(orderHolder.isEmpty() || trackNum > trackingNumber){
			
			printUpdate.println("---------------------------------------------------------------");
			printUpdate.println();
			printUpdate.println("Any order hasn't been placed yet.");
			printUpdate.println();
			printUpdate.println("---------------------------------------------------------------");
			printUpdate.println();
			
		}
		// if user tried to enter tracking number that is larger than the tracking number produced
		// it shows as error. 
		else if(trackNum > trackingNumber) {
			ErrorMessageInvalidTNum(printUpdate);
		}
		
		else {
			
			boolean found = false;
			int index = 0;
			// we are going to traverse the orderHolder until we find the matching track number or, until 
			// the track number at index exceed the target track number
			while(found == false && index < orderHolder.size()) {
				int tempTNum = orderHolder.get(index).getTrackingNumber();
				if(tempTNum == trackNum) {
					found = true;
				}
				else {
					index++;
				}
			}
			// if loop terminates and the index was the size of the orderHolder which indicates that the order matches wasn't found
			// it is considered as a error and print out error message.
			if(index == orderHolder.size()) {
				ErrorMessageInvalidTNum(printUpdate);
			}
			else {
			String orderType;
			String name = orderHolder.get(index).getFirstName() + " "+ orderHolder.get(index).getLastName();
			String status = orderHolder.get(index).getStatus();
			
			if(orderHolder.get(index) instanceof InStorePickUp) {
				orderType = "In store pick up";
				printUpdate.println("Order Type: "+orderType);
				printUpdate.println("Name: "+ name);
				printUpdate.println("Status: "+ status);
			}
			
			else if(orderHolder.get(index) instanceof ShippedOrder) {
				if(orderHolder.get(index) instanceof WaitlistOrder) {
					orderType = "Waitlist order";
				}
				else {
					orderType ="Shipped order";
				}
				printUpdate.println("Order Type: "+orderType);
				printUpdate.println("Name: "+ name);
				printUpdate.println("Status: "+ status);
			}
		}
				printUpdate.println("---------------------------------------------------------------");
				printUpdate.println();
			}	
	}

	//----------------------------------------------------------------------------------------
	// create a InStorePickUp order, if any items on order is not in stock, then 
	
	public static InStorePickUp createInStorePickUp(String[]customerInfo, String[]itemsList, PrintWriter printUpdate) {
		
		InStorePickUp order = new InStorePickUp();
		
		//InStorePickUp order;
		int tnum = trackingNumber;
		trackingNumber+=1;
		String fname = customerInfo[1];
		String lname = customerInfo[2];
		String location = customerInfo[3];
		
		//helper method1.
		boolean inStock = checkOrderedItems(itemsList);
		boolean Quantity;
		
		Random r = new Random();
		int n = r.nextInt(1);
		String status = "Processing Order";
		if (n == 0) {
			status = "Ready for Pickup";
		}
		
		if(inStock == true) {
			//helper method 2: check quantity
			Quantity = checkQuantity(itemsList);
			
			if(Quantity == true) {
				ArrayList<Item> cart = new ArrayList<Item>();
				
				for(int i = 0;i<itemsList.length;i++) {
					
					Item searched = inventory.searchItem(itemsList[i]);
					Item temp = inventory.putItemInOrder(searched);
					cart.add(temp);
				}
				
				order = new InStorePickUp(cart, fname, lname, day, tnum, status, location);
				double totalCost = order.calculateCost();
				return order;
			}
				else {
					ArrayList<Item> place = new ArrayList<Item>();
					ArrayList<String>remove = new ArrayList<String>();
					
					for(int i = 0;i <itemsList.length;i++) {
						Item temp = inventory.searchItem(itemsList[i]);
						if(temp.getStockNo() == -1) {
							remove.add(itemsList[i]);
						}
						else {
							place.add(inventory.putItemInOrder(temp));
						}
					}
					
					// if arrayList of item is empty which means that there's no item that can be in the order,
					// the order is cancelled.
					if(place.isEmpty()) {
						
						printUpdate.println(fname +","+"Your order could not go through, please try again.");
						InStorePickUp bad = new InStorePickUp();
						bad.setTrackingNumber(-1);
						return bad;
					}
					else {
						printISPUpdate(fname,lname,remove,printUpdate);
					}
					
					order = new InStorePickUp(place, fname, lname, day, tnum, status, location);
					
					double totalCost = order.calculateCost();
					return order;
				}
			
		}
		else {
			ArrayList<Item> place = new ArrayList<Item>();
			ArrayList<String>remove = new ArrayList<String>();
			
			for(int i = 0;i <itemsList.length;i++) {
				Item temp = inventory.searchItem(itemsList[i]);
				if(temp.getStockNo() == -1) {
					remove.add(itemsList[i]);
				}
				else {
					place.add(inventory.putItemInOrder(temp));
				}
			}
			
			// if arrayList of item is empty which means that there's no item that can be in the order,
			// the order is cancelled.
			if(place.isEmpty()) {
				printUpdate.println(fname +","+"Your order could not go through, please try again.");
				InStorePickUp bad = new InStorePickUp();
				bad.setTrackingNumber(-1);
				return bad;
			}
			else {
				printISPUpdate(fname,lname,remove,printUpdate);
			}
			
			order = new InStorePickUp(place, fname, lname, day, tnum, status, location);
			
			double totalCost = order.calculateCost();
			return order;
		}
	}
	
	
	//----------------------------------------------------------------------------------------
	//CreateShippedOrder: this method creates shipped order and waitlist order if needed and is taking two parameters
	
	public static ArrayList<Order> createShippedOrder(String[] customerInfo,String[]itemsOrdered){
		
		ArrayList<Order> order = new ArrayList<Order>();
		ArrayList<Item> place;
		ArrayList<String>itemNeeded;
		ShippedOrder so;
		WaitlistOrder wo;
		//before we make order, we want to make sure if items in order are currently inStock or not
		//helper method 1.
		
		int tnum = trackingNumber;
		trackingNumber+=1;
		String fname = customerInfo[1];
		String lname = customerInfo[2];
		String shippingCompany = customerInfo[3];
		String waitlistPreference = customerInfo[customerInfo.length-1];
		boolean inStock = checkOrderedItems(itemsOrdered);
		// if it was true, next we need to make sure we have quantity of that item needed. 
		// if boolean is false, then we will start creating order. whether it will be split or not is depending on the user
		boolean Quantity;
		if(inStock == true) {
			// helper method 2: check quantity 
			Quantity = checkQuantity(itemsOrdered);
			//this should be possible only when items are in stock and have enough quantity. 
			if(Quantity == true) {
				
				// might be reusable as a method to truly just create shipped order instead of decision making. line 120 to 132. 
			
				// we are not sure about placed date yet.	
				 place = new ArrayList<Item>();
				
				for(int i = 0;i<itemsOrdered.length;i++) {
						
					//first we just search for the item if it's in the inventory,
					Item searched = inventory.searchItem(itemsOrdered[i]);
					//based on the search result, we call putItemInOrder, when this method is involked, Item object in inventory will be removed
					// and temp will save what we take out from inventory for the order. 
					Item temp = inventory.putItemInOrder(searched);
					
					place.add(temp);
					
				}
				
				so = new ShippedOrder(place,fname,lname,day,tnum,"Processing Order",shippingCompany);
				
				so.calculateCost();
				order.add(so);
				
			}
		}
		else {
			//this else block execute when we have item but we don't have enough of them. 
			// and if user wanted to split the order, inside this if statement it create waitlist order and 
			//shipped order
			
				if(waitlistPreference.equals("Split")) {
					// we save the tracking number temporary, and after saving the tracking number
					// we increment the tracking number by one for the next order to be placed. 
					int tnum2 = trackingNumber;
					trackingNumber+=1;
					
					
					// these arraylist help us to separate items that are in the inventory that we can take out to put into 
					// the shipped order and name of items that we need for the waitlist order to be completed.
					
					place = new ArrayList<Item>();
					itemNeeded = new ArrayList<String>();
				
					for(int i = 0;i<itemsOrdered.length;i++) {
						Item temp = inventory.searchItem(itemsOrdered[i]);
						if(temp.getStockNo() == -1) {
							itemNeeded.add(itemsOrdered[i]);
						}
						else {
							place.add(inventory.putItemInOrder(temp));
							
						}
					}
					
					so = new ShippedOrder(place,fname,lname,day,tnum,"Processing Order",shippingCompany);
					ArrayList<Item> split = new ArrayList<Item>();
					wo = new WaitlistOrder(split,fname,lname,day,tnum2,"Waitlisted",shippingCompany,itemNeeded);
				
					so.calculateCost();
					wo.calculateCost();
					order.add(so);
					order.add(wo);
				}
				
				else if(waitlistPreference.equals("Merge")) {
					//create only waitlist order
					
					place = new ArrayList<Item>();
					itemNeeded = new ArrayList<String>();
					
					for(int i = 0;i<itemsOrdered.length;i++) {
						
						Item temp = inventory.searchItem(itemsOrdered[i]);
						if(temp.getStockNo() == -1) {
							itemNeeded.add(itemsOrdered[i]);
						}
						else {
							place.add(inventory.putItemInOrder(temp));
						}
					}
					wo = new WaitlistOrder(place,fname,lname,day,tnum,"Waitlisted",shippingCompany,itemNeeded);
					wo.calculateCost();
					
					order.add(wo);
				}
			}
					return order;
	}
	
	
	
	//----------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------
	
	public static ArrayList<Order> createExpressOrder(String[] customerInfo,String[]itemsOrdered){
		
		ArrayList<Order> order = new ArrayList<Order>();
		ExpressOrder expro;
		WaitlistOrder wo;
		ArrayList<Item> place;
		ArrayList<String> itemNeeded;
		int tnum = trackingNumber;
		trackingNumber+=1;
		
		String fname = customerInfo[1];
		String lname = customerInfo[2];
		String shippingCompany = customerInfo[3];
		boolean notification = Boolean.parseBoolean(customerInfo[4]);
		String waitlistPreference = customerInfo[customerInfo.length-1];
		
		boolean inStock = checkOrderedItems(itemsOrdered);
		
		if(inStock == true) {
			
		boolean Quantity = checkQuantity(itemsOrdered);
		//this is in the situation we have enough stock to complete the order. 
		
		if(Quantity == true) {
			//create only express order
			
			
			place = new ArrayList<Item>();
			
			//double subtotal = 0;
			for(int i = 0;i<itemsOrdered.length;i++) {
				//we could definitely simplfy here. 	
				//first we just search for the item if it's in the inventory,
				Item searched = inventory.searchItem(itemsOrdered[i]);
				//based on the search result, we call putItemInOrder, when this method is involked, Item object in inventory will be removed
				// and temp will save what we take out from inventory for the order. 
				Item temp = inventory.putItemInOrder(searched);
			
				place.add(temp);
			}
			
			expro = new ExpressOrder(place,fname,lname,day,tnum,"Shipped",shippingCompany,notification);
			expro.calculateCost();
			
			order.add(expro);
		}
		}
		else {
			if(waitlistPreference.equals("Split")) {
				
				int tnum2 = trackingNumber;
				trackingNumber+=1;
				
				 place = new ArrayList<Item>();
				 itemNeeded = new ArrayList<String>();
				 
				for(int i = 0;i<itemsOrdered.length;i++) {
					Item temp = inventory.searchItem(itemsOrdered[i]);
					if(temp.getStockNo() == -1) {
						itemNeeded.add(itemsOrdered[i]);
					}
					else {
						place.add(inventory.putItemInOrder(temp));
						
					}
				}
			
				expro = new ExpressOrder(place,fname,lname,day,tnum,"Shipped",shippingCompany,notification);
				expro.calculateCost();
				ArrayList<Item>waitlist = new ArrayList<Item>();
				wo = new WaitlistOrder(waitlist,fname,lname,day,tnum2,"Waitlisted",shippingCompany,itemNeeded);
				wo.calculateCost();
				;
				order.add(expro);
				order.add(wo);
			}
			
			else if(waitlistPreference.equals("Merge")) {
			
				 place = new ArrayList<Item>();
				 itemNeeded = new ArrayList<String>();
				
				for(int i = 0;i<itemsOrdered.length;i++) {
					
					Item temp = inventory.searchItem(itemsOrdered[i]);
					
					if(temp.getStockNo() == -1) {
						itemNeeded.add(itemsOrdered[i]);
					}
					else {
						place.add(inventory.putItemInOrder(temp));
					}
				}
				wo = new WaitlistOrder(place,fname,lname,day,tnum,"Waitlisted",shippingCompany,itemNeeded);
				wo.calculateCost();
				order.add(wo);
			}
		}
		return order;
	}
	
								//*Helper Methods//
	
	//----------------------------------------------------------------------------------------
	//helper method 1:
	public static boolean checkOrderedItems(String[]itemList) {
		boolean inStock = true;
		int i = 0;
		//this loop is going to run as long as each item are in the inventory, if it was not in the inventory then returns false
		while(inStock== true && i<itemList.length) {
			if(inventory.checkForItem(itemList[i])) {
				i++;
			}
			else {
				inStock= false;
			}
		}
		return inStock;
	}
	
	//----------------------------------------------------------------------------------------
	//helper method 2: checking for quantity of item, checking to see if we have enough of that item.
	
	public static boolean checkQuantity(String[]itemList) {
		
		boolean enough = true;
		int stockCount;
		int itemOrdered;
		
		// this loop does not stop in the middle because we need to make sure until the very endo of what is ordered. 
		for(int i = 0; i<itemList.length;i++) {
			
		stockCount = inventory.checkCount(itemList[i]);
		itemOrdered = count(itemList,itemList[i]);
		// boolean remains true if stockCount was larger or equal to itemOrdered
		
		if(stockCount >= itemOrdered) {
			enough = true;
		}
		else {
		// if not then, it becomes false.
			enough = false;
		}
		}
		return enough;
	}
	
	//----------------------------------------------------------------------------------------
	//helper method 3: count how many of specific item we have in order
	
	public static int count(String[]itemList,String item) {
		int count = 0;
		for(int i = 0;i<itemList.length;i++) {
			if(item.equals(itemList[i])) {
				count++;
			}
		}
		return count;
	}
	
	
	//----------------------------------------------------------------------------------------
	//helper method 4: print on out put file what is removed from order when InStorePickUp is created.
	
	public static void printISPUpdate(String fname,String lname,ArrayList<String>remove, PrintWriter printUpdate) {
		
		printUpdate.println("In store pick up order warning: ");
		printUpdate.println("Our sincere apology," + fname + " "+ lname);
		printUpdate.println("Items listed were not available and were removed from order: ");
		for(int i = 0;i <remove.size()-1;i++) {
			
			printUpdate.print(remove.get(i));
			printUpdate.print(",");
			
		}
		
		printUpdate.println(remove.get(remove.size()-1));
		printUpdate.println();
		printUpdate.println("---------------------------------------------------------------");
		printUpdate.println();
	}
	
	//----------------------------------------------------------------------------------------
	//helper method 5: print daily order information to the company activity output file
	
	public static void updateCompanyActivity(ArrayList<Order> orders, PrintWriter activity) {
		activity.println("Daily Activity Update Day " + day + ":");
		activity.println("----------------------------------------------------------------------");
		for(int i = 0; i < orders.size(); i++) {
			activity.println(orders.get(i));
			activity.println("----------------------------------------------------------------------");
		}
	}

	//----------------------------------------------------------------------------------------
	//helper method 6: print notifications for express orders to printUpdate if user wants notifications
	
	public static void notify(ArrayList<Order> orders, PrintWriter printUpdate) {
		
		
		for (int i = 0; i < orders.size(); i++) {
			if (orders.get(i) instanceof ExpressOrder) {
				Order temp = orders.get(i);
				ExpressOrder temp2 = (ExpressOrder)temp;
				boolean choice = temp2.getNotification();
				if (choice == true) {
					printUpdate.println("---------------------------------------------------------------");
					String name = orders.get(i).getFirstName() + " " + orders.get(i).getLastName();
					int track = orders.get(i).getTrackingNumber();
					String status = orders.get(i).getStatus();
					printUpdate.println("Express Order notification for " + name + ":");
					printUpdate.println("Status of Order #" + track + ": " + status);
					printUpdate.println("---------------------------------------------------------------");
				}
			}
		}
	}
	
	//----------------------------------------------------------------------------------------
	//helper method 7: restock Inventory method
	
	public static void restockInventory(String restockRequest) {
		// broke down the string into array of string
		String[]requestedItems = restockRequest.split("&");
		//search the item through the sample, and get the item information
		for(int index = 1;index < requestedItems.length;index++) {
			Item temp = inventory.searchItem(requestedItems[index]);
			Supplier s = temp.getSupplier();
			Item[] restock = s.sendItems(temp);
			
			for(int rs = 0;rs<restock.length;rs++) {
				inventory.recieveItem(restock[rs]);
			}
		}
		// get the supplier of the item and call send item, it will be saved into an array of items
		// transfer that item to inventory by using receiveItem method. 
		
	}
	
	//----------------------------------------------------------------------------------------
	//helper method 8: this checks the inventory situation and update the waitlist so it can be completed as a shipped order. 
	
	public static WaitlistOrder WaitlistOrderUpdate(WaitlistOrder check) {
		
		
			int size = check.getNotInStock().size();
			String[] temp = new String[size];
			//now we are allocating the name of items in ArrayList of waitlist order to array of String
			for(int t = 0;t < temp.length;t++) {
				temp[t] = check.getNotInStock().get(t);
			}
			//this condition checks if items are in the stock or not.
			if(checkOrderedItems(temp)) {
				//search for the item and get the item
				for(int t = 0;t<temp.length;t++) {
					
				Item searched = inventory.searchItem(temp[t]);
				
				Item place = inventory.putItemInOrder(searched);
				//place the item into order 
				check.getOrderLine().add(place);
				check.getNotInStock().remove(t);
				
				}
				check.setStatus("Shipped");
				
				}
		
			else {
				// if item is not in stock and the differnce of current day and orderplaced date was larger than or equal to 14 then it
				// returns empty waitlist order and in the main, 
				if(day - check.getOrderPlacedDate() >= 14) {
					check.setStatus("Auto-Cancel");
				}
			}
		return check;
	}
	
	//----------------------------------------------------------------------------------------
	//helper method 9: print auto cancel message: print out a cancel messege to let user know their waitlist order had been cancelled.
	
	public static void printCancelMessage(WaitlistOrder cancel, PrintWriter printUpdate) {
		
		printUpdate.println("---------------------------------------------------------------");
		printUpdate.println();
		printUpdate.println("Waitlist order status update: ");
		printUpdate.println(cancel.getFirstName() +" "+ cancel.getLastName() + ", your waitlist order had been cancelled.");
		printUpdate.println("---------------------------------------------------------------");
		
	}
	
	//----------------------------------------------------------------------------------------
	//helper method 10: display the first message for user in output file for user
	
	public static void printWelcomeMessage(PrintWriter printUpdate) {
		
		printUpdate.println("This is a notification window for customers: ");
		printUpdate.println("Thank you for choosing our service.");
		printUpdate.println("***************************************************************");
		                    
	}                       
	
	
	//----------------------------------------------------------------------------------------
	//helpder method 11: warningOrderDelivered method
	
	public static void printWarningOrderDelivered(Order order,PrintWriter activity) {
		
		String name = order.getFirstName() + " " + order.getLastName();
		int trackNum = order.getTrackingNumber();
		activity.println("\n*****************************Warning**********************************");				 
		activity.println("\nThis order has been delivered and complete, therefore will be deleted from our system.");
		activity.println("Ordered by "+ name + ", tracking number: "+ trackNum);
		activity.println();
		activity.println("**********************************************************************");
	}                       
	//----------------------------------------------------------------------------------------
	//helper method 12: warningOrderPickUp method: this notifies the company that the in store pick up order
	// has been picked up. 
	public static void printWarningOrderPickedUp(Order order,PrintWriter activity) {
		
		String name = order.getFirstName() + " " + order.getLastName();
		int trackNum = order.getTrackingNumber();
		activity.println("\n**************************** Warning *********************************");				 
		activity.println("\nThis order has been picked up and will be deleted from our system.");
		activity.println("Order by "+ name + ", tracking number: "+ trackNum);
		activity.println();
		activity.println("**********************************************************************");
	}
	//----------------------------------------------------------------------------------------
	//helper method 13: ErrorMessageInvalidTNum method: prompt user that the tracking number they entered is invalid.
	
	public static void ErrorMessageInvalidTNum(PrintWriter printUpdate) {
		printUpdate.println("************************ Warning ******************************");
		printUpdate.println("\nInvalid Tracking number, please check your tracking number and try again");
		printUpdate.println("\n************************ Warning ******************************");
	}
}

