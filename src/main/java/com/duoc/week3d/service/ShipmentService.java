package com.duoc.week3d.service;

import java.util.List;

import com.duoc.week3d.model.Shipment;

public interface ShipmentService {
    List<Shipment> getShipments();

    Shipment getShipmentById(int id);
}
