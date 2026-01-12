package com.mcts.cms.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mcts.cms.entities.enuns.StatusVehicle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    @Column(name = "manufactured_year")
    private Integer year;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "acquisition_date")
    private LocalDate acquisitionDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "registration_date", updatable = false)
    private LocalDate registrationDate = LocalDate.now();
    private StatusVehicle status;

    @OneToOne(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private Acquisition acquisition;

    @OneToOne(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private Sale sale;

    @PrePersist
    protected void onCreate() {
        if (registrationDate == null) {
            registrationDate = LocalDate.now();
        }
        if(status == null) {
            status = StatusVehicle.STOCK;
        }
    }
}
