package com.mcts.cms.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCredentialsDTO {

    @JsonProperty("username")
    private String userName;
    private String password;
    @JsonProperty("fullname")
    private String fullName;
}
