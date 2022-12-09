/**
 * @author Adrianna Ramirez
 * 114313272
 * Recitation 02
 */

import java.util.HashMap;
import java.util.*;
import big.data.*;
import java.io.Serializable;
//class opens auctions and stores it into a hash table
public class AuctionTable implements Serializable {
    static HashMap<String, Auction> auctionTable = new HashMap<>();
    //initializes new hash map

    /**
     * constructor method for auction table
     * sets auctionTable to null
     */
    public AuctionTable(){
        auctionTable = null;
    }

    /**
     * constructor method for auction table with parameter:
     * @param newTable hashmap
     * assigns values of new table to previously declared auction table
     */
    public AuctionTable(HashMap<String, Auction> newTable) {
    //initialize hash map
        auctionTable = newTable;
   }

    /**
     *uses big data library to construct auction table from a remote data source
     * pre-conditions : url represents data source that can be connected using big data library, proper syntax
     * @param URL String represent the URL to the remote data source
     * @return auction table constructed from the remote data source
     * @throws IllegalArgumentException if URL does not represent valid datasource
     */

    public static AuctionTable buildFromURL(String URL) throws IllegalArgumentException{
        //AuctionTable AuctionDataTable = new AuctionTable();
        HashMap<String, Auction> AuctionDataTable = new HashMap<>();
        //new hashmap
        try{
            DataSource ds = DataSource.connectXML(URL).load();
            //connects and loads data source from given URL
            String[] id = ds.fetchStringArray("listing/auction_info/id_num");
            String[] bid = ds.fetchStringArray("listing/auction_info/current_bid");
            String[] seller = ds.fetchStringArray("listing/seller_info/seller_name");
            String[] buyer = ds.fetchStringArray("listing/auction_info/high_bidder/bidder_name");
            String[] time = ds.fetchStringArray("listing/auction_info/time_left");
            String[] itemInfo = ds.fetchStringArray("listing/item_info/description");

            for(int i =0; i < id.length; i++){


                String newBid = bid[i].replaceAll("[$,]","");



                Auction newAuction = new Auction();
                newAuction.auctionID = id[i];
                newAuction.currentBid = Double.parseDouble(newBid);
                newAuction.sellerName = seller[i];
                newAuction.buyerName = buyer[i];

                if (time[i].contains(",")){
                    int days = Integer.parseInt(time[i].split(", ")[0].substring(0, 2).trim());
                    int hours = Integer.parseInt(time[i].split(", ")[1].substring(0, 2).trim());
                    hours += (days * 24);

                    newAuction.timeRemaining = hours;
                } else if(time[i].contains("days")) {
                    int days = Integer.parseInt(time[i].substring(0, 2).trim());
                    int hours = days * 24;

                    newAuction.timeRemaining = hours;
                }
                else {
                    int hours = Integer.parseInt(time[i].substring(0, 2).trim());
                    newAuction.timeRemaining = hours;
                }
                newAuction.ItemInfo = String.format("%42.42s",itemInfo[i]);
                AuctionDataTable.put(newAuction.auctionID, newAuction);
                //puts each auction into an auction table
            }
        }catch (Exception e) {
            throw new IllegalArgumentException("Invalid argument");
        }
        AuctionTable AuctionTableReturn = new AuctionTable(AuctionDataTable);
//        for (Auction auction: AuctionTableData.values()) {
//            System.out.println(auction.auctionID);
//            auctionTable.put(auction.auctionID, auction);
//        }
        return AuctionTableReturn;
    }

    /**
     * manually posts auction and adds it to table
     * @param auctionID unique key for object
     * @param auction auction to insert into the table with the corresponding auctionID
     * @throws IllegalArgumentException if given auctionID is already stored in the table
     */
    public void putAuction(String auctionID, Auction auction) throws IllegalArgumentException{
        if (auctionTable.containsKey(auctionID)){
            throw new IllegalArgumentException("Given auctionID already exists.");
        }else {
            auctionTable.put(auctionID, auction);
        }
    }

    /**
     * gets information of an auction that contains given ID as key
     * @param auctionID unique key for object
     * @return auction object with the given key, null otherwise
     */
    public Auction getAuction(String auctionID){

        return auctionTable.get(auctionID);
    }

    /**
     * simulates the passing of time, decrease the time remaining of all Auction objects by the amount specified
     * value cannot go below 0
     * post-conditions: all auctions in the table have their timeRemaining timer decreased, original value is less than the decreased value
     * set value to 0
     * @param numHours number of hours to decrease the timeRemaining value by
     * @throws IllegalArgumentException if given number of hours is negative
     */
    public void letTimePass(int numHours) throws IllegalArgumentException{
        if (numHours < 0){
            throw new  IllegalArgumentException("Number of hours cannot me negative.");
        } else {
            for(Map.Entry<String, Auction> melissa: auctionTable.entrySet()){
                Auction jack = melissa.getValue();
                jack.decrementTimeRemaining(numHours);
            }
        }
    }

    /**
     * iterates over all Auction objects in the table and removes them if they are expired
     * expiration is determined by the time remaining
     * post-conditions: only open auction remain in the table
     */
    public void removeExpiredAuctions(){
//        for(Map.Entry<String, Auction> melissa: auctionTable.entrySet()){
//            Auction jack = melissa.getValue();
//            String andre = melissa.getKey();
//            if (jack.getTimeRemaining() == 0){
//                auctionTable.remove(andre);
//            }
//        }
        String jack = "";
        for(String andre: auctionTable.keySet()) {
            if (auctionTable.get(andre).getTimeRemaining() == 0)
                    jack += andre + " ";
        }
        String[] bonquiqui = jack.split(" ");
        for (String bon: bonquiqui)
            auctionTable.remove(bon);
    }

    /**
     * prints the auctionTable in tabular form
     */
    public void printTable(){
        System.out.println(" Auction ID |      Bid   |        Seller         " +
                "|          Buyer          |    Time   |  Item Info \n" +
                "=======================================================================" +
                "============================================================");
        for(Map.Entry<String, Auction> melissa: auctionTable.entrySet()){
            Auction jack = melissa.getValue();
//            String andre = melissa.getKey();
            System.out.println(jack.toString());
        }
    }
}
