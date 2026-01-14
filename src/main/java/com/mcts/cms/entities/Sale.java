package com.mcts.cms.entities;

import jakarta.persistence.*;
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

    @OneToOne
    @MapsId
    private Vehicle vehicle;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;


    public BigDecimal getProfit() {
        return Optional.ofNullable(saleValue)
                .flatMap(saleVal -> Optional.ofNullable(vehicle)
                        .flatMap(orderObj -> Optional.ofNullable(orderObj.getTotalInvestment())
                                .map(investment -> saleVal.subtract(investment))
                        )
                )
                .orElse(BigDecimal.ZERO);
    }

}
