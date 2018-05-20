package net.topermaper.auctionnogui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Menu {
	
	private String menu;
	//private List<Item> itemList;
	//private List<User> userList;
	private ItemDAO itemDAO;
	private UserDAO userDAO;
	private BidDAO bidDAO;
    
    private static void cleanScreen() {
    	for(int clear = 0; clear < 100; clear++) {
			System.out.println("") ;
		}
    }
    
    public Menu() {
    	this.menu = "MAIN";
    	
    	itemDAO = new ItemDAO(
        		Auctionnogui.getjdbcURL(), Auctionnogui.getjdbcUsername(), Auctionnogui.getjdbcPassword());
    	
    	userDAO = new UserDAO(
        		Auctionnogui.getjdbcURL(), Auctionnogui.getjdbcUsername(), Auctionnogui.getjdbcPassword());
    	
    	bidDAO = new BidDAO(
        		Auctionnogui.getjdbcURL(), Auctionnogui.getjdbcUsername(), Auctionnogui.getjdbcPassword());
    }
    
	private List<Item> getItems() {

		List<Item> itemList = new ArrayList<>();
		
		try {

			itemList = itemDAO.listAllItems();

		} catch (SQLException e){
			e.printStackTrace();
			System.out.print("Can not connect to DB");
		}
		 
		return itemList;
	}
	
	private List<User> getUsers() {

		List<User> userList = new ArrayList<User>();
		try {

			userList = userDAO.listAllUsers();

		} catch (SQLException e){
			e.printStackTrace();
			System.out.print("Can not connect to DB");
		}
		 
		return userList;
	}
	
	private List<BidExtended> getBids() {

		List<BidExtended> bidList = new ArrayList<>();
		
		try {

			bidList = bidDAO.listAllBids();

		} catch (SQLException e){
			e.printStackTrace();
			System.out.print("Can not connect to DB");
		}
		
		return bidList;
		 	 
	}
	
	private BidExtended getWinningBid(int item_id) {

		BidExtended bid = null;
		
		try {

			bid = bidDAO.getWinningBid(item_id);

		 } catch (SQLException e){
			 e.printStackTrace();
			 System.out.print("Can not connect to DB");
		 }
		 
		 return bid;
	}
	
	private void printBidHeader() {
		String headerFormat = "%5s %-25s %-20s %20s";
		System.out.println(String.format(headerFormat, "id","description", "name", "bid"));
		System.out.println("");
	}
	
	private void printBid(BidExtended bid) {
		String lineFormat = "%5s %-25s %-20s %20.2f";
		System.out.println(String.format(lineFormat, 
				Integer.toString(bid.getId()),
				bid.getDescription(),
				bid.getName()+" "+bid.getSurname(),
				bid.getBid()
				));
	}
	
	private void printBid(BidExtended bid, boolean printHeader) {
		printBidHeader();
		printBid(bid);
	}

	private void printItem(Item item) {
		String lineFormat = "%5s %-35s";
		System.out.println(String.format(lineFormat , Integer.toString(item.getId()),item.getDescription()));
	}

	private void printItemHeader() {
		String headerFormat = "%5s %-35s";;
		System.out.println(String.format(headerFormat, "id","description"));
		System.out.println("");
	}
	

	private void printItems(List<Item> itemList) {

		printItemHeader();

		Iterator<Item> itemItr = itemList.iterator();
		while(itemItr.hasNext()) {
			 printItem(itemItr.next());
			 
		}
	}
	
	private void printBids(List<BidExtended> bidList) {

		printBidHeader();

		Iterator<BidExtended> bidItr = bidList.iterator();
		while(bidItr.hasNext()) {
			 
			printBid(bidItr.next());
		}
	}
	
	
	private void printUsers(List<User> userList) {

		String lineFormat = "%5s %-25s %-25s";
		
		User user;

		//Print header
		System.out.println(String.format(lineFormat, "id","name","email"));
		System.out.println("");

		//Print items
		Iterator<User> userItr = userList.iterator();
		while(userItr.hasNext()) {
			 user = userItr.next();
			 System.out.println(String.format(lineFormat, 
					 Integer.toString(user.getId()),
					 user.getName()+" "+user.getSurname(),
					 user.getEmail()
					 ));
		}
	}
    
    public void show() {
    	
    	Scanner scanner = new Scanner(System.in);
    	
    	cleanScreen();
    	
    	switch (this.menu) {
			case "MAIN":
			    System.out.println("********** MAIN MENU *********");
			    System.out.println("");
			    System.out.println("");
			    System.out.println("Options:");
			    System.out.println("");
			    System.out.println("1 - Items");
			    System.out.println("2 - Users");
			    System.out.println("3 - Bids");
			    System.out.println("9 - Exit");
			    System.out.println("");
			    System.out.println("Your choice: ");
			    
			    switch (scanner.next()) {

		        case "1":
		        	this.menu="ITEM";
		        	break;

		        case "2":
		        	this.menu="USER";
		        	break;
		        	
		        case "3":
		        	this.menu="BID";
		        	break;

		        case "9":        	
		        	System.out.println(String.format("Terminating program ..."));
		        	MyUtils.sleep(Auctionnogui.MSG_DELAY_MS);
		        	cleanScreen();
		        	System.exit(0);
		        	break;

		        default:
		        	break;
			    }

			    break;
			    
			case "ITEM":
			    System.out.println("********** ITEM MENU *********");
			    System.out.println("");
			    System.out.println("Item list:");
			    System.out.println("");
	        	getItems();
	        	
	        	printItems(getItems());
				
				System.out.println("");
				System.out.println("Options");
				System.out.println("");
			    System.out.println("1 - Add item");
			    System.out.println("2 - Delete item");
			    System.out.println("3 - Update item");
			    System.out.println("9 - Go main");
			    System.out.println("");
			    System.out.println("Your choice: ");
		    	
			  	switch (scanner.next()) {

			        case "1":
			        	this.menu="ITEM-ADD";
			        	break;
			        case "2":
			        	this.menu="ITEM-DELETE";
			        	break;
			        case "3":
			        	break;
			        case "9":
			        	this.menu="MAIN";
			        	break;
			        default:
			        	break;
			  	}
			  	
			  	break;
			  	
			  	
			case "USER":
			    System.out.println("********** USER MENU *********");
			    System.out.println("");
			    System.out.println("User list:");
			    System.out.println("");
	        	
	        	printUsers(getUsers());
				
				System.out.println("");
				System.out.println("Options");
				System.out.println("");
			    System.out.println("1 - Add user");
			    System.out.println("2 - Delete user");
			    System.out.println("3 - Update user");
			    System.out.println("9 - Go main");
			    System.out.println("");
			    System.out.println("Your choice: ");
		    	
			  	switch (scanner.next()) {

			        case "1":
			        	this.menu="USER-ADD";
			        	break;
			        case "2":
			        	this.menu="USER-DELETE";
			        	break;
			        case "3":
			        	break;
			        case "9":
			        	this.menu="MAIN";
			        	break;
			        default:
			        	break;
			  	}
			  	
			  	break;

			case "BID":
			    System.out.println("********** BID MENU *********");
			    System.out.println("");

				System.out.println("Options:");
				System.out.println("");
			    System.out.println("1 - Bid on an item");
			    System.out.println("2 - Get winning bid");
			    System.out.println("3 - Get item bids");
			    System.out.println("4 - Get user bids");
			    System.out.println("5 - Delete bid");
			    System.out.println("9 - Go main");
			    System.out.println("");
			    System.out.println("Your choice: ");
		    	
			  	switch (scanner.next()) {

			        case "1":
			        	this.menu="BID-ADD";
			        	break;
			        case "2":
			        	this.menu="BID-WINNING";
			        	break;
			        case "3":
			        	this.menu="BID-ITEM";
			        	break;
			        case "4":
			        	this.menu="BID-USER";
			        	break;
			        case "5":
			        	this.menu="BID-DELETE";
			        	break;
			        case "9":
			        	this.menu="MAIN";
			        	break;
			        default:
			        	break;
			  	}
			  	
			  	break;

			case "ITEM-ADD":
				 System.out.println("********** Add Item *********");
				 System.out.println("");
				 System.out.println("");
				 System.out.println("Description:");
				 System.out.println("");
				 
		    	// get the input as a String
			    String description = scanner.nextLine();
      
				try {
					itemDAO.insertItem(new Item(description));
					System.out.println("");
					System.out.println("Item added to the DB");
				} catch (SQLException e){
					 e.printStackTrace();
					 System.out.println("");
					 System.out.println("Can not connect to DB");
				} finally {
					MyUtils.sleep(Auctionnogui.MSG_DELAY_MS);
				}
				
				this.menu = "ITEM";

				break;
				
			case "ITEM-DELETE":
			    System.out.println("********** Delete Item *********");
			    System.out.println("");
			    System.out.println("");
			    System.out.println("Item list:");
			    System.out.println("");
	        	
	        	printItems(getItems());
				
	        	System.out.println("");
	        	System.out.println("Id:");
	        	System.out.println("");
     
				try {
			        itemDAO.deleteItem(new Item(Integer.parseInt(scanner.next())));
					System.out.println("");
					System.out.println("Item deleted from the DB");
				} catch (SQLException e){
					 e.printStackTrace();
					 System.out.println("");
					 System.out.println("Can not connect to DB");
				} finally {
					MyUtils.sleep(Auctionnogui.MSG_DELAY_MS);
				}
				
				this.menu = "ITEM";

				break;
				
			case "USER-ADD":
				System.out.println("********** Add User *********");
				System.out.println("");

				System.out.println("Name:");
				System.out.println("");
			    String name = scanner.nextLine();
			    System.out.println("");
			    
				System.out.println("Surname:");
				System.out.println("");
			    String surname = scanner.nextLine();
			    System.out.println("");
			    
				System.out.println("Email:");
				System.out.println("");
			    String email = scanner.nextLine();
     
				try {
					userDAO.insertUser(new User(name,surname,email));
					System.out.println("");
					System.out.println("User added to the DB");
				} catch (SQLException e){
					 e.printStackTrace();
					 System.out.println("");
					 System.out.println("Can not connect to DB");
				} finally {
					MyUtils.sleep(Auctionnogui.MSG_DELAY_MS);
				}
				
				this.menu = "USER";

				break;
				
			case "USER-DELETE":
			    System.out.println("********** Delete User *********");
			    System.out.println("");
			    System.out.println("User list:");
			    System.out.println("");
	        	
	        	printUsers(getUsers());
				
	        	System.out.println("");
	        	System.out.println("Id:");
	        	System.out.println("");
    
				try {
			        itemDAO.deleteItem(new Item(Integer.parseInt(scanner.next())));
					System.out.println("");
					System.out.println("Item deleted from the DB");
				} catch (SQLException e){
					 e.printStackTrace();
					 System.out.println("");
					 System.out.println("Can not connect to DB");
				} finally {
					MyUtils.sleep(Auctionnogui.MSG_DELAY_MS);
				}
				
				this.menu = "USER";

				break;
				
				
			case "BID-ADD":
			    System.out.println("********** Add bid on an item *********");
			    System.out.println("");
			    System.out.println("User list:");
			    System.out.println("");
	        	
	        	printUsers(getUsers());
				
	        	System.out.println("");
	        	System.out.println("Id:");
	        	System.out.println("");
    
	        	Bid bid = new Bid ();
	        	bid.setUserId(Integer.parseInt(scanner.next()));
	        	
	        	System.out.println("");
			    System.out.println("Item list:");
			    System.out.println("");
			    
	        	printItems(getItems());

	        	System.out.println("");
	        	System.out.println("Id:");
	        	System.out.println("");
	        	
	        	bid.setItemId(Integer.parseInt(scanner.next()));
	        	
	        	System.out.println("");
	        	System.out.println("Bid:");
	        	System.out.println("");
	        	
	        	bid.setBid(Float.parseFloat(scanner.next()));
	        		
				try {
			        bidDAO.addBid(bid);
					System.out.println("");
					System.out.println("Bid added to the DB");
				} catch (SQLException e){
					 e.printStackTrace();
					 System.out.println("");
					 System.out.println("Can not connect to DB");
				} finally {
					MyUtils.sleep(Auctionnogui.MSG_DELAY_MS);
				}
				
				this.menu = "BID";

				break;
				
			case "BID-WINNING":
			    System.out.println("********** Winning bid for an item *********");
			    System.out.println("");
			    System.out.println("Item list:");
			    System.out.println("");
	        	
	        	printItems(getItems());
				
	        	System.out.println("");
	        	System.out.println("Id:");
	        	System.out.println("");

	        	printBid(getWinningBid(Integer.parseInt(scanner.next())),true);
	        	
				System.out.println("");
				System.out.println("Options:");
				System.out.println("");
	        	System.out.println("1 - Ok, go back to bid menu");
			    System.out.println("9 - Go main");
			    System.out.println("");
			    System.out.println("Your choice: ");
		    	
			  	switch (scanner.next()) {

			        case "1":
			        	this.menu="BID";
			        	break;
			        case "9":
			        	this.menu="MAIN";
			        	break;
			        default:
			        	break;
			  	}

				break;
				
				
				
			case "BID-USER":
			    System.out.println("********** User bids *********");
			    System.out.println("");
			    System.out.println("User list:");
			    System.out.println("");
	        	
	        	printUsers(getUsers());
				
	        	System.out.println("");
	        	System.out.println("Id:");
	        	System.out.println("");
				
				try {					
					printBids(bidDAO.getUserBids(Integer.parseInt(scanner.next())));
				} catch (SQLException e){
					 e.printStackTrace();
					 System.out.println("");
					 System.out.println("Can not connect to DB");
				} finally {
					MyUtils.sleep(Auctionnogui.MSG_DELAY_MS);
				}
				
				System.out.println("");
				System.out.println("Options");
				System.out.println("");
	        	System.out.println("1 - Ok, go back to bid menu");
			    System.out.println("9 - Go main");
			    System.out.println("");
			    System.out.println("Your choice: ");
		    	
			  	switch (scanner.next()) {

			        case "1":
			        	this.menu="BID";
			        	break;
			        case "9":
			        	this.menu="MAIN";
			        	break;
			        default:
			        	break;
			  	}

				break;
				
				
			case "BID-ITEM":
			    System.out.println("********** Item bids *********");
			    System.out.println("");
			    System.out.println("Item list:");
			    System.out.println("");
	        	
	        	printItems( getItems());
				
	        	System.out.println("");
	        	System.out.println("Id:");
	        	System.out.println("");
				
				try {					
					printBids(bidDAO.getItemBids(Integer.parseInt(scanner.next())));
				} catch (SQLException e){
					 e.printStackTrace();
					 System.out.println("");
					 System.out.println("Can not connect to DB");
				} finally {
					MyUtils.sleep(Auctionnogui.MSG_DELAY_MS);
				}
				
				System.out.println("");
				System.out.println("Options:");
				System.out.println("");
	        	System.out.println("1 - Ok, go back to bid menu");
			    System.out.println("9 - Go main");
			    System.out.println("");
			    System.out.println("Your choice: ");
		    	
			  	switch (scanner.next()) {

			        case "1":
			        	this.menu="BID";
			        	break;
			        case "9":
			        	this.menu="MAIN";
			        	break;
			        default:
			        	break;
			  	}

				break;
				
				
			case "BID-DELETE":
			    System.out.println("********** Delete Bid *********");
			    System.out.println("");
			    System.out.println("");
			    System.out.println("Bid list:");
			    System.out.println("");
	        	
	        	printBids(getBids());
			
	        	System.out.println("");
	        	System.out.println("Id:");
	        	System.out.println("");
     
				try {
			        bidDAO.deleteBid(new BidExtended(Integer.parseInt(scanner.next())));
					System.out.println("");
					System.out.println("Bid deleted from the DB");
				} catch (SQLException e){
					 e.printStackTrace();
					 System.out.println("");
					 System.out.println("Can not connect to DB");
				} finally {
					MyUtils.sleep(Auctionnogui.MSG_DELAY_MS);
				}
				
				this.menu = "BID";

				break;
				
				
    	}

    }
    

}
