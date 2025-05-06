package com.duoc.week3d.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "shipment")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_shipment_id")
    @SequenceGenerator(name = "sq_shipment_id", sequenceName = "sq_shipment_id", allocationSize = 1, initialValue = 1)
    private int id;

    @NotNull(message = "Sender name cannot be null")
    @Size(min = 1, max = 50, message = "Sender name must be between 1 and 50 characters")
    private String sender;

    @NotNull(message = "Receiver name cannot be null")
    @Size(min = 1, max = 50, message = "Receiver name must be between 1 and 50 characters")
    private String receiver;

    @NotNull(message = "Origin country cannot be null")
    private String countryOrigin;

    @NotNull(message = "Origin country cannot be null")
    private String cityOrigin;

    @NotNull(message = "Destination country cannot be null")
    private String countryDestination;

    @NotNull(message = "Destination city cannot be null")
    private String cityDestination;

    @NotNull(message = "Current location country cannot be null")
    private String currentLocationCountry;

    @NotNull(message = "Current location city cannot be null")
    private String currentLocationCity;

    private LocalDate estimatedArrival;
    private LocalDateTime lastUpdated = LocalDateTime.now();
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;
}
