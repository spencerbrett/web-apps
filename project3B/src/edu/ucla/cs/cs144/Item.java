package edu.ucla.cs.cs144;

import java.util.Date;
import java.util.List;

public class Item {
	private int ItemID;
	private int Number_of_Bids;
    private float Currently, First_Bid;
    private Date Started, Ends;
    private String Name, Description, SellerID;
    private List<Category> CategoryList;
    private List<Bid> BidList;
    private User seller;
	
    public int getItemID() {
    	return ItemID;
    }
    public void setItemID(int itemID) {
    	ItemID = itemID;
    }
    public List<Bid> getBidList() {
		return BidList;
	}
	public void setBidList(List<Bid> bidList) {
		BidList = bidList;
	}
	public void addToBidList(Bid myBid) {
		BidList.add(myBid);
	}
	public List<Category> getCategoryList() {
		return CategoryList;
	}
	public void setCategoryList(List<Category> categoryList) {
		CategoryList = categoryList;
	}
	public void addToCatList(Category myCategory) {
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
	public Date getEnds() {
		return Ends;
	}
	public void setEnds(Date ends) {
		Ends = ends;
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
	public Date getStarted() {
		return Started;
	}
	public void setStarted(Date started) {
		Started = started;
	}
    
    
}
