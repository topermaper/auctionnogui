package net.topermaper.auctionnogui;

public final class MyUtils {
	
	public static final void sleep (int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}