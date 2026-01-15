package com.mcts.cms.dto.response;

import com.mcts.cms.dto.DepositVehicleClientDTO;
import com.mcts.cms.dto.InstallmentDTO;
import com.mcts.cms.entities.enuns.StatusDeposit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {

    private InstallmentDTO installment;
    private DepositVehicleClientDTO deposit;
    private BigDecimal newRemainingAmount;
    private BigDecimal totalPaid;
    private boolean depositCompleted;

    public PaymentResponseDTO(InstallmentDTO installment, DepositVehicleClientDTO deposit) {
        this.installment = installment;
        this.deposit = deposit;
        this.newRemainingAmount = deposit.getRemainingAmount();
        this.totalPaid = deposit.getInitialDepositValue()
                .add(installment.getValuePerInstallment());
        this.depositCompleted = deposit.getStatus() == StatusDeposit.COMPLETED;
    }
}
