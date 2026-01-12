package com.mcts.cms.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToOne
    @JoinColumn(name = "acquisition_id")
    private Acquisition acquisition;

    @OneToOne
    @MapsId
    private Vehicle vehicle;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Sale sale;

    public BigDecimal getTotal() {
        return Stream.of(rights, cp, innater, loading, customsBroker, driver, inspection, licensePlate)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getInvestment() {
        return Stream.of(acquisition.getTotal(), getTotal())
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
