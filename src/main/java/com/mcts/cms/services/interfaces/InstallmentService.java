package com.mcts.cms.services.interfaces;

import com.mcts.cms.dto.InstallmentDTO;
import com.mcts.cms.entities.Deposit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface InstallmentService {

    InstallmentDTO payInstallment(Long id, LocalDate paymentDate);
    List<InstallmentDTO> findByDepositId(Long depositId);
    void generateInstallments(Deposit deposit);
    BigDecimal calculateInstallmentValue(BigDecimal totalAmount, Integer totalInstallments);
    List<String> notifyDueInstallments();
}
