package com.duoc.week3d.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.duoc.week3d.model.Location;
import com.duoc.week3d.model.Shipment;
import com.duoc.week3d.model.ShipmentStatus;
import com.duoc.week3d.model.ShipmentStatus.Status;

// Repository layer where the data is managed
@Repository
public class ShipmentRepository {
        private final List<Shipment> shipments = new ArrayList<>();

        // Return all available shipments
        public List<Shipment> getShipments() {
                return shipments;
        }

        // Return a specific shipment details by id
        public Shipment getShipmentById(int id) {
                for (Shipment shipment : shipments) {
                        if (shipment.getId() == id)
                                return shipment;
                }
                return null;
        }

        // Return the current location of a specific shipment
        public Location getCurrentLocationById(int id) {
                for (Shipment shipment : shipments) {
                        if (shipment.getId() == id)
                                return shipment.getCurrentLocation();
                }
                return null;
        }

        // Return status of a specific shipment
        public ShipmentStatus getStatusById(int id) {
                for (Shipment shipment : shipments) {
                        if (shipment.getId() == id)
                                return shipment.getStatus();
                }
                return null;
        }

        public ShipmentRepository() {
                // Generate some in-memory shipments.
                shipments.add(new Shipment(1, "Sender 1", "Receiver 1", new Location("CHN", "Pekin"),
                                new Location("CHL", "Santiago"), new Location("CHL", "Santiago"),
                                new ShipmentStatus(Status.IN_TRANSIT, "Correos de Chile"),
                                new GregorianCalendar(2025, 4, 1).getTime(),
                                Date.from(new Date().toInstant()), Date.from(new Date().toInstant())));
                shipments.add(new Shipment(2, "Sender 2", "Receiver 2", new Location("USA", "Miami"),
                                new Location("CHL", "Valparaiso"), new Location("USA", "Miami"),
                                new ShipmentStatus(Status.PENDING, "USP"), new GregorianCalendar(2025, 4, 15).getTime(),
                                Date.from(new Date().toInstant()), Date.from(new Date().toInstant())));
                shipments.add(new Shipment(3, "Sender 3", "Receiver 3", new Location("USA", "New York"),
                                new Location("CHL", "Curico"), new Location("CHL", "Curico"),
                                new ShipmentStatus(Status.DELIVERED, "Starken"),
                                new GregorianCalendar(2025, 3, 28).getTime(),
                                new GregorianCalendar(2025, 3, 28).getTime(),
                                new GregorianCalendar(2025, 2, 28).getTime()));
                shipments.add(new Shipment(4, "Sender 4", "Receiver 4", new Location("BRA", "Sao Paulo"),
                                new Location("CHL", "Valparaiso"), new Location("CHL", "Valparaiso"),
                                new ShipmentStatus(Status.DELIVERED, "Chilexpress"),
                                new GregorianCalendar(2025, 3, 28).getTime(),
                                new GregorianCalendar(2025, 3, 28).getTime(),
                                new GregorianCalendar(2025, 2, 28).getTime()));
                shipments.add(new Shipment(5, "Sender 5", "Receiver 5", new Location("USA", "Miami"),
                                new Location("CHL", "Santiago"), new Location("ARG", "Buenos Aires"),
                                new ShipmentStatus(Status.CANCELLED, "USP"),
                                new GregorianCalendar(2025, 3, 28).getTime(),
                                new GregorianCalendar(2025, 3, 28).getTime(),
                                new GregorianCalendar(2025, 2, 28).getTime()));
        }
}
