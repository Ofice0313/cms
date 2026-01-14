package com.mcts.cms.services.summaries;

import com.mcts.cms.entities.enuns.StatusDeposit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositSummary {

    private Long depositId;
    private BigDecimal saleValue;
    private BigDecimal initialDeposit;
    private BigDecimal remainingAmount;
    private BigDecimal totalPaid;
    private StatusDeposit status;
    private Integer paidInstallments;
    private Integer totalInstallments;
    private List<InstallmentSummary> installments;
}
