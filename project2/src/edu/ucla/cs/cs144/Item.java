package edu.ucla.cs.cs144;

import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Item {

	private int itemId;
	private String name;
	private double currentBid;
	private double buyPrice;
	private double firstBid;
	private int numBids;
	private int sellerId;
	private Date started;
	private Date ends;
	private String description;
	private List categories = new ArrayList();

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

	public double getBuyPrice() {
		return buyPrice;
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

	public void setSellerId(int id) {
		sellerId = id;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setStarted(Date started) {
		this.started = started;
	}

	public Date getStarted() {
		return started;
	}

	public void setEnds(Date ends) {
		this.ends = ends;
	}

	public Date getEnds() {
		return ends;
	}

	public void setDescription(String desc) {
		description = desc;
	}

	public String getDescription() {
		return description;
	}

	public List getCategories() {
		return categories;
	}

	public void setCategories(List categories) {
		this.categories = categories;
	}
	
	public void insertCategory(String category) {
		categories.add(category);
	}
	
	public String getCategory(int index) {
		return (String) categories.get(index);
	}
}
