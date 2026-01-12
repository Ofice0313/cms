package com.mcts.cms.repositories;

import com.mcts.cms.entities.Acquisition;
import com.mcts.cms.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcquisitionRepository extends JpaRepository<Acquisition, Long> {
}
