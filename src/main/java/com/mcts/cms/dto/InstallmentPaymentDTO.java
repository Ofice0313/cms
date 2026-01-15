package com.mcts.cms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstallmentPaymentDTO {

    @NotNull(message = "Payment date is required")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("payment_date")
    private LocalDate paymentDate;

    @Positive(message = "Payment value must be positive")
    @JsonProperty("payment_value")
    private BigDecimal paymentValue;

    @Size(max = 500, message = "Observations must not exceed 500 characters")
    private String observations;
}
