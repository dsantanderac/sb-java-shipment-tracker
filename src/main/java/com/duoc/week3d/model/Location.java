package com.duoc.week3d.model;

// Class to represent the location of a shipment
public class Location {
    private String country;
    private String city;

    public Location(String country, String city) {
        this.country = country;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }
}
