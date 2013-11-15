package edu.ucla.cs.cs144;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Item {
	private int ItemID;
	private int Number_of_Bids;
    private float Currently, First_Bid;
    private String Started, Ends;
    private String Name, Description, SellerID;
    private List<String> CategoryList;
    private List<Bid> Bids;
    private User seller;
    
    public Item(){
        CategoryList = new ArrayList<String>();
        Bids = new ArrayList<Bid>();
    }

    public int getItemID() {
    	return ItemID;
    }
    public void setItemID(int itemID) {
    	ItemID = itemID;
    }
    public List<Bid> getBidList() {
		return Bids;
	}
	public void setBidList(List<Bid> bidList) {
		Bids = bidList;
	}
	public void addToBidList(Bid myBid) {
		Bids.add(myBid);
	}
	public List<String> getCategoryList() {
		return CategoryList;
	}
	public void setCategoryList(List<String> categoryList) {
		CategoryList = categoryList;
	}
	public void addToCatList(String myCategory) {
		CategoryList.add(myCategory);
	}
	public float getCurrently() {
		return Currently;
	}
	public void setCurrently(float currently) {
		Currently = currently;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getEnds() {
		return Ends;
	}
	public void setEnds(String ends) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date endsDate = null;
        try {
            endsDate = sdf.parse(ends);
        } catch (java.text.ParseException e) {
            System.err.println(e);
        }
		SimpleDateFormat newDateFormat = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
        String formattedEnds = newDateFormat.format(endsDate);
		Ends = formattedEnds;
	}
	public float getFirst_Bid() {
		return First_Bid;
	}
	public void setFirst_Bid(float first_Bid) {
		First_Bid = first_Bid;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getNumber_of_Bids() {
		return Number_of_Bids;
	}
	public void setNumber_of_Bids(int number_of_Bids) {
		Number_of_Bids = number_of_Bids;
	}
	public User getSeller() {
		return seller;
	}
	public void setSeller(User seller) {
		this.seller = seller;
	}
	public String getSellerID() {
		return SellerID;
	}
	public void setSellerID(String sellerID) {
		SellerID = sellerID;
	}
	public String getStarted() {
		return Started;
	}
	public void setStarted(String started) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date startedDate = null;
        try {
            startedDate = sdf.parse(started);
        } catch (java.text.ParseException e) {
            System.err.println(e);
        }
		SimpleDateFormat newDateFormat = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
        String formattedStarted = newDateFormat.format(startedDate);
		Started = formattedStarted;
	}
    
    
}
