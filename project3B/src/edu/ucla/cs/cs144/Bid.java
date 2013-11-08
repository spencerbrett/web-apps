package edu.ucla.cs.cs144;

import java.util.Date;

public class Bid {
	private User Bidder;
	private Date Time;
	private float Amount;
	
	public Bid(User bidder, Date time, float amount) {
		super();
		Bidder = bidder;
		Time = time;
		Amount = amount;
	}
	public Bid() {
		super();
		Bidder = null;
		Time = null;
		Amount = 0;
	}
	public float getAmount() {
		return Amount;
	}

	public void setAmount(float amount) {
		Amount = amount;
	}

	public User getBidder() {
		return Bidder;
	}

	public void setBidder(User bidder) {
		Bidder = bidder;
	}

	public Date getTime() {
		return Time;
	}

	public void setTime(Date time) {
		Time = time;
	}
	
}
