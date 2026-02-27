package com.mcts.cms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mcts.cms.entities.Quotation;
import com.mcts.cms.entities.Vehicle;
import com.mcts.cms.entities.enuns.StatusVehicle;
import com.mcts.cms.entities.enuns.Step;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotationDTO {

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

    private Step step;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    @JsonProperty("purchase_value")
    private BigDecimal purchaseValue;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal food;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal hotel;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal fuel;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal rights;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal cp;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal innater;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal loading;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    @JsonProperty("customs_broker")
    private BigDecimal customsBroker;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal driver;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal inspection;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    @JsonProperty("license_plate")
    private BigDecimal licensePlate;

    @Column(name = "order_date")
    private LocalDate orderDate = LocalDate.now();

    private BigDecimal acquisition;

    private BigDecimal order;

    private BigDecimal investment;

    private BigDecimal travel;

    @JsonProperty("sale_value")
    private BigDecimal saleValue;

    private BigDecimal diversos;

    private String observations;

    @JsonProperty("client_id")
    private ClientDTO client;

    public QuotationDTO(Quotation entity) {
        this.id = entity.getId();
        this.brand = entity.getBrand();
        this.model = entity.getModel();
        this.year = entity.getYear();
        this.acquisitionDate = entity.getAcquisitionDate();
        this.registrationDate = entity.getRegistrationDate();
        this.step = entity.getStep();
        this.cp = entity.getCp();
        this.purchaseValue = entity.getPurchaseValue();
        this.driver = entity.getDriver();
        this.hotel = entity.getHotel();
        this.food = entity.getFood();
        this.fuel = entity.getFuel();
        this.rights = entity.getRights();
        this.innater = entity.getInnater();
        this.loading = entity.getLoading();
        this.customsBroker = entity.getCustomsBroker();
        this.inspection = entity.getInspection();
        this.licensePlate = entity.getLicensePlate();
        this.acquisition = entity.getSubTotalAcquisition();
        this.order = entity.getSubTotalOrder();
        this.investment = entity.getTotalInvestment();
        this.travel =entity.getValueTravel();
        this.saleValue = entity.getSaleValue();
        this.diversos = entity.getDiversos();
        this.observations = entity.getObservations();
        this.client = new ClientDTO(entity.getClient());
    }

}
