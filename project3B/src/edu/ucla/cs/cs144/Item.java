package edu.ucla.cs.cs144;

import java.util.Date;
import java.util.List;

public class Item {
	private int ItemID;
	private int Number_of_Bids;
    private float Currently, First_Bid;
    private Date Started, Ends;
    private String Name, Description, SellerID;
    private List<String> CategoryList;
    private List<Bid> Bids;
    private User seller;
    
    public String generateXML(){
    	String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
    	xml += "<Item ItemID=\""+ItemID+"\">\n";
    	xml += "  <Name>"+Name+"</Name>\n";
    	for(String cat : CategoryList){
    		xml += "  <Category>"+cat+"</Category>\n";
    	}
    	xml += "  <Currently>$"+String.format("%.2f",Currently)+"</Currently>\n";
    	xml += "  <First_Bid>$"+String.format("%.2f",First_Bid)+"</First_Bid>\n";
    	xml += "  <Number_of_Bids>"+Number_of_Bids+"</Number_of_Bids>\n";
    	if(Bids.isEmpty()){
    		xml += "  <Bids/>\n";
    	}
    	else {
    		xml += "  <Bids>\n";
    		for(Bid b : Bids){
    			xml += "    <Bid>";
    			xml += "      <Bidder UserID=\""+b.getBidder().getUserID()+"\" Rating=\""+b.getBidder().getRating()+"\">\n";
    			xml += "        <Location>"+b.getBidder().getLocation()+"</Location>\n";
    			xml += "        <Country>"+b.getBidder().getCountry()+"</Country>\n";
    			xml += "      </Bidder>\n";
    			xml += "    </Bid>\n";
    		}
    		xml += "  </Bids>\n";
    	}
    	xml += "  <Location>"+seller.getLocation()+"</Location>\n";
    	xml += "  <Country>"+seller.getCountry()+"</Country>\n";
    	// Check to make sure this date is formatted correctly
    	xml += "  <Started>"+Started.toString()+"</Started>\n";
    	xml += "  <Ends>"+Ends.toString()+"</Ends>\n";
    	xml += "  <Seller UserID=\""+seller.getUserID()+"\" Rating=\""+seller.getRating()+"\"/>\n";
    	xml += "  <Description>"+Description+"</Description>\n";
    	xml += "</Item>\n";
    	return xml;
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
