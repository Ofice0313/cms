package com.mcts.cms.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String phone;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private Deposit deposit;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Sale> buys = new ArrayList<>();
}
