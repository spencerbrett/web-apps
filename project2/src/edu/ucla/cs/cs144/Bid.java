package edu.ucla.cs.cs144;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	public String getPostingTime() {
		DateFormat ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return ts.format(postingTime);
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
