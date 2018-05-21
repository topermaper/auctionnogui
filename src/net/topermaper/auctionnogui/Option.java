package net.topermaper.auctionnogui;

public class Option {

	private String selector;
	private String description;

	Option(String selector, String description) {
		this.selector = selector;
		this.description = description;
	}
	
	public String getSelector() {
		return selector;
	}
	public String getDescription() {
		return description;
	}

}
