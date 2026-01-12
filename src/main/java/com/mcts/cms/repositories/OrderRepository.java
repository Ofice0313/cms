package com.mcts.cms.repositories;

import com.mcts.cms.entities.Order;
import com.mcts.cms.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
