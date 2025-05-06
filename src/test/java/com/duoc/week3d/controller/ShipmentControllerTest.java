package com.duoc.week3d.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.duoc.week3d.model.Location;
import com.duoc.week3d.model.Shipment;
import com.duoc.week3d.model.Status;
import com.duoc.week3d.model.request.UpdateStatusRequest;
import com.duoc.week3d.service.ShipmentService;

@ExtendWith(MockitoExtension.class)
public class ShipmentControllerTest {

    @Mock
    private ShipmentService shipmentService;

    @InjectMocks
    private ShipmentController shipmentController;

    private Shipment shipment;

    @BeforeEach
    void setup() {
        shipment = Shipment.builder()
                .id(1)
                .sender("John Doe")
                .receiver("Jane Smith")
                .countryOrigin("USA")
                .cityOrigin("New York")
                .countryDestination("CHL")
                .cityDestination("Santiago")
                .currentLocationCountry("USA")
                .currentLocationCity("Miami")
                .status(Status.builder().id(3).name("Pending").build())
                .createdAt(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .estimatedArrival(LocalDate.now().plusDays(5))
                .build();
    }

    @Test
    void testGetAllShipments() {
        // Arrange
        when(shipmentService.getShipments()).thenReturn(List.of(shipment));

        // Act
        ResponseEntity<CollectionModel<EntityModel<Shipment>>> response = shipmentController.getAllShipments();
        CollectionModel<EntityModel<Shipment>> foundShipmentsModel = response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, foundShipmentsModel.getContent().size());
        assertNotNull(foundShipmentsModel);
        assertTrue(foundShipmentsModel.hasLink("self"));
        verify(shipmentService).getShipments();
    }

    @Test
    void testGetShipmentById() {
        // Arrange
        when(shipmentService.getShipmentById(1)).thenReturn(java.util.Optional.of(shipment));

        // Act
        ResponseEntity<EntityModel<Shipment>> response = shipmentController.getShipmentById(1);
        EntityModel<Shipment> foundShipmentModel = response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(foundShipmentModel);
        assertTrue(foundShipmentModel.hasLink("self"));
        verify(shipmentService).getShipmentById(1);
    }

    @Test
    void testGetShipmentByIdNotFound() {
        // Arrange
        when(shipmentService.getShipmentById(1)).thenReturn(java.util.Optional.empty());

        // Act
        ResponseEntity<EntityModel<Shipment>> response = shipmentController.getShipmentById(1);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(shipmentService).getShipmentById(1);
    }

    @Test
    void testGetShipmentCurrentLocationById() {
        // Arrange
        when(shipmentService.getShipmentCurrentLocationById(1)).thenReturn(Location.builder()
                .country("USA")
                .city("Miami")
                .build());

        // Act
        ResponseEntity<EntityModel<Location>> response = shipmentController.getShipmentCurrentLocationById(1);
        EntityModel<Location> foundShipmentModel = response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(foundShipmentModel);
        assertTrue(foundShipmentModel.hasLink("self"));
        verify(shipmentService).getShipmentCurrentLocationById(1);
    }

    @Test
    void testGetShipmentCurrentLocationByIdNotFound() {
        // Arrange
        when(shipmentService.getShipmentCurrentLocationById(1)).thenReturn(null);

        // Act
        ResponseEntity<EntityModel<Location>> response = shipmentController.getShipmentCurrentLocationById(1);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(shipmentService).getShipmentCurrentLocationById(1);
    }

    @Test
    void testGetShipmentStatusById() {
        // Arrange
        when(shipmentService.getShipmentStatusById(1)).thenReturn(shipment.getStatus());

        // Act
        ResponseEntity<EntityModel<Status>> response = shipmentController.getShipmentStatusById(1);
        EntityModel<Status> foundStatusModel = response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(foundStatusModel);
        assertEquals("Pending", foundStatusModel.getContent().getName());
        assertTrue(foundStatusModel.hasLink("self"));
        verify(shipmentService).getShipmentStatusById(1);
    }

    @Test
    void testGetShipmentStatusByIdNotFound() {
        // Arrange
        when(shipmentService.getShipmentStatusById(2)).thenReturn(null);

        // Act
        ResponseEntity<EntityModel<Status>> response = shipmentController.getShipmentStatusById(2);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(shipmentService).getShipmentStatusById(2);
    }

    @Test
    void testCreateShipment() {
        // Arrange
        when(shipmentService.saveShipment(shipment)).thenReturn(shipment);

        // Act
        ResponseEntity<EntityModel<Shipment>> response = shipmentController.createShipment(shipment);
        EntityModel<Shipment> createdShipmentModel = response.getBody();

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(createdShipmentModel);
        assertTrue(createdShipmentModel.hasLink("self"));
        verify(shipmentService).saveShipment(shipment);
    }

    @Test
    void testUpdateShipmentLocation() {
        // Arrange
        shipment.setCurrentLocationCountry("CHL");
        shipment.setCurrentLocationCity("Santiago");
        when(shipmentService.updateShipmentLocation(1, Location.builder()
                .country("CHL")
                .city("Santiago")
                .build()))
                .thenReturn(shipment);

        // Act
        ResponseEntity<EntityModel<Shipment>> response = shipmentController.updateShipmentLocation(1,
                Location.builder().country("CHL").city("Santiago").build());
        EntityModel<Shipment> updatedShipmentModel = response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(updatedShipmentModel);
        assertEquals("Santiago", updatedShipmentModel.getContent().getCurrentLocationCity());
        assertEquals("CHL", updatedShipmentModel.getContent().getCurrentLocationCountry());
        assertTrue(updatedShipmentModel.hasLink("self"));
        verify(shipmentService).updateShipmentLocation(1, Location.builder()
                .country("CHL")
                .city("Santiago")
                .build());
    }

    @Test
    void testUpdateShipmentStatus() {
        // Arrange
        shipment.setStatus(Status.builder().id(2).name("INACTIVE").build());
        when(shipmentService.updateShipmentStatus(1, 2)).thenReturn(shipment);

        // Act
        ResponseEntity<EntityModel<Shipment>> response = shipmentController.updateShipmentStatus(1,
                UpdateStatusRequest.builder()
                        .statusId(2)
                        .build());
        EntityModel<Shipment> updatedShipmentModel = response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(updatedShipmentModel);
        assertEquals("INACTIVE", updatedShipmentModel.getContent().getStatus().getName());
        assertTrue(updatedShipmentModel.hasLink("self"));
        verify(shipmentService).updateShipmentStatus(1, 2);
    }

    @Test
    void testDeleteShipment() {
        // Arrange
        int shipmentId = shipment.getId();
        doNothing().when(shipmentService).deleteShipment(shipmentId);

        // Act
        ResponseEntity<Void> response = shipmentController.deleteShipment(shipmentId);

        // Assert
        verify(shipmentService).deleteShipment(shipmentId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
