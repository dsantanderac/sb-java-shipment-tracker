package com.duoc.week3d.service;

import java.util.List;

import com.duoc.week3d.model.Location;
import com.duoc.week3d.model.Shipment;
import com.duoc.week3d.model.ShipmentStatus;

public interface ShipmentService {
    List<Shipment> getShipments();

    Shipment getShipmentById(int id);

    Location getShipmentCurrentLocationById(int id);

    ShipmentStatus getShipmentStatusById(int id);
}
