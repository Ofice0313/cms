package com.mcts.cms.entities;

import com.mcts.cms.entities.enuns.StatusInstallment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_installment")
public class Installment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "installment_number")
    private Integer installmentNumber;
    @Column(name = "value_per_installment")
    private BigDecimal valuePerInstallment;
    @Column(name = "payment_date")
    private LocalDate paymentDate;
    private StatusInstallment status;

    @ManyToOne
    @JoinColumn(name = "deposit_id")
    private Deposit deposit;
}
