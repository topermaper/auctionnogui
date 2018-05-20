package net.topermaper.auctionnogui;

public class BidExtended extends Bid{

	String userName;
	String userSurname;
	String itemDescription;
	
	public BidExtended(int bid_id) {
		super(bid_id);
	}
	
    public BidExtended(int id, int item_id, String description, int user_id, String name, String surname, float bid) {	
    	super(id, item_id, user_id , bid);
    	this.userName = name;
    	this.userSurname = surname;
    	this.itemDescription = description;
    }
    
    public BidExtended(int item_id, String description, int user_id, String name, String surname, float bid) {	
    	super(item_id, user_id , bid);
    	this.userName = name;
    	this.userSurname = surname;
    	this.itemDescription = description;
    }
    
    public String getName() {
    	return userName;
    }
    
    public String getSurname() {
    	return userSurname;
    }
    
    public String getDescription() {
    	return itemDescription;
    }
    
}
