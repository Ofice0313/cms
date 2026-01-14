package com.mcts.cms.entities.services;

import java.math.BigDecimal;

public interface DepositService {
    void calculateRemaining();
    BigDecimal getRemaining();
    BigDecimal getTotalPaid();
    boolean isFullyPaid();
    void updateStatus();
}
