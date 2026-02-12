package com.mcts.cms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mcts.cms.entities.enuns.StatusDeposit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositSummaryDTO {

    @JsonProperty("deposit_id")
    private Long depositId;

    @JsonProperty("client")
    private String client;

    @JsonProperty("vehicle")
    private String vehicle;

    @JsonProperty("sale_value")
    private BigDecimal saleValue;

    @JsonProperty("remaining_value")
    private BigDecimal remainingValue;

    @JsonProperty("installment")
    private String installment;

    @JsonProperty("next_due_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate nextDueDate;

    private StatusDeposit status;

    public DepositSummaryDTO(Long depositId,
                             String clientName,
                             String clientPhone,
                             String brand,
                             String model,
                             Integer manufacturedYear,
                             BigDecimal saleValue,
                             BigDecimal remainingValue,
                             Integer totalInstallments,
                             Integer paidInstallments,
                             LocalDate nextDueDate,
                             StatusDeposit status) {
        this.depositId = depositId;
        this.client = buildClient(clientName, clientPhone);
        this.vehicle = buildVehicle(brand, model, manufacturedYear);
        this.saleValue = saleValue;
        this.remainingValue = remainingValue;
        this.installment = buildInstallment(totalInstallments, paidInstallments);
        this.nextDueDate = nextDueDate;
        this.status = status;
    }

    private static String buildClient(String name, String phone) {
        StringBuilder builder = new StringBuilder();
        if (name != null && !name.isBlank()) {
            builder.append(name.trim());
        }
        if (phone != null && !phone.isBlank()) {
            if (!builder.isEmpty()) {
                builder.append(' ');
            }
            builder.append(phone.trim());
        }
        return builder.toString();
    }

    private static String buildVehicle(String brand, String model, Integer year) {
        StringBuilder builder = new StringBuilder();
        if (brand != null && !brand.isBlank()) {
            builder.append(brand.trim());
        }
        if (model != null && !model.isBlank()) {
            if (!builder.isEmpty()) {
                builder.append(' ');
            }
            builder.append(model.trim());
        }
        if (year != null) {
            if (!builder.isEmpty()) {
                builder.append(' ');
            }
            builder.append(year);
        }
        return builder.toString();
    }

    private static String buildInstallment(Integer total, Integer paid) {
        int totalValue = total == null ? 0 : total;
        int paidValue = paid == null ? 0 : paid;
        return paidValue + "/" + totalValue;
    }
}