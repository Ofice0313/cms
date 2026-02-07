package com.mcts.cms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mcts.cms.entities.Client;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ClientDTO extends RepresentationModel<ClientDTO> {

    private Long id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String email;
    private String phone;

    public ClientDTO(Client entity) {
        this.id = entity.getId();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.email = entity.getEmail();
        this.phone = entity.getPhone();
    }
}
