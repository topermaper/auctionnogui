package net.topermaper.auctionnogui;


public class User {
    protected int id;
    protected String name;
    protected String surname;
    protected String email;

 
    public User() {
    }
 
    public User(int id) {
        this.id = id;
    }
 
    public User(String name) {
        this.name = name;
    }
    
    public User(int id, String name) {
    	this.id = id;
    	this.name = name;
    }

    
    public User(int id, String name,  String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }
    
    public User(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
    
    public User(int id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
     
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name  = name;
    }
    
    public String getSurname() {
        return surname;
    }
 
    public void setSurname(String surname) {
        this.surname  = surname;
    }
    
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email  = email;
    }

}
