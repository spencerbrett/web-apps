package edu.ucla.cs.cs144;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bid {
	private User Bidder;
	private String Time;
	private float Amount;
	
	public Bid(User bidder, String time, float amount) {
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

	public String getTime() {
		return Time;
	}

	public void setTime(Date timeDate) {
		SimpleDateFormat newDateFormat = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
        String formattedTime = newDateFormat.format(timeDate);
		Time = formattedTime;
	}
	public void setTime(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date timeDate = null;
        try {
            timeDate = sdf.parse(time);
        } catch (java.text.ParseException e) {
            System.err.println(e);
        }
		SimpleDateFormat newDateFormat = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
        String formattedTime = newDateFormat.format(timeDate);
		Time = formattedTime;
	}
	
}
