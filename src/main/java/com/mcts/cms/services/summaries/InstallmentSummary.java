package com.mcts.cms.services.summaries;

import com.mcts.cms.entities.enuns.StatusInstallment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstallmentSummary {

    private Integer installmentNumber;
    private BigDecimal value;
    private LocalDate dueDate;
    private StatusInstallment status;

}
