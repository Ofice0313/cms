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
import java.util.Optional;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_sale")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sale_value")
    private BigDecimal saleValue;
    @Column(name = "sale_date")
    private LocalDate saleDate;
    private String observations;

    @PositiveOrZero(message = "O valor de diversos deve ser maior ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 2 casas decimais")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    private BigDecimal diversos;

    @OneToOne
    @MapsId
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public BigDecimal getSaleValueWithDiversos() {
        BigDecimal sale = saleValue != null ? saleValue : BigDecimal.ZERO;
        BigDecimal div = diversos != null ? diversos : BigDecimal.ZERO;
        return sale.add(div);
    }

    public BigDecimal getProfit() {
        return Optional.ofNullable(getSaleValueWithDiversos())
                .flatMap(total -> Optional.ofNullable(vehicle)
                        .flatMap(v -> Optional.ofNullable(v.getTotalInvestment())
                                .map(total::subtract)
                        )
                )
                .orElse(BigDecimal.ZERO);
    }

}
