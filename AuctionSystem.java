/**
 * @author Adrianna Ramirez
 * 114313272
 * Recitation 02
 */
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class AuctionSystem implements Serializable {
    /**
     *prompts user for username,
     * presents options/menu to use on the auction table
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Starting...");
        AuctionTable auctionTable = new AuctionTable();
        //AuctionTable auctionTable = null;
        try {
            FileInputStream file = new FileInputStream("auction.obj");
            //chooses path for file its loading from
            //checks if the file auctions.obj exists in the current directory
            //if it does :
            ObjectInputStream inputStream = new ObjectInputStream(file);
            //creates an object from file path, loaded from saved AuctionTable for new auctions/bids
            auctionTable.auctionTable = (HashMap) inputStream.readObject();
            //explicit casting , takes input stream , reads an object

            inputStream.close();
            file.close();
            System.out.println("Loading previous Auction Table...");
        } catch (Exception e) {
            System.out.println("No previous auction table detected.\nCreating new table...");
            //catches exception if there are no previous auction tables, creates new table
        }

        Scanner bestie = new Scanner(System.in);
        System.out.println("Please select a username: ");
        String user = bestie.nextLine(); //String member variable for username
        System.out.println("Menu: \n" +
                "   (D) - Import Data from URL \n" +
                "   (A) - Create a New Auction \n" +
                "   (B) - Bid on an Item \n" +
                "   (I) - Get Info on Auction \n" +
                "   (P) - Print All Auctions \n"+
                "   (R) - Remove Expired Auctions \n" +
                "   (T) - Let Time Pass \n" +
                "   (Q) - Quit");
        //menu choices
        String optionChoice = "";
        while(!optionChoice.contentEquals("Q")){
            System.out.println("Please select an option: ");
            optionChoice = bestie.nextLine();
            switch(optionChoice){
                case "D":
                    try{
                        System.out.println("Please enter a URL: ");
                        String url = bestie.nextLine();
                        auctionTable.buildFromURL(url);
                        //implements build URL from class auction table that takes
                        //auction from given
                        System.out.println("Loading...");
                        System.out.println("Auction data loaded successfully!");
                    } catch (Exception e){
                        System.out.println("Invalid URL.");
                    }
                    break;
                case "A":
                    System.out.println("Creating new Auction as " + user + ".");
                    System.out.println("Please enter an Auction ID: ");
                    String newID = bestie.nextLine();
                    System.out.println("Please enter an Auction time (hours) : ");
                    int newTime = Integer.parseInt(bestie.nextLine());
                    System.out.println("Please enter some Item Info: ");
                    String newInfo = bestie.nextLine();
                    Auction newAuction = new Auction(newID, user, newTime, newInfo);
                    auctionTable.putAuction(newID, newAuction);
                    System.out.println("Auction " + newID + " inserted into table.");
                    break;
                case "B":
                    System.out.println("Please enter an Auction ID: ");
                    newID = bestie.nextLine();
                    try{
                        //check if auction is closed or not
                        //
                        if(auctionTable.getAuction(newID).getTimeRemaining() == 0){
                            System.out.println("Auction " + newID + " is CLOSED");
                            double closedCurrentBid = auctionTable.getAuction(newID).getCurrentBid();
                            System.out.println("    Current Bid: $ " + closedCurrentBid);
                            System.out.println("");
                            System.out.println("You can no longer bid on this item.");
                        } else {
                            System.out.println("Auction " + newID + " is OPEN");
                            double openCurrentBid = auctionTable.getAuction(newID).getCurrentBid();
                            System.out.println("    Current Bid: " + openCurrentBid);
                            System.out.println("What would you like to bid?: ");
                            double newBid = Double.parseDouble(bestie.nextLine());
                            if (newBid > openCurrentBid){
                                auctionTable.getAuction(newID).newBid(auctionTable.getAuction(newID).getBuyerName(), newBid);
                                System.out.println("Bid accepted.");
                            } else{
                                System.out.println("Bid denied.");
                            }
                        }
                    }catch (Exception e){
                        System.out.println("Invalid auction.");
                    }
                    break;
                case "I":
                    try{
                        System.out.println("Please enter an Auction ID");
                        newID = bestie.nextLine();
                        String name = auctionTable.getAuction(newID).getSellerName();
                        String buyer = auctionTable.getAuction(newID).getBuyerName();
                        int time = auctionTable.getAuction(newID).getTimeRemaining();
                        String info = auctionTable.getAuction(newID).getItemInfo();
                        System.out.println("Auction " + newID + ":\n" +
                                "    Seller: " + name + "\n" +
                                "    Buyer: " + buyer + "\n" +
                                "    Time: " + time + "\n" +
                                "    Info: " + info + "\n");

                    }catch (Exception e){
                        System.out.println("Invalid Auction ID.");
                }
                    //format to print single auction from id given from user
                    break;
                case "P":
                    auctionTable.printTable();
                    break;
                case "R":
                    System.out.println("Removing expired auctions...");
                    auctionTable.removeExpiredAuctions();
                    System.out.println("All expired auctions removed.");
                    break;
                case "T":
                    System.out.println("How many hours should pass :");
                    int hours = Integer.parseInt(bestie.nextLine());
                    auctionTable.letTimePass(hours);
                    System.out.println("Time passing... \nAuction times updated.");
                    break;
                case "Q":
                    System.out.println("Writing Auction Table to file...");


                    FileOutputStream file = new FileOutputStream("auction.obj");
                    ObjectOutputStream outStream = new ObjectOutputStream(file);
                    outStream.writeObject(auctionTable.auctionTable);
                    outStream.close();
                    file.close();
                    break;
            }

        }


        System.out.println("Done! \n \nGoodbye.");
        System.exit(0);

    }
}
