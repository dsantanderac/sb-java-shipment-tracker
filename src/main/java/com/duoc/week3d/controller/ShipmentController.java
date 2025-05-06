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
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

// Shipments controller layer
@RequestMapping("/api/shipments")
@RestController
@CrossOrigin(origins = "*")
public class ShipmentController {
    private final ShipmentService shipmentService;
    private static final Logger logger = LoggerFactory.getLogger(ShipmentController.class);

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Shipment>>> getAllShipments() {
        List<EntityModel<Shipment>> shipments = shipmentService.getShipments().stream()
                .map(shipment -> {
                    EntityModel<Shipment> shipmentModel = EntityModel.of(shipment);
                    shipmentModel.add(
                            linkTo(methodOn(ShipmentController.class).getShipmentById(shipment.getId())).withSelfRel());
                    return shipmentModel;
                }).toList();
        logger.info("Retrieved {} users", shipments.size());

        return ResponseEntity.ok(CollectionModel.of(shipments,
                linkTo(methodOn(ShipmentController.class).getAllShipments()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Shipment>> getShipmentById(@PathVariable int id) {
        Optional<Shipment> shipment = shipmentService.getShipmentById(id);
        if (shipment.isEmpty()) {
            logger.info("Shipment not found by ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        EntityModel<Shipment> shipmentModel = EntityModel.of(shipment.get());
        shipmentModel.add(linkTo(methodOn(ShipmentController.class).getShipmentById(id)).withSelfRel());

        return ResponseEntity.ok(shipmentModel);
    }

    @GetMapping("/{id}/location")
    public ResponseEntity<EntityModel<Location>> getShipmentCurrentLocationById(@PathVariable int id) {
        Location location = shipmentService.getShipmentCurrentLocationById(id);
        if (location == null) {
            logger.info("Shipment not found by ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Shipment location found by ID: {}", id);
        EntityModel<Location> locationModel = EntityModel.of(location);
        locationModel.add(linkTo(methodOn(ShipmentController.class).getShipmentCurrentLocationById(id)).withSelfRel());
        return ResponseEntity.ok(locationModel);
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<EntityModel<Status>> getShipmentStatusById(@PathVariable int id) {
        Status status = shipmentService.getShipmentStatusById(id);
        if (status == null) {
            logger.info("Shipment not found by ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Shipment status found by ID: {}", id);
        EntityModel<Status> statusModel = EntityModel.of(status);
        statusModel.add(linkTo(methodOn(ShipmentController.class).getShipmentStatusById(id)).withSelfRel());

        return ResponseEntity.ok(statusModel);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Shipment>> createShipment(@RequestBody Shipment shipment) {
        Shipment savedShipment = shipmentService.saveShipment(shipment);
        logger.info("Shipment saved with ID: {}", savedShipment.getId());
        EntityModel<Shipment> shipmentModel = EntityModel.of(savedShipment);
        shipmentModel.add(linkTo(methodOn(ShipmentController.class).getShipmentById(savedShipment.getId()))
                .withSelfRel());

        return ResponseEntity.created(
                linkTo(methodOn(ShipmentController.class).getShipmentById(savedShipment.getId())).toUri())
                .body(shipmentModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable int id) {
        shipmentService.deleteShipment(id);
        logger.info("Shipment deleted with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<EntityModel<Shipment>> updateShipmentStatus(@PathVariable int id,
            @RequestBody UpdateStatusRequest request) {
        Shipment updatedShipment = shipmentService.updateShipmentStatus(id, request.getStatusId());
        if (updatedShipment == null) {
            logger.info("Shipment not found by ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Shipment status updated with ID: {}", id);
        EntityModel<Shipment> shipmentModel = EntityModel.of(updatedShipment);
        shipmentModel.add(linkTo(methodOn(ShipmentController.class).getShipmentById(updatedShipment.getId()))
                .withSelfRel());

        return new ResponseEntity<>(shipmentModel, HttpStatus.OK);
    }

    @PutMapping("/{id}/location")
    public ResponseEntity<EntityModel<Shipment>> updateShipmentLocation(@PathVariable int id,
            @RequestBody Location location) {
        Shipment updatedShipment = shipmentService.updateShipmentLocation(id, location);
        if (updatedShipment == null) {
            logger.info("Shipment not found by ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Shipment location updated with ID: {}", id);
        EntityModel<Shipment> shipmentModel = EntityModel.of(updatedShipment);
        shipmentModel.add(linkTo(methodOn(ShipmentController.class).getShipmentById(updatedShipment.getId()))
                .withSelfRel());

        return new ResponseEntity<>(shipmentModel, HttpStatus.OK);
    }
}
