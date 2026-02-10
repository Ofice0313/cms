package com.mcts.cms.dto.security;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"full_name", "e_mail", "phone_number"})
public class AccountCredentialsDTO {

    @JsonProperty("full_name")
    @JsonAlias({"full_name", "fullName", "fullname"})
    private String fullName;
    @JsonProperty("e_mail")
    @JsonAlias({"e_mail", "email", "username"})
    private String email;
    @JsonProperty("phone_number")
    @JsonAlias({"phone_number", "phoneNumber", "phonenumber"})
    private String phoneNumber;
    private String password;
    @JsonProperty("confirmation_password")
    private String confirmationPassword;
}
