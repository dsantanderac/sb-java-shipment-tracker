package com.duoc.week3d.model;

// Class to represent the status of a shipment
public class ShipmentStatus {
    public enum Status {
        PENDING,
        IN_TRANSIT,
        DELIVERED,
        CANCELLED,
        HELD_BY_CUSTOMS
    }

    private Status status;
    private String carrier;

    public ShipmentStatus(Status status, String carrier) {
        this.status = status;
        this.carrier = carrier;
    }

    public Status getStatus() {
        return status;
    }

    public String getCarrier() {
        return carrier;
    }
}
