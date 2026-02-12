package com.mcts.cms.services;

import com.mcts.cms.dto.SaleSummaryDTO;
import com.mcts.cms.dto.SaleVehicleClientDTO;
import com.mcts.cms.entities.*;
import com.mcts.cms.entities.enuns.StatusVehicle;
import com.mcts.cms.repositories.*;
import com.mcts.cms.services.exceptions.BusinessException;
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

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private SaleRepository repository;

    @Transactional(readOnly = true)
    public Page<SaleVehicleClientDTO> findAllPaged(Pageable pageable) {
        Page<Sale> list = repository.findAll(pageable);
        return list.map(x -> new SaleVehicleClientDTO(x));
    }

    @Transactional(readOnly = true)
    public SaleVehicleClientDTO findById(Long id) {
        Optional<Sale> obj = repository.findById(id);
        Sale entity = obj.orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
        return new SaleVehicleClientDTO(entity);
    }

    @Transactional(readOnly = true)
    public BigDecimal totalProfitForSoldVehicles() {
        return repository.sumProfitForSoldVehicles();
    }

    @Transactional(readOnly = true)
    public Page<SaleSummaryDTO> findSaleSummary(Integer year, Integer month, Pageable pageable) {
        return repository.findSaleSummary(year, month, pageable);
    }

    @Transactional
    public SaleVehicleClientDTO insert(SaleVehicleClientDTO dto) {
        Sale entity = new Sale();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new SaleVehicleClientDTO(entity);
    }

    @Transactional
    public SaleVehicleClientDTO update(Long id, SaleVehicleClientDTO dto) {
        try {
            Sale entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new SaleVehicleClientDTO(entity);
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

    private void copyDtoToEntity(SaleVehicleClientDTO dto, Sale entity) {
        entity.setSaleValue(dto.getSaleValue());
        entity.setObservations(dto.getObservations());
        entity.setSaleDate(dto.getSaleDate());

        if (dto.getVehicle().getId() == null || dto.getClient().getId() == null) {
            throw new BusinessException("client_id and vehicle_id are required");
        }

        Client client = clientRepository.getReferenceById(dto.getClient().getId());
        Vehicle vehicle = vehicleRepository.getReferenceById(dto.getVehicle().getId());

        if (vehicle.getStatus() != StatusVehicle.STOCK) {
            boolean sameVehicle = entity.getVehicle() != null
                    && entity.getVehicle().getId() != null
                    && entity.getVehicle().getId().equals(vehicle.getId());
            if (!sameVehicle) {
                throw new BusinessException("Only vehicles with status STOCK can be sold");
            }
        }

        entity.setClient(client);
        entity.setVehicle(vehicle);
        vehicle.setStatus(StatusVehicle.SOLD);
    }
}
