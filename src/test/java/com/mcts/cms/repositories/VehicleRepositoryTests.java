package com.mcts.cms.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class VehicleRepositoryTests {

    @Autowired
    private VehicleRepository repository;

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){
        repository.deleteById(1L);
    }
}
