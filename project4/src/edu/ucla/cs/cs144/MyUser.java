package edu.ucla.cs.cs144;

public class User {
	private String userId;
	private int rating;
	private String location;
	private String country;
	
	public User(String userId, int rating, String location, String country) {
		super();
		this.userId = userId;
		this.rating = rating;
		this.location = location;
		this.country = country;
	}
	public User() {
		userId = null;
		rating = 0;
		location = null;
		country = null;
	}
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
