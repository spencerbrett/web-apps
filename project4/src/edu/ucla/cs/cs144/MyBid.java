package edu.ucla.cs.cs144;

public class Bid {
	private User bidder;
	private String time;
	private float amount;
	
	public Bid(User bidder, String time, float amount) {
		super();
		this.bidder = bidder;
		this.time = time;
		this.amount = amount;
	}
	public Bid() {
		super();
		bidder = null;
		time = null;
		amount = 0;
	}
	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public User getBidder() {
		return bidder;
	}

	public void setBidder(User bidder) {
		this.bidder = bidder;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
