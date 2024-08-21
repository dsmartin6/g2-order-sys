# CMS 270 - G2 Ordering System

## Group Members
Jenny Goldsher, Deandra Martin, Hiroki Sato, Miriam Scheinblum

###
Directions for users who use this system:

A. How to load inventory:
   
   1.Go to Inventory.txt
   2.Type in the supplier's name for the system to instantiate a supplier for the system
     so that system can contact that supplier when restock is needed.
     
   3.Move on to the next line and type in information for the item with which you would like to load the inventory
   	 Format: (Numbers of stock of the item),(item name),(stock No./ID),(item Price)
   	 
   4. If you want to have multiple items in the inventory, move to down to the next line and repeat 3!
   	
   So, after you finish typing in your Inventory.txt file should look like this:
   
Pandemic
5,Toilet Paper,1,4.00
5,Toothbrush,2,1.00
5,Face Mask,3,3.50
   		
   		
 B. How customers order in the system:
 
 	1. Go to UserInput.txt
 	2. Type in what activity you would like to do with the system, track your order or make a order?
 	   Type in T for tracking your order, and MO for making an order.
 	   Move down to the next line
 	   
 	3. Format for after determining activity should be:
 		
 		a. if you had typed in T:
 		
 			(your tracking number: will be non-zero integer)
 			
 		b. if you had typed in MO:
 			
 			For shipped order:
 			SO,(first name),(last name),(ShippingCompnay),(waitlist preference of user) 
 			
 			For express order:
 			EXPO,(first name),(last name),(ShippingCompany),(Notification preference)(preference of user) 
 			
 			For in store pick up:
 			ISPU,(first name),(last name),(location) 
 	
 		*waitlist preference should be typed as either Merge or Split
 		*notification preference should be typed as true or false
 		
 		after everything is set move down to next line
 		
 	4. 	Type in items you desire
 		Format for selecting items should be:
 		
 		(item name),(item name),(item name)
 		
 		**if you want to order multiples of an item, please, put the name of the item twice
 		
 		
 		For example your UserInput.txt formatting would look like this:
 		
MO
EXPO,Jordan,Fox,FexEx,false,Merge
Compact Desk,Headphones
T
2
MO
ISPU,Larry,Hilfigger,Maitland
Laptop,Pen
MO
SO,Teddy,Rose,FedEx,Split
Toothbrush,Wallet,Paper Towel
 		
   		
   
