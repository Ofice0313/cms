package com.mcts.cms.dto.security;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountCredentialsDTO {

    private String userName;
    private String password;
}
