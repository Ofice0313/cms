package com.mcts.cms.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountCredentialsDTO {

    @JsonProperty("username")
    private String userName;
    private String password;
}
