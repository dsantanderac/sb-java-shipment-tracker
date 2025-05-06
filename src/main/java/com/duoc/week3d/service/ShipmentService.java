package com.duoc.week3d.service;

import java.util.List;
import java.util.Optional;

import com.duoc.week3d.model.Location;
import com.duoc.week3d.model.Shipment;
import com.duoc.week3d.model.Status;

public interface ShipmentService {
    List<Shipment> getShipments();

    Optional<Shipment> getShipmentById(int id);

    Location getShipmentCurrentLocationById(int id);

    Status getShipmentStatusById(int id);

    Shipment saveShipment(Shipment shipment);

    Shipment updateShipmentStatus(int id, int statusId);

    void deleteShipment(int id);

    Shipment updateShipmentLocation(int id, Location location);
}
