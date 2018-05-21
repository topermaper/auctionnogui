package net.topermaper.auctionnogui;

 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 

public class BidDAO implements BidTracker{
    private String jdbcURL;
    private String jdbcBidname;
    private String jdbcPassword;
    private Connection jdbcConnection;
    
    public BidDAO(String jdbcURL, String jdbcBidname, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcBidname = jdbcBidname;
        this.jdbcPassword = jdbcPassword;
    }
     
    private void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(
                                        jdbcURL, jdbcBidname, jdbcPassword);
        }
    }
     
    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }
     
    public boolean addBid(Bid Bid) throws SQLException {
        String sql = "INSERT INTO tBid (item_id, user_id, bid) VALUES (?,?,?)";
        connect();
        
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, Bid.getItemId());
        statement.setInt(2, Bid.getUserId());
        statement.setFloat(3, Bid.getBid());
         
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }
     
    
    public List <BidDetails> listAllBids() throws SQLException {
    	List <BidDetails> bidList = new ArrayList<>();
        String sql = "SELECT u.user_id, u.name, u.surname, i.item_id, i.description, b.bid_id, b.bid FROM tUser u, tItem i, tBid b WHERE u.user_id = b.user_id and i.item_id = b.item_id";

        connect();
        
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);

        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {

        	BidDetails bidDetails = new BidDetails(
            		resultSet.getInt("bid_id"),
            		resultSet.getInt("item_id"),
            		resultSet.getString("description"),
            		resultSet.getInt("user_id"),
            		resultSet.getString("name"),
            		resultSet.getString("surname"),
            		resultSet.getFloat("bid")
            		);

            bidList.add(bidDetails);
        }
         
        resultSet.close();
        statement.close();
         
        disconnect();
         
        return bidList;
    }
    
     
    public boolean deleteBid(BidDetails bid) throws SQLException {
        String sql = "DELETE FROM tBid where bid_id = ?";
         
        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, bid.getId());
         
        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;     
    }
     
    public List<BidDetails> getUserBids(int user_id) throws SQLException {

        List<BidDetails> bidList = new ArrayList<>();

        String sql = "SELECT b.bid_id, u.user_id, u.name, u.surname, i.item_id,i.description,b.bid_id, b.bid "
        		+ "FROM tUser u, tItem i, tBid b "
        		+ "WHERE b.user_id = ? AND u.user_id = b.user_id AND i.item_id = b.item_id";

        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, user_id);
         
        ResultSet resultSet = statement.executeQuery();
        
        while (resultSet.next()) {

            BidDetails bid = new BidDetails(
            		resultSet.getInt("bid_id"),
            		resultSet.getInt("item_id"),
            		resultSet.getString("description"),
            		resultSet.getInt("user_id"),
            		resultSet.getString("name"),
            		resultSet.getString("surname"),
            		resultSet.getFloat("bid"));

            bidList.add(bid);
        }
         
        resultSet.close();
        statement.close();
         
        return bidList;
    }
    
    public List<BidDetails> getItemBids(int item_id) throws SQLException {

        List<BidDetails> bidList = new ArrayList<>();

        String sql = "SELECT b.bid_id, u.user_id, u.name, u.surname, i.item_id,i.description,b.bid_id, b.bid "
        		+ "FROM tUser u, tItem i, tBid b "
        		+ "WHERE b.item_id = ? AND u.user_id = b.user_id AND i.item_id = b.item_id";
   

        connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, item_id);
         
        ResultSet resultSet = statement.executeQuery();
        
        while (resultSet.next()) {

            BidDetails bid = new BidDetails(
            		resultSet.getInt("bid_id"),
            		resultSet.getInt("item_id"),
            		resultSet.getString("description"),
            		resultSet.getInt("user_id"),
            		resultSet.getString("name"),
            		resultSet.getString("surname"),
            		resultSet.getFloat("bid"));

            bidList.add(bid);

        }
         
        resultSet.close();
        statement.close();
         
        return bidList;
    }
    
    public BidDetails getWinningBid(int item_id) throws SQLException {

    	BidDetails bid = null;
    	BidDetails maxBid = null;
        List<BidDetails> bidList = getItemBids(item_id);

    	Iterator<BidDetails> bidItr = bidList.iterator();

		while(bidItr.hasNext()) {

			 bid = bidItr.next();
			 
			 if ((maxBid == null) || (bid.getBid() > maxBid.getBid())) {
				 maxBid = bid;
			 }
		}

        return maxBid;
    }

}
    
    
    
    
