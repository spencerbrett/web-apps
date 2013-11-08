package edu.ucla.cs.cs144;

public class User {
	private String UserID;
	private int Rating;
	private String Location;
	private String Country;
	
	public User(String userID, int rating, String location, String country) {
		super();
		UserID = userID;
		Rating = rating;
		Location = location;
		Country = country;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public int getRating() {
		return Rating;
	}

	public void setRating(int rating) {
		Rating = rating;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}
	
}
