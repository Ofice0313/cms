package com.mcts.cms.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_acquisition")
public class Acquisition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @PositiveOrZero(message = "O valor de compra deve ser maior ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 2 casas decimais")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    @Column(name = "purchase_value")
    private BigDecimal purchaseValue;

    @PositiveOrZero(message = "O valor da viagem deve ser maior ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "A quilometragem deve ter no máximo 2 casas decimais")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal travel;

    @OneToOne
    @MapsId
    private Vehicle vehicle;

    @OneToOne(mappedBy = "acquisition", cascade = CascadeType.ALL)
    private Order order;

    public BigDecimal getTotal() {
        return Stream.of(purchaseValue, travel)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
