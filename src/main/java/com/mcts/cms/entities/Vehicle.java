package com.mcts.cms.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mcts.cms.entities.enuns.StatusVehicle;
import com.mcts.cms.entities.services.VehicleService;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_vehicle")
public class Vehicle implements VehicleService {

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

    @PositiveOrZero(message = "O valor de compra deve ser maior ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 2 casas decimais")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    @Column(name = "purchase_value")
    private BigDecimal purchaseValue;

    @PositiveOrZero(message = "O valor da viagem deve ser maior ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "A quilometragem deve ter no máximo 2 casas decimais")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal travel;

    @PositiveOrZero(message = "O valor do direito deve ser maior ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 2 casas decimais")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal rights;

    @PositiveOrZero(message = "O valor do cp deve ser maior ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 2 casas decimais")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal cp;

    @PositiveOrZero(message = "O valor do innater deve ser maior ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 2 casas decimais")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal innater;

    @PositiveOrZero(message = "O valor do carregamento deve ser maior ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 2 casas decimais")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal loading;

    @PositiveOrZero(message = "O valor do despachante deve ser maior ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 2 casas decimais")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    @Column(name = "customs_broker")
    private BigDecimal customsBroker;

    @PositiveOrZero(message = "O valor do motorista deve ser maior ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 2 casas decimais")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal driver;

    @PositiveOrZero(message = "O valor da inspeção deve ser maior ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 2 casas decimais")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal inspection;

    @PositiveOrZero(message = "O valor da licença deve ser maior ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 2 casas decimais")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    @Column(name = "license_plate")
    private BigDecimal licensePlate;

    @Column(name = "order_date")
    private LocalDate orderDate = LocalDate.now();

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

    @Override
    public BigDecimal getSubTotalOrder() {
        return Stream.of(rights, cp, innater, loading, customsBroker, driver, inspection, licensePlate)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getSubTotalAcquisition() {
        return Stream.of(purchaseValue, travel)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getTotalInvestment() {
        return Stream.of(getSubTotalAcquisition(), getSubTotalOrder())
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
