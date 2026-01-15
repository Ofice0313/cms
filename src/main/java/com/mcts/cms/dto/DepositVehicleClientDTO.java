package com.mcts.cms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mcts.cms.entities.Deposit;
import com.mcts.cms.entities.Installment;
import com.mcts.cms.entities.enuns.StatusDeposit;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositVehicleClientDTO {

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

    @JsonProperty("due_date")
    private LocalDate dueDate;

    @JsonProperty("total_installments")
    private Integer totalInstallments;

    @JsonProperty("paid_installments")
    private Integer paidInstallments = 0;

    private BigDecimal remainingAmount;

    @JsonProperty("vehicle_id")
    private VehicleDTO vehicle;

    @JsonProperty("client_id")
    private ClientDTO client;

    @JsonProperty("installments")
    private List<InstallmentDTO> installments;

    public DepositVehicleClientDTO(Deposit entity) {
        this.id = entity.getId();
        this.initialDepositValue = entity.getInitialDepositValue();
        this.depositDate = entity.getDepositDate();
        this.saleValue = entity.getSaleValue();
        this.status = entity.getStatus();
        this.observations = entity.getObservations();
        this.dueDate = entity.getDueDate();
        this.totalInstallments = entity.getTotalInstallments();
        this.paidInstallments = entity.getPaidInstallments();
        this.remainingAmount = entity.getRemainingAmount();
        this.vehicle = new VehicleDTO(entity.getVehicle());
        this.client = new ClientDTO(entity.getClient());

        // Inclui as parcelas se existirem
        if (entity.getInstallments() != null && !entity.getInstallments().isEmpty()) {
            this.installments = entity.getInstallments().stream()
                    .map(InstallmentDTO::new)
                    .collect(Collectors.toList());
        }
    }
}
