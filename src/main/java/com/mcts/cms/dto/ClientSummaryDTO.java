package com.mcts.cms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientSummaryDTO {

    private Long id;

    @JsonProperty("client_name")
    private String clientName;

    private String phone;

    private String email;

    @JsonProperty("total_purchases")
    private Long totalPurchases;

    public ClientSummaryDTO(Long id, String firstName, String lastName, String phone, String email, Long totalPurchases) {
        this.id = id;
        this.clientName = buildClientName(firstName, lastName);
        this.phone = phone;
        this.email = email;
        this.totalPurchases = totalPurchases;
    }

    private static String buildClientName(String firstName, String lastName) {
        StringBuilder builder = new StringBuilder();
        if (firstName != null && !firstName.isBlank()) {
            builder.append(firstName.trim());
        }
        if (lastName != null && !lastName.isBlank()) {
            if (!builder.isEmpty()) {
                builder.append(' ');
            }
            builder.append(lastName.trim());
        }
        return builder.toString();
    }
}
