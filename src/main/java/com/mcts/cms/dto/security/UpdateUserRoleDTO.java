package com.mcts.cms.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRoleDTO {

    @JsonProperty("e_mail")
    private String email;

    private String role;
}