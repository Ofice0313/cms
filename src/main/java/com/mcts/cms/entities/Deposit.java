package com.mcts.cms.entities;

import com.mcts.cms.entities.enuns.StatusDeposit;
import com.mcts.cms.entities.enuns.StatusInstallment;
import com.mcts.cms.entities.services.DepositService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "total_installments")
    private Integer totalInstallments;

    @Column(name = "paid_installments")
    private Integer paidInstallments = 0;

    @Column(name = "remaining_amount")
    private BigDecimal remainingAmount;

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    @MapsId
    private Vehicle vehicle;

    @OneToMany(mappedBy = "deposit")
    @OrderBy("installmentNumber ASC")
    private List<Installment> installments = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Override
    @PrePersist
    @PreUpdate
    public void calculateRemaining() {
        this.remainingAmount = saleValue.subtract(initialDepositValue);
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public BigDecimal getRemaining() {
        if (remainingAmount == null) {
            calculateRemaining();
        }
        return remainingAmount;
    }

    @Override
    public BigDecimal getTotalPaid() {
        BigDecimal total = initialDepositValue;
        if (installments != null) {
            for (Installment installment : installments) {
                if (installment.getStatus() == StatusInstallment.PAID) {
                    total = total.add(installment.getValuePerInstallment());
                }
            }
        }
        return total;
    }

    @Override
    public boolean isFullyPaid() {
        return getTotalPaid().compareTo(saleValue) >= 0;
    }

    @Override
    public void updateStatus() {
        if (isFullyPaid()) {
            this.status = StatusDeposit.COMPLETED;
        } else if (paidInstallments > 0) {
            this.status = StatusDeposit.IN_PROGRESS;
        } else {
            this.status = StatusDeposit.PENDING;
        }
    }
}
