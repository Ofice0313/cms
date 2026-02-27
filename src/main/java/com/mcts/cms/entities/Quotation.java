package com.mcts.cms.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mcts.cms.entities.enuns.StatusVehicle;
import com.mcts.cms.entities.enuns.Step;
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

@Entity
@Table(name = "tb_quotation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quotation implements VehicleService {

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

    @PositiveOrZero(message = "O valor de compra deve ser maior ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 2 casas decimais")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    @Column(name = "purchase_value")
    private BigDecimal purchaseValue;

    @PositiveOrZero(message = "O valor da viagem deve ser maior ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "A quilometragem deve ter no máximo 2 casas decimais")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal hotel;

    @PositiveOrZero(message = "O valor da viagem deve ser maior ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "A quilometragem deve ter no máximo 2 casas decimais")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal food;

    @PositiveOrZero(message = "O valor da viagem deve ser maior ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "A quilometragem deve ter no máximo 2 casas decimais")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal fuel;

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
    private BigDecimal innater = new BigDecimal("12000.0");

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
    private BigDecimal driver = new BigDecimal("2000.0");

    @PositiveOrZero(message = "O valor da inspeção deve ser maior ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 2 casas decimais")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal inspection = new BigDecimal("800.0");

    @PositiveOrZero(message = "O valor da licença deve ser maior ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 2 casas decimais")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    @Column(name = "license_plate")
    private BigDecimal licensePlate = new BigDecimal("5000.0");

    @Column(name = "order_date")
    private LocalDate orderDate = LocalDate.now();

    @PositiveOrZero(message = "O valor de diversos deve ser maior ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 2 casas decimais")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal diversos;

    private String observations;

    private Step step;

    private BigDecimal saleValue;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @PrePersist
    protected void onCreate() {
        if (registrationDate == null) {
            registrationDate = LocalDate.now();
        }
        if(step == null) {
            step = Step.DURBAN_SA;
        }
    }

    @Override
    public BigDecimal getSubTotalOrder() {
        return Stream.of(rights, cp, innater, loading, customsBroker, driver, inspection, licensePlate)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getValueTravel() {
        return Stream.of(hotel, food, fuel)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getSubTotalAcquisition() {
        return purchaseValue;
    }

    @Override
    public BigDecimal getTotalInvestment() {
        return Stream.of(purchaseValue, getValueTravel(), getSubTotalOrder())
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getValueSale() {
        return Stream.of(saleValue,getTotalInvestment())
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
