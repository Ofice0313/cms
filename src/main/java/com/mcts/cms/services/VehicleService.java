package com.mcts.cms.services;

import com.mcts.cms.dto.VehicleDTO;
import com.mcts.cms.entities.Vehicle;
import com.mcts.cms.repositories.VehicleRepository;
import com.mcts.cms.services.exceptions.DatabaseException;
import com.mcts.cms.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository repository;

    @Transactional(readOnly = true)
    public Page<VehicleDTO> findAllPaged(Pageable pageable) {
        Page<Vehicle> list = repository.findAll(pageable);
        return list.map(x -> new VehicleDTO(x));
    }

    @Transactional(readOnly = true)
    public VehicleDTO findById(Long id) {
        Optional<Vehicle> obj = repository.findById(id);
        Vehicle entity = obj.orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
        return new VehicleDTO(entity);
    }

    @Transactional
    public VehicleDTO insert(VehicleDTO  dto) {
        Vehicle vehicle = new Vehicle();
        copyDtoToEntity(dto, vehicle);
        vehicle = repository.save(vehicle);
        return new VehicleDTO(vehicle);
    }

    @Transactional
    public VehicleDTO update(Long id, VehicleDTO dto) {
        try {
            Vehicle entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new VehicleDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Resource not found!");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!repository.existsById(id)) {
            throw new ResourceNotFoundException("Resource not found!");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Referential integrity failure");
        }
    }

    private void copyDtoToEntity(VehicleDTO dto, Vehicle vehicle) {
        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());
        vehicle.setYear(dto.getYear());
        vehicle.setAcquisitionDate(dto.getAcquisitionDate());
        vehicle.setRegistrationDate(dto.getRegistrationDate());
        vehicle.setStatus(dto.getStatus());
    }
}
