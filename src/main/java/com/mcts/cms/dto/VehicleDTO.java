package com.mcts.cms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mcts.cms.entities.Vehicle;
import com.mcts.cms.entities.enuns.StatusVehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {

    private Long id;
    private String brand;
    private String model;
    @JsonProperty("manufactured_year")
    private Integer year;
    @JsonProperty("acquisition_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate acquisitionDate;
    @JsonProperty("registration_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate registrationDate;
    private StatusVehicle status;

    public VehicleDTO(Vehicle entity) {
        this.id = entity.getId();
        this.brand = entity.getBrand();
        this.model = entity.getModel();
        this.year = entity.getYear();
        this.acquisitionDate = entity.getAcquisitionDate();
        this.registrationDate = entity.getRegistrationDate();
        this.status = entity.getStatus();
    }

}
