package com.mcts.cms.services.interfaces;

import com.mcts.cms.dto.InstallmentDTO;

import java.math.BigDecimal;
import java.util.List;

public interface DepositService {
    void generateInstallments(Long depositId);
    List<InstallmentDTO> getInstallmentsByDepositId(Long depositId);
}
