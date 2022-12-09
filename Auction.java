/**
 * @author Adrianna Ramirez
 * 114313272
 * Recitation 02
 */
import java.io.Serializable;

//class represents an active auction currently in database
public class Auction implements Serializable{
// member variables:
    int timeRemaining;
    double currentBid;
    String auctionID;
    String sellerName;
    String buyerName;
    String ItemInfo;

    /**
     * accessor method for member variable timeRemaining
     * @return declared int timeRemaining
     */
    public int getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * accessor method for member variable buyerName
     * @return declared string buyerName
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * accessor method for member variable currentBid
     * @return declared double currentBid
     */
    public double getCurrentBid() {
        return currentBid;
    }

    /**
     * accessor method for member variable auctionID
     * @return declared string auctionID
     */
    public String getAuctionID() {
        return auctionID;
    }

    /**
     * accessor method for member variable sellerName
     * @return declared string seller name
     */
    public String getSellerName() {
        return sellerName;
    }

    /**
     * accessor method for member variable Item Info
     * @return declared String item Info
     */
    public String getItemInfo() {
        return ItemInfo;
    }

    /**
     * constructor method for Auction that initializes member variables
     * to zero or null
     */
    public Auction() {
        timeRemaining = 0;
        currentBid = 0;
        auctionID = sellerName = buyerName = ItemInfo = null;
    }

    /**
     * constructor method for Auction with the following parameters
     * @param auctionID
     * @param sellerName
     * @param timeRemaining
     * @param itemInfo
     * assigns values given to auctionId, sellerName, timeRemaining, ItemInfo of class Auction
     */
    public Auction(String auctionID, String sellerName, int timeRemaining, String itemInfo){
        this.auctionID = auctionID;
        this.sellerName = sellerName;
        this.timeRemaining = timeRemaining;
        this.ItemInfo = itemInfo;
    }

    /**
     * decreases time remaining for auction by the given specified amount
     * @param time input/given specified amount
     * Post-conditions : timeRemaining decremented by indicated amount, greater than or equal to 0
     */
    public void decrementTimeRemaining(int time){
        if (time > timeRemaining){
            //if time is greater than the current time remaining :
            timeRemaining = 0; //timeRemaining is set to 0
        }else {
            timeRemaining -= time;
        }
    }

    /**
     * creates new bid on auction, replaces higher bid with current
     * pre-conditions: auction is not closed
     * post-conditions: current bid reflects the largest bid placed on the object
     * @param bidderName
     * @param bidAmt
     * @throws ClosedAuctionException if auction is closed and no more bids can be placed
     */
    public void newBid(String bidderName, double bidAmt) throws ClosedAuctionException{
        if (timeRemaining > 0) { //if there is time remaining left on auction
            if (bidAmt > currentBid){ //if param bid amount is higher than current bid
                currentBid = bidAmt; // sets value of given bid amount  to member variable current Bid
                buyerName = bidderName; // sets name of given bidder name to member variable buyer name
            }
        }else {
            throw new ClosedAuctionException("Auction is closed and no more bids can be placed.");

        }
    }
    // returns

    /**
     * to String method
     * @return  auction in a form of a table
     */
    @Override
    public String toString() {

//        String ananya ="  " + auctionID + " | $   " + currentBid + "  |  "
//                + sellerName + "           |  " + buyerName +
//                "    | " + timeRemaining + "hours | " + ItemInfo;
        String ananya = String.format("  %9s | $%9.2f | %-22.22s|  %-23.23s| %3d hours | %-42.42s",auctionID,
                currentBid,sellerName,buyerName,timeRemaining,ItemInfo);

        return ananya;
    }
}
