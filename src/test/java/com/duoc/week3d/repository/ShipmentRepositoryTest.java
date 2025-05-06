
package com.duoc.week3d.repository;

import com.duoc.week3d.model.Shipment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ShipmentRepositoryTest {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Test
    public void testSaveShipment() {
        Shipment shipment = Shipment.builder()
                .sender("John Doe")
                .receiver("Jane Smith")
                .countryOrigin("USA")
                .cityOrigin("New York")
                .countryDestination("CHL")
                .cityDestination("Santiago")
                .currentLocationCountry("USA")
                .currentLocationCity("Miami")
                .status(null) // Assuming status is set later
                .createdAt(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .estimatedArrival(LocalDate.now().plusDays(5))
                .build();

        Shipment savedShipment = shipmentRepository.save(shipment);

        assertNotNull(savedShipment.getId());
        assertEquals(shipment.getSender(), savedShipment.getSender());
        assertEquals(shipment.getReceiver(), savedShipment.getReceiver());
    }

}
