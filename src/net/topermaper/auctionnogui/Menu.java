package net.topermaper.auctionnogui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Menu {
	
	private String menu;
	private String menuTitle;

	private ItemDAO itemDAO;
	private UserDAO userDAO;
	private BidDAO bidDAO;
	
	private String displayInfo;
	
    private ArrayList<Option> options; 
    
    public Menu() {
    	this.menu = "MAIN";
    	this.menuTitle = "MAIN MENU";
    	this.options = new ArrayList<>();
    	this.displayInfo = "";

	    options.add(new Option("1","Items"));
	    options.add(new Option("2","Users"));
	    options.add(new Option("3","Bids"));
	    options.add(new Option("9","Exit"));
    	
    	this.itemDAO = new ItemDAO(
        		Auctionnogui.getjdbcURL(), Auctionnogui.getjdbcUsername(), Auctionnogui.getjdbcPassword());
    	
    	this.userDAO = new UserDAO(
        		Auctionnogui.getjdbcURL(), Auctionnogui.getjdbcUsername(), Auctionnogui.getjdbcPassword());
    	
    	this.bidDAO = new BidDAO(
        		Auctionnogui.getjdbcURL(), Auctionnogui.getjdbcUsername(), Auctionnogui.getjdbcPassword());
    }
    
    private static void cleanScreen() {
    	for(int clear = 0; clear < 100; clear++) {
			System.out.println("") ;
		}
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
	
	private List<BidDetails> getBids() {

		List<BidDetails> bidList = new ArrayList<>();
		
		try {

			bidList = bidDAO.listAllBids();

		} catch (SQLException e){
			e.printStackTrace();
			System.out.print("Can not connect to DB");
		}
		
		return bidList;
		 	 
	}
	
	private BidDetails getWinningBid(int item_id) {

		BidDetails bid = null;
		
		try {

			bid = bidDAO.getWinningBid(item_id);

		 } catch (SQLException e){
			 e.printStackTrace();
			 System.out.print("Can not connect to DB");
		 }
		 
		 return bid;
	}
	
	private void printBidHeader() {
		String headerFormat = "%5s %-30s %-30s %10s";
		System.out.println(String.format(headerFormat, "id","item description", "user name", "bid"));
		System.out.println("");
	}
	
	private void printBid(BidDetails bid) {
		String lineFormat = "%5s %-30s %-30s %10.2f";
		System.out.println(String.format(lineFormat, 
				Integer.toString(bid.getId()),
				bid.getDescription(),
				bid.getName()+" "+bid.getSurname(),
				bid.getBid()
				));
	}
	
	private void printItem(Item item) {
		String lineFormat = "%5s %-35s";
		System.out.println(String.format(lineFormat , Integer.toString(item.getId()),item.getDescription()));
	}

	private void printItemHeader() {
		String headerFormat = "%5s %-35s";
		System.out.println("Item List:");
		System.out.println("");
		System.out.println(String.format(headerFormat, "id","description"));
		System.out.println("");
	}
	

	private void printItems(List<Item> itemList) {

		printItemHeader();

		Iterator<Item> itemItr = itemList.iterator();
		while(itemItr.hasNext()) {
			 printItem(itemItr.next());
			 
		}
		
		System.out.println("");
	}
	
	private void printBids(List<BidDetails> bidList) {

		if ((bidList != null) && !(bidList.isEmpty())) {
			
			printBidHeader();
	
			Iterator<BidDetails> bidItr = bidList.iterator();
			while(bidItr.hasNext()) {
				 
				printBid(bidItr.next());
			}
			
			System.out.println("");

		} else {
			System.out.println("No bids found");
			System.out.println("");
		}
	}
	
	
	private void printUserHeader() {
		String headerFormat = "%5s %-25s %-25s";
		System.out.println("User list:");
		System.out.println("");
		System.out.println(String.format(headerFormat, "id","name","email"));
		System.out.println("");
	}
	
	private void printUser(User user) {
		String lineFormat = "%5s %-25s %-25s";
		System.out.println(String.format(lineFormat, 
				 Integer.toString(user.getId()),
				 user.getName()+" "+user.getSurname(),
				 user.getEmail()
				 ));
	}
	
	private void printUsers(List<User> userList) {

		
		printUserHeader();
		
		Iterator<User> userItr = userList.iterator();
		while(userItr.hasNext()) {
			printUser(userItr.next());
			
		}
		
		System.out.println("");
	}
    
	private void printMenuTitle() {
		String titleFormat = "********** %s *********";
	    System.out.println(String.format(titleFormat, this.menuTitle));
	    System.out.println("");
	    System.out.println("");
	}
	
	private void printOptions() {
		if (!(this.options.isEmpty())) {
			
			Option option;
			String optionsFormat = "%s - %s";

		    System.out.println("Options:");
		    System.out.println("");
		    
		    Iterator<Option> iterator = this.options.iterator();
		    
		    while (iterator.hasNext()) {
		    	option = iterator.next();
		    	System.out.println(String.format(optionsFormat, option.getSelector(), option.getDescription()));
		    }
		    
		    System.out.println("");
		    System.out.println("Your choice: ");
		}
	}
	
	private void displayInfo() {
		switch (this.displayInfo) {
			case "ITEMS":
				this.printItems(getItems());
				break;
			case "USERS":
				this.printUsers(getUsers());
				break;
			case "BIDS":
				this.printBids(getBids());
				break;
		}
	}
	
	private void displayInfo(String displayInfo) {
		this.displayInfo=displayInfo;
		this.displayInfo();
	}
	
	private void goToMenu(String menu) {
	    options.clear();
	
	    this.menu =  menu;
	    
		switch (menu) {
		case "MAIN":
			this.menuTitle = "MAIN MENU";
		    options.add(new Option("1","Items"));
		    options.add(new Option("2","Users"));
		    options.add(new Option("3","Bids"));
		    options.add(new Option("9","Exit"));
		    this.displayInfo="";
		    break;
		
		case "ITEM":
			this.menuTitle = "ITEM MENU";
		    options.add(new Option("1","Add item"));
		    options.add(new Option("2","Delete item"));
		    options.add(new Option("9","Go main"));
		    this.displayInfo="ITEMS";
		    
		    break;
		    
		case "USER":
			this.menuTitle = "USER MENU";
		    options.add(new Option("1","Add user"));
		    options.add(new Option("2","Delete user"));
		    options.add(new Option("9","Go main"));
		    this.displayInfo="USERS";
		    break;

		case "BID":
			this.menuTitle = "BID MENU";
		    options.add(new Option("1","Bid on an item"));
		    options.add(new Option("2","Get winning bid"));
		    options.add(new Option("3","Get item bids"));
		    options.add(new Option("4","Get user bids"));
		    options.add(new Option("5","Delete bid"));
		    options.add(new Option("9","Go main"));
		    this.displayInfo="";
		    break;
		    
		case "ITEM-ADD":
			this.menuTitle = "Add item";
			this.displayInfo="";
		    break;
		    
		case "ITEM-DELETE":
			this.menuTitle = "Delete item";
			this.displayInfo="ITEMS";
			break;
			
		case "USER-ADD":
			this.menuTitle = "Add user";
			this.displayInfo="";
		    break;
		    
		case "USER-DELETE":
			this.menuTitle = "Delete user";
			this.displayInfo="USERS";
			break;
			
		case "BID-ADD":
			this.menuTitle = "Add bid on an item";
			this.displayInfo="ITEMS";
			break;
			
		case "BID-WINNING":
			this.menuTitle = "Winning bid for an item";
			this.displayInfo="ITEMS";
			break;
			
		case "BID-USER":
			this.menuTitle = "User bids";
			this.displayInfo="USERS";
			break;
			
		case "BID-ITEM":
			this.menuTitle = "Item bids";
			this.displayInfo="ITEMS";
			break;
			
		case "BID-DELETE":
			this.menuTitle = "Delete bid";
			this.displayInfo="BIDS";
			break;
	
		}
	}
	
    public void show() {
    	
    	Scanner scanner = new Scanner(System.in);
    	
    	cleanScreen();
    	
	    printMenuTitle();
	    displayInfo();
	    printOptions();
    	
    	switch (this.menu) {
			case "MAIN":
			  
			    switch (scanner.next()) {

		        case "1":
		        	goToMenu("ITEM");
		        	break;

		        case "2":
		        	goToMenu("USER");
		        	break;
		        	
		        case "3":
		        	goToMenu("BID");
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
		    	
			  	switch (scanner.next()) {

			        case "1":
			        	goToMenu("ITEM-ADD");
			        	break;
			        case "2":
			        	goToMenu("ITEM-DELETE");
			        	break;
			        case "9":
			        	goToMenu("MAIN");
			        	break;
			        default:
			        	break;
			  	}
			  	
			  	break;
			  				  	
			case "USER":
		    	
			  	switch (scanner.next()) {

			        case "1":
			        	goToMenu("USER-ADD");
			        	break;
			        case "2":
			        	goToMenu("USER-DELETE");
			        	break;
			        case "9":
			        	goToMenu("MAIN");
			        	break;
			        default:
			        	break;
			  	}
			  	
			  	break;

			case "BID":
		    	
			  	switch (scanner.next()) {

			        case "1":
			        	goToMenu("BID-ADD");
			        	break;
			        case "2":
			        	goToMenu("BID-WINNING");
			        	break;
			        case "3":
			        	goToMenu("BID-ITEM");
			        	break;
			        case "4":
			        	goToMenu("BID-USER");
			        	break;
			        case "5":
			        	goToMenu("BID-DELETE");
			        	break;
			        case "9":
			        	goToMenu("MAIN");
			        	break;
			        default:
			        	break;
			  	}
			  	
			  	break;

			case "ITEM-ADD":

				System.out.println("Description:");
				System.out.println("");
				 
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
				
				goToMenu("ITEM");

				break;
				
			case "ITEM-DELETE":
				
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
				
				goToMenu("ITEM");

				break;
				
			case "USER-ADD":

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
				
				goToMenu("USER");

				break;
				
			case "USER-DELETE":
			    
	        	System.out.println("Id:");
	        	System.out.println("");
    
				try {
			        userDAO.deleteUser(new User(Integer.parseInt(scanner.next())));
					System.out.println("");
					System.out.println("User deleted from the DB");
				} catch (SQLException e){
					 e.printStackTrace();
					 System.out.println("");
					 System.out.println("Can not connect to DB");
				} finally {
					MyUtils.sleep(Auctionnogui.MSG_DELAY_MS);
				}
				
				goToMenu("USER");

				break;
				
				
			case "BID-ADD":

	        	Bid bid = new Bid ();
				
	        	System.out.println("Id:");
	        	System.out.println("");
    
	        	bid.setItemId(Integer.parseInt(scanner.next()));
	        	
	        	System.out.println("");
	        	displayInfo("USERS");
	
	        	System.out.println("Id:");
	        	System.out.println("");
	        	
	        	bid.setUserId(Integer.parseInt(scanner.next()));
	        	
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
				
				goToMenu("BID");

				break;
				
			case "BID-WINNING":

	        	System.out.println("Id:");
	        	System.out.println("");

	        	BidDetails bidDetails = getWinningBid(Integer.parseInt(scanner.next()));
	        	if (bidDetails != null) {
	        		printBid(bidDetails);
	        	} else {
	        		System.out.println("");
	        		System.out.println("No bids found");
	        		System.out.println("");
	        	}
	        	
	        	System.out.println("Press ENTER to continue ...");
	        	try{System.in.read();}
	        	catch(Exception e){}

			  	goToMenu("BID");

				break;
							
			case "BID-USER":
			    
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
				
	        	System.out.println("Press ENTER to continue ...");
	        	try{System.in.read();}
	        	catch(Exception e){}

			  	goToMenu("BID");
		    	
				break;
							
			case "BID-ITEM":

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
				
	        	System.out.println("Press ENTER to continue ...");
	        	try{System.in.read();}
	        	catch(Exception e){}

			  	goToMenu("BID");

				break;
				
				
			case "BID-DELETE":
				
	        	System.out.println("Id:");
	        	System.out.println("");
     
				try {
			        bidDAO.deleteBid(new BidDetails(Integer.parseInt(scanner.next())));
					System.out.println("");
					System.out.println("Bid deleted from the DB");
				} catch (SQLException e){
					 e.printStackTrace();
					 System.out.println("");
					 System.out.println("Can not connect to DB");
				} finally {
					MyUtils.sleep(Auctionnogui.MSG_DELAY_MS);
				}
				
				goToMenu("BID");

				break;
				
				
    	}

    }
    

}
