package com.mcts.cms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mcts.cms.entities.Client;
import com.mcts.cms.entities.Deposit;
import com.mcts.cms.entities.Installment;
import com.mcts.cms.entities.Vehicle;
import com.mcts.cms.entities.enuns.StatusDeposit;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositDTO {

    private Long id;
    @JsonProperty("initial_deposit_value")
    private BigDecimal initialDepositValue;
    @JsonProperty("sale_value")
    private BigDecimal saleValue;
    private StatusDeposit status;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("deposit_date")
    private LocalDate depositDate;
    private String observations;

    @JsonProperty("vehicle_id")
    private Long vehicle;

    private List<InstallmentDTO> installments = new ArrayList<>();

    @JsonProperty("client_id")
    private Long client;

    public DepositDTO(Deposit entity) {
        this.id = entity.getId();
        this.initialDepositValue = entity.getInitialDepositValue();
        this.depositDate = entity.getDepositDate();
        this.saleValue = entity.getSaleValue();
        this.status = entity.getStatus();
        this.observations = entity.getObservations();
        this.vehicle = entity.getVehicle().getId();
        this.client = entity.getClient().getId();

        for (Installment installment: entity.getInstallments()) {
            installments.add(new InstallmentDTO(installment));
        }
    }
}
