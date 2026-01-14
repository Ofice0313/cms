package com.mcts.cms.services.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDepositRequest {

    private BigDecimal saleValue;
    private BigDecimal initialDepositValue;
    private Integer numberOfInstallments;
    private Long vehicleId;
    private Long clientId;
    private String observations;
    private LocalDate dueDate;
}
