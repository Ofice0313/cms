package com.mcts.cms.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecoverPasswordDTO {

    @JsonProperty("e_mail")
    private String email;

    @JsonProperty("new_password")
    private String newPassword;

    @JsonProperty("confirmation_password")
    private String confirmationPassword;
}