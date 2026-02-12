package com.mcts.cms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleSummaryDTO {

    private String vehicle;

    @JsonProperty("client_name")
    private String clientName;

    @JsonProperty("sale_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate saleDate;

    private BigDecimal investment;

    @JsonProperty("sale_value")
    private BigDecimal saleValue;

    private BigDecimal profit;

    @JsonProperty("margin_percent")
    private BigDecimal marginPercent;

    public SaleSummaryDTO(String brand,
                          String model,
                          Integer manufacturedYear,
                          String clientName,
                          LocalDate saleDate,
                          BigDecimal investment,
                          BigDecimal saleValue,
                          BigDecimal profit,
                          BigDecimal marginPercent) {
        this.vehicle = buildVehicleName(brand, model, manufacturedYear);
        this.clientName = clientName;
        this.saleDate = saleDate;
        this.investment = investment;
        this.saleValue = saleValue;
        this.profit = profit;
        this.marginPercent = marginPercent;
    }

    private static String buildVehicleName(String brand, String model, Integer year) {
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
}