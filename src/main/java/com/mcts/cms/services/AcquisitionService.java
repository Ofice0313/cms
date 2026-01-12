package com.mcts.cms.services;

import com.mcts.cms.dto.AcquisitionVehicleDTO;
import com.mcts.cms.entities.Acquisition;
import com.mcts.cms.entities.Vehicle;
import com.mcts.cms.repositories.AcquisitionRepository;
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
public class AcquisitionService {

    @Autowired
    private AcquisitionRepository acquisitionRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Transactional(readOnly = true)
    public Page<AcquisitionVehicleDTO> findAllPaged(Pageable pageable) {
        Page<Acquisition> list = acquisitionRepository.findAll(pageable);
        return list.map(x -> new AcquisitionVehicleDTO(x));
    }

    @Transactional(readOnly = true)
    public AcquisitionVehicleDTO findById(Long id) {
        Optional<Acquisition> obj = acquisitionRepository.findById(id);
        Acquisition entity = obj.orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
        return new AcquisitionVehicleDTO(entity);
    }

    @Transactional
    public AcquisitionVehicleDTO insert(AcquisitionVehicleDTO dto) {
        Acquisition entity = new Acquisition();
        copyDtoToEntity(dto, entity);
        entity = acquisitionRepository.save(entity);
        return new AcquisitionVehicleDTO(entity);
    }

    @Transactional
    public AcquisitionVehicleDTO update(Long id, AcquisitionVehicleDTO dto) {
        try {
            Acquisition entity = acquisitionRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = acquisitionRepository.save(entity);
            return new AcquisitionVehicleDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Resource not found!");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!acquisitionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Resource not found!");
        }
        try {
            acquisitionRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Referential integrity failure");
        }
    }

    private void copyDtoToEntity(AcquisitionVehicleDTO dto, Acquisition acquisition) {
        acquisition.setPurchaseValue(dto.getPurchaseValue());
        acquisition.setTravel(dto.getTravel());
        Vehicle vehicle = vehicleRepository.getReferenceById(dto.getVehicleDTO().getId());
        acquisition.setVehicle(vehicle);
    }
}
