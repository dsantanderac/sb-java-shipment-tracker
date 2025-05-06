package com.duoc.week3d.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.duoc.week3d.model.Location;
import com.duoc.week3d.model.Shipment;
import com.duoc.week3d.model.Status;
import com.duoc.week3d.repository.ShipmentRepository;
import com.duoc.week3d.repository.StatusRepository;

import lombok.RequiredArgsConstructor;

// Service layer to manage business logic
@Service
@RequiredArgsConstructor
public class ShipmentImpl implements ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final StatusRepository statusRepository;
    private static final Logger logger = LoggerFactory.getLogger(ShipmentImpl.class);

    // Override get shipments
    @Override
    public List<Shipment> getShipments() {
        return shipmentRepository.findAll();
    }

    // Override get shipment by id
    @Override
    public Optional<Shipment> getShipmentById(int id) {
        return shipmentRepository.findById(id);
    }

    // Override get shipment location by id
    @Override
    public Location getShipmentCurrentLocationById(int id) {
        Location location = shipmentRepository.findCurrentLocation(id);
        if (location != null) {
            logger.info("Shipment found with ID: {} - method getShipmentCurrentLocationById", id);
            return location;
        } else {
            logger.warn("Shipment with ID: {} not found - method getShipmentCurrentLocationById", id);
            return null;
        }
    }

    // Override get shipment status by id
    @Override
    public Status getShipmentStatusById(int id) {
        Optional<Shipment> shipmentFound = shipmentRepository.findById(id);
        if (shipmentFound.isPresent()) {
            Shipment shipment = shipmentFound.get();
            logger.info("Shipment found with ID: {} - method getShipmentStatusById", id);
            return shipment.getStatus();
        } else {
            logger.warn("Shipment with ID: {} not found - method getShipmentStatusById", id);
            return null;
        }
    }

    // Override save shipment
    @Override
    public Shipment saveShipment(Shipment shipment) {
        logger.info("Create new shipment: {} - method saveShipment", shipment);
        try {
            Shipment savedShipment = shipmentRepository.save(shipment);
            logger.info("Shipment created with ID: {} - method saveShipment", savedShipment.getId());
            return savedShipment;
        } catch (Exception e) {
            logger.error("Error creating shipment - method saveShipment", e);
            throw e;
        }
    }

    // Override update shipment status
    @Override
    public Shipment updateShipmentStatus(int id, int statusId) {
        logger.info("Update shipment with ID: {} - method updateShipmentStatus", id);
        Optional<Shipment> existingShipment = shipmentRepository.findById(id);
        if (existingShipment.isPresent()) {
            logger.info("Shipment found with ID: {} - method updateShipmentStatus", id);
            Shipment shipment = existingShipment.get();
            Optional<Status> status = statusRepository.findById(statusId);
            if (!status.isPresent()) {
                logger.warn("Status with ID: {} not found - method updateShipmentStatus", statusId);
                return null;
            }
            shipment.setStatus(status.get());
            shipment.setLastUpdated(LocalDateTime.now());
            return shipmentRepository.save(shipment);
        } else {
            logger.warn("Shipment with ID: {} not found - method updateShipmentStatus", id);
            return null;
        }

    }

    // Override delete shipment
    @Override
    public void deleteShipment(int id) {
        logger.info("Delete shipment with ID: {} - method deleteShipment", id);
        try {
            Optional<Shipment> existingShipment = shipmentRepository.findById(id);

            if (existingShipment.isPresent()) {
                shipmentRepository.delete(existingShipment.get());
                logger.info("Shipment with ID: {} deleted - method deleteShipment", id);
            } else {
                logger.warn("Shipment with ID: {} not found - method deleteShipment", id);
            }
        } catch (Exception e) {
            logger.error("Delete shipment error - method deleteShipment", e);
            throw e;
        }
    }

    // Override update shipment location
    @Override
    public Shipment updateShipmentLocation(int id, Location location) {
        logger.info("Update shipment location with ID: {} - method updateShipmentLocation", id);
        Optional<Shipment> existingShipment = shipmentRepository.findById(id);
        if (existingShipment.isPresent()) {
            Shipment shipment = existingShipment.get();
            shipment.setCurrentLocationCountry(location.getCountry());
            shipment.setCurrentLocationCity(location.getCity());
            shipment.setLastUpdated(LocalDateTime.now());
            return shipmentRepository.save(shipment);
        } else {
            logger.warn("Shipment with ID: {} not found - method updateShipmentLocation", id);
            return null;
        }
    }
}
