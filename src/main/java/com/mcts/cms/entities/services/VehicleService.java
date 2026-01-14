package com.mcts.cms.entities.services;

import java.math.BigDecimal;

public interface VehicleService {

    BigDecimal getTotalInvestment();
    BigDecimal getSubTotalAcquisition();
    BigDecimal getSubTotalOrder();
}
