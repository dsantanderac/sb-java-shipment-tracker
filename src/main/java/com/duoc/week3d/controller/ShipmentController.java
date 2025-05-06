package com.duoc.week3d.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duoc.week3d.model.Location;
import com.duoc.week3d.model.Shipment;
import com.duoc.week3d.model.Status;
import com.duoc.week3d.model.request.UpdateStatusRequest;
import com.duoc.week3d.service.ShipmentService;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

// Shipments controller layer
@RequestMapping("/api/shipments")
@RestController
public class ShipmentController {
    private final ShipmentService shipmentService;
    private static final Logger logger = LoggerFactory.getLogger(ShipmentController.class);

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping
    public List<Shipment> getAllShipments() {
        return shipmentService.getShipments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable int id) {
        Optional<Shipment> shipment = shipmentService.getShipmentById(id);
        return shipment.map(value -> {
            logger.info("Shipment found by ID: {}", id);
            return new ResponseEntity<>(value, HttpStatus.OK);
        }).orElseGet(() -> {
            logger.info("Shipment not found by ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    @GetMapping("/{id}/location")
    public ResponseEntity<Location> getShipmentCurrentLocationById(@PathVariable int id) {
        Location location = shipmentService.getShipmentCurrentLocationById(id);
        if (location == null) {
            logger.info("Shipment not found by ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Shipment location found by ID: {}", id);
        return ResponseEntity.ok(location);
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<Status> getShipmentStatusById(@PathVariable int id) {
        Status status = shipmentService.getShipmentStatusById(id);
        if (status == null) {
            logger.info("Shipment not found by ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Shipment status found by ID: {}", id);
        return ResponseEntity.ok(status);
    }

    @PostMapping
    public ResponseEntity<Shipment> createShipment(@RequestBody Shipment shipment) {
        Shipment savedShipment = shipmentService.saveShipment(shipment);
        logger.info("Shipment saved with ID: {}", savedShipment.getId());
        return new ResponseEntity<>(savedShipment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable int id) {
        shipmentService.deleteShipment(id);
        logger.info("Shipment deleted with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Shipment> updateShipmentStatus(@PathVariable int id,
            @RequestBody UpdateStatusRequest request) {
        Shipment updatedShipment = shipmentService.updateShipmentStatus(id, request.getStatusId());
        if (updatedShipment == null) {
            logger.info("Shipment not found by ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Shipment status updated with ID: {}", id);
        return new ResponseEntity<>(updatedShipment, HttpStatus.OK);
    }

    @PutMapping("/{id}/location")
    public ResponseEntity<Shipment> updateShipmentLocation(@PathVariable int id,
            @RequestBody Location location) {
        Shipment updatedShipment = shipmentService.updateShipmentLocation(id, location);
        if (updatedShipment == null) {
            logger.info("Shipment not found by ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Shipment location updated with ID: {}", id);
        return new ResponseEntity<>(updatedShipment, HttpStatus.OK);
    }
}
