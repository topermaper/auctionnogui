package net.topermaper.auctionnogui;


public class Auctionnogui {
	
	private static final String jdbcURL = "jdbc:mysql://18.130.8.148:3306/auction";
	private static final String jdbcUsername = "auction";
	private static final String jdbcPassword = "auction";
	protected static final int MSG_DELAY_MS = 1000;
	
	private static Menu menu;

	protected static String getjdbcURL() {
		return jdbcURL;
	}
	
	protected static String getjdbcUsername() {
		return jdbcUsername;
	}
	
	protected static String getjdbcPassword() {
		return jdbcPassword;
	}

	
	public static void main(String[] args) {
		menu = new Menu();
		
	    while (true) {
		    menu.show();

	    }
	    
	}
}
