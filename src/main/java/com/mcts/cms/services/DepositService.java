package com.mcts.cms.services;

import com.mcts.cms.dto.DepositVehicleInstallmentClientDTO;
import com.mcts.cms.dto.InstallmentDTO;
import com.mcts.cms.entities.*;
import com.mcts.cms.repositories.*;
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
public class DepositService {


    @Autowired
    private InstallmentRepository installmentRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private DepositRepository repository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Transactional(readOnly = true)
    public Page<DepositVehicleInstallmentClientDTO> findAllPaged(Pageable pageable) {
        Page<Deposit> list = repository.findAll(pageable);
        return list.map(x -> new DepositVehicleInstallmentClientDTO(x));
    }

    @Transactional(readOnly = true)
    public DepositVehicleInstallmentClientDTO findById(Long id) {
        Optional<Deposit> obj = repository.findById(id);
        Deposit entity = obj.orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
        return new DepositVehicleInstallmentClientDTO(entity);
    }

    @Transactional
    public DepositVehicleInstallmentClientDTO insert(DepositVehicleInstallmentClientDTO dto) {
        Deposit entity = new Deposit();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new DepositVehicleInstallmentClientDTO(entity);
    }

    @Transactional
    public DepositVehicleInstallmentClientDTO update(Long id, DepositVehicleInstallmentClientDTO dto) {
        try {
            Deposit entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new DepositVehicleInstallmentClientDTO(entity);
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

    private void copyDtoToEntity(DepositVehicleInstallmentClientDTO dto, Deposit entity) {
        entity.setDepositDate(dto.getDepositDate());
        entity.setObservations(dto.getObservations());
        entity.setStatus(dto.getStatus());
        entity.setInitialDepositValue(dto.getInitialDepositValue());
        entity.setSaleValue(dto.getSaleValue());
        Client client = clientRepository.getReferenceById(dto.getClient().getId());
        for (InstallmentDTO installmentDTO: dto.getInstallments()) {
            Installment installment = installmentRepository.getReferenceById(installmentDTO.getId());
            entity.getInstallments().add(installment);
        }
        Vehicle vehicle = vehicleRepository.getReferenceById(dto.getVehicle().getId());
        entity.setVehicle(vehicle);
        entity.setClient(client);
    }
}
