package com.duoc.week3d.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.duoc.week3d.model.Shipment;
import com.duoc.week3d.repository.ShipmentRepository;

// Service layer to manage business logic
@Service
public class ShipmentImpl implements ShipmentService {
    private final ShipmentRepository shipmentRepository = new ShipmentRepository();

    // Override get shipments
    @Override
    public List<Shipment> getShipments() {
        return shipmentRepository.getShipments();
    }
}
