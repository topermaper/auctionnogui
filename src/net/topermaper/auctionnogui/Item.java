package net.topermaper.auctionnogui;


public class Item {
    protected int id;
    protected String description;

 
    public Item() {
    }
 
    public Item(int id) {
        this.id = id;
    }
 
    public Item(String description) {
        this.description = description;
    }
    
    public Item(int id, String description) {
        this.id = id;
        this.description = description;
    }
     
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getDescription() {
        return description;
    }
 
    public void setDescription(String description) {
        this.description  = description;
    }

}
