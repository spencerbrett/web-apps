package edu.ucla.cs.cs144;

import java.lang.String;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Item {

    private int itemId;

    private String name;

    private double currentBid;

    private Double buyPrice = null;

    private double firstBid;

    private int numBids;

    private Date started;

    private Date ends;

    private String description;

    private List<String> categories = new ArrayList<String>();

    private List<Bid> bids = new ArrayList<Bid>();

    private User seller;

    public void setItemId(int id) {
        itemId = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCurrentBid(double currentBid) {
        this.currentBid = currentBid;
    }

    public double getCurrentBid() {
        return currentBid;
    }

    public void setBuyPrice(double price) {
        buyPrice = price;
    }

    public String getBuyPrice() {
        if (buyPrice == null)
            return "";
        else
            return buyPrice.toString();
    }

    public void setFirstBid(double firstBid) {
        this.firstBid = firstBid;
    }

    public double getFirstBid() {
        return firstBid;
    }

    public void setNumBids(int numBids) {
        this.numBids = numBids;
    }

    public int getNumBids() {
        return numBids;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public String getStarted() {
        DateFormat ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ts.format(started);
    }

    public void setEnds(Date ends) {
        this.ends = ends;
    }

    public String getEnds() {
        DateFormat ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ts.format(ends);
    }

    public void setDescription(String desc) {
        description = desc;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void insertCategory(String category) {
        categories.add(category);
    }

    public String getCategory(int index) {
        return (String) categories.get(index);
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public void insertBid(Bid bid) {
        bids.add(bid);
    }

    public Bid getBid(int index) {
        return (Bid) bids.get(index);
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public String printItem() {
        DateFormat ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = "Item ID: " + Integer.toString(itemId) + "\n";
        result += "Name: " + name + "\n";
        for (String category : categories) {
            result += "Category: " + category + "\n";
        }
        result += "Currently: " + Double.toString(currentBid) + "\n";
        if (this.getBuyPrice() != null) {
            result += "Buy_Price: " + Double.toString(buyPrice) + "\n";
        }
        result += "First_Bid: " + Double.toString(firstBid) + "\n";
        result += "Number_of_Bids: " + Integer.toString(numBids) + "\n";
        result += "Started: " + ts.format(started) + "\n";
        result += "Ends: " + ts.format(ends) + "\n";

        for (Bid b : bids) {
            result += "Bid\n";
            result += "\tAmount: " + Double.toString(b.getAmount()) + "\n";
            result += "\tTime: " + b.getPostingTime() + "\n";
            result += "\tUser\n";
            result += "\t\tUser_ID: " + b.getBidder().getUserId() + "\n";
            result += "\t\tRating: "
                    + Integer.toString(b.getBidder().getRating()) + "\n";
            result += "\t\tLocation: " + b.getBidder().getLocation() + "\n";
            result += "\t\tCountry: " + b.getBidder().getCountry() + "\n";
        }

        result += "Seller\n";
        result += "\tUser_ID: " + seller.getUserId() + "\n";
        result += "\tRating: " + Integer.toString(seller.getRating()) + "\n";
        result += "\tLocation: " + seller.getLocation() + "\n";
        result += "\tCountry: " + seller.getCountry() + "\n";

        result += "Description: " + description + "\n";

        return result;
    }
}
