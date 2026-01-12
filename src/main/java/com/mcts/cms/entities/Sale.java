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
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public BigDecimal getProfit() {
        return Optional.ofNullable(saleValue)
                .flatMap(saleVal -> Optional.ofNullable(order)
                        .flatMap(orderObj -> Optional.ofNullable(orderObj.getInvestment())
                                .map(investment -> saleVal.subtract(investment))
                        )
                )
                .orElse(BigDecimal.ZERO);
    }

}
