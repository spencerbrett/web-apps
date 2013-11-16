package edu.ucla.cs.cs144;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bid implements Comparable {
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
	
    public int compareTo(Object o) {
        Bid other = (Bid) o;
        SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
        Date timeDate1 = null;
        Date timeDate2 = null;
        try {
            timeDate1 = sdf.parse(this.Time);
            timeDate2 = sdf.parse(other.getTime());
        } catch (java.text.ParseException e) {
            System.err.println(e);
        }
        
        if (timeDate1.after(timeDate2)) {
            return -1;
        } else if (timeDate1.equals(timeDate2)) {
            return 0;
        } else {
            return 1;
        }
    }
}