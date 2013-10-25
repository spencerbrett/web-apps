package edu.ucla.cs.cs144;

import java.util.Date;

public class Bid {

	private int itemId;
	private int userId;
	private Date postingTime;
	private double amount;
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public Date getPostingTime() {
		return postingTime;
	}
	public void setPostingTime(Date postingTime) {
		this.postingTime = postingTime;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
