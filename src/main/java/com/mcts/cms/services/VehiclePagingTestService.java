package com.mcts.cms.services;

import com.mcts.cms.dto.VehicleDTO;
import com.mcts.cms.entities.Vehicle;
import com.mcts.cms.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehiclePagingTestService {

    @Autowired
    private VehicleRepository repository;

    @Transactional(readOnly = true)
    public Page<VehicleDTO> findAllPagedTest(Pageable pageable) {
        Page<Vehicle> list = repository.findAll(pageable);
        return list.map(VehicleDTO::new);
    }
}
