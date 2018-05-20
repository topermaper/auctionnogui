package net.topermaper.auctionnogui;

import java.sql.SQLException;
import java.util.List;

public interface BidTracker {
	
	public boolean addBid (Bid bid) throws SQLException;
	
	public Bid getWinningBid (int itemId) throws SQLException;
	
	public List<BidExtended> getItemBids (int itemId) throws SQLException;
	
	public List<BidExtended> getUserBids (int userId) throws SQLException;;
	

}
