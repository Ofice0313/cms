package com.mcts.cms.repositories;

import com.mcts.cms.dto.ClientSummaryDTO;
import com.mcts.cms.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.buys s LEFT JOIN FETCH s.vehicle WHERE c.id = :id")
    Optional<Client> findByIdWithSalesAndVehicles(Long id);

    @Query("SELECT new com.mcts.cms.dto.ClientSummaryDTO(c.id, c.firstName, c.lastName, c.phone, c.email, COUNT(s)) " +
           "FROM Client c LEFT JOIN c.buys s GROUP BY c.id, c.firstName, c.lastName, c.phone, c.email")
    Page<ClientSummaryDTO> findClientSummary(Pageable pageable);
}
