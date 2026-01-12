package com.mcts.cms.entities;

import com.mcts.cms.entities.enuns.StatusDeposit;
import com.mcts.cms.entities.services.DepositService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_deposit")
public class Deposit implements DepositService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "initial_deposit_value")
    private BigDecimal initialDepositValue;
    @Column(name = "sale_value")
    private BigDecimal saleValue;
    private StatusDeposit status;
    @Column(name = "deposit_date")
    private LocalDate depositDate;
    private String observations;

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @OneToMany(mappedBy = "deposit")
    private List<Installment> installments = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Override
    public BigDecimal remaining() {
        return saleValue.subtract(initialDepositValue);
    }
}
