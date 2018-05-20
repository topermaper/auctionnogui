package net.topermaper.auctionnogui;


public class Bid {
    protected int id;
    protected int item_id;
    protected int user_id;
    protected float bid;

 
    public Bid() {
    }
 
    public Bid(int id) {
        this.id = id;
    }
 
    public Bid(int item_id, int user_id, float bid) {
    	this.item_id = item_id;
    	this.user_id = user_id;
    	this.bid = bid;
    }
    
    
    public Bid(int id, int item_id, int user_id, float bid) {	
    	this(item_id, user_id , bid);
    	this.id = id;
    }
    
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public int getItemId() {
        return item_id;
    }
 
    public void setItemId(int item_id) {
        this.item_id  = item_id;
    }
    
    public int getUserId() {
        return user_id;
    }
 
    public void setUserId(int user_id) {
        this.user_id  = user_id;
    }
    
    public float getBid() {
        return bid;
    }
 
    public void setBid(float bid) {
        this.bid  = bid;
    }

}
