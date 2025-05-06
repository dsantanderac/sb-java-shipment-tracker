package com.duoc.week3d.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.duoc.week3d.model.Location;
import com.duoc.week3d.model.Shipment;

// Repository layer where the data is managed
@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {
        // Custom query to get the current location of a shipment by its ID
        @Query("SELECT new com.duoc.week3d.model.Location(s.currentLocationCountry, s.currentLocationCity) FROM Shipment s WHERE s.id = :id")
        Location findCurrentLocation(@Param("id") int id);
}
