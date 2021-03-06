package edu.ucla.cs.cs144;

public class User {
    private String userId;

    private int rating;

    private String location;

    private String country;

    public String getCountry() {
        if (country == null) {
            return "";
        } else {
            return country;
        }
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocation() {
        if (location == null) {
            return "";
        } else {
            return location;
        }
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
