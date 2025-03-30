package com.duoc.week3d.model;

import java.util.Date;

// Class to represent a shipment
public class Shipment {
    private int id;
    private String sender;
    private String receiver;
    private Location originLocation;
    private Location deliverLocation;
    private Location currentLocation;
    private ShipmentStatus status;
    private Date estimatedArrival;
    private Date lastUpdated;
    private Date createdAt;

    public Shipment(int id, String sender, String receiver, Location originLocation, Location deliverLocation,
            Location currentLocation, ShipmentStatus status,
            Date estimatedArrival, Date lastUpdated, Date createdAt) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.originLocation = originLocation;
        this.deliverLocation = deliverLocation;
        this.currentLocation = currentLocation;
        this.status = status;
        this.estimatedArrival = estimatedArrival;
        this.lastUpdated = lastUpdated;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public Location getOriginLocation() {
        return originLocation;
    }

    public Location getDeliverLocation() {
        return deliverLocation;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public Date getEstimatedArrival() {
        return estimatedArrival;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
