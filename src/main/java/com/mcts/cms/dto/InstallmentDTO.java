package com.mcts.cms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mcts.cms.entities.Installment;
import com.mcts.cms.entities.enuns.StatusInstallment;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstallmentDTO {

    private Long id;
    @JsonProperty("installment_number")
    private Integer installmentNumber;
    @JsonProperty("value_per_installment")
    private BigDecimal valuePerInstallment;
    @JsonProperty("payment_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate paymentDate;
    @JsonProperty("due_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dueDate;
    private StatusInstallment status;

    private String observations;

    public InstallmentDTO(Installment entity) {
        this.id = entity.getId();
        this.installmentNumber = entity.getInstallmentNumber();
        this.paymentDate = entity.getPaymentDate();
        this.dueDate = entity.getDueDate();
        this.valuePerInstallment = entity.getValuePerInstallment();
        this.status = entity.getStatus();
        this.observations = entity.getObservations();
    }
}
