package edu.ucla.cs.cs144;

import java.util.Date;

public class Bid {
	
	private User bidder;
	private Date postingTime;
	private double amount;
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getPostingTime() {
		return postingTime;
	}
	public void setPostingTime(Date postingTime) {
		this.postingTime = postingTime;
	}
	public User getBidder() {
		return bidder;
	}
	public void setBidder(User bidder) {
		this.bidder = bidder;
	}
}
