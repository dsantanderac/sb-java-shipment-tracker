package com.duoc.week3d.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duoc.week3d.model.Location;
import com.duoc.week3d.model.Shipment;
import com.duoc.week3d.service.ShipmentService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Shipments controller layer
@RequestMapping("/api/shipments")
@RestController
public class ShipmentController {
    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping
    public List<Shipment> getAllShipments() {
        return shipmentService.getShipments();
    }

    @GetMapping("/{id}")
    public Shipment getShipmentById(@PathVariable int id) {
        return shipmentService.getShipmentById(id);
    }

    @GetMapping("/{id}/location")
    public Location getShipmentCurrentLocationById(@PathVariable int id) {
        return shipmentService.getShipmentCurrentLocationById(id);
    }
}
