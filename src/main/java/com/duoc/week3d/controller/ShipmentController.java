package com.duoc.week3d.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duoc.week3d.model.Shipment;
import com.duoc.week3d.service.ShipmentService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

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
}
