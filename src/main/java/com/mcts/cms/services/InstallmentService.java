package com.mcts.cms.services;

import com.mcts.cms.dto.InstallmentDTO;
import com.mcts.cms.entities.Installment;
import com.mcts.cms.repositories.InstallmentRepository;
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
public class InstallmentService {

    @Autowired
    private InstallmentRepository repository;

    @Transactional(readOnly = true)
    public Page<InstallmentDTO> findAllPaged(Pageable pageable) {
        Page<Installment> list = repository.findAll(pageable);
        return list.map(x -> new InstallmentDTO(x));
    }

    @Transactional(readOnly = true)
    public InstallmentDTO findById(Long id) {
        Optional<Installment> obj = repository.findById(id);
        Installment entity = obj.orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
        return new InstallmentDTO(entity);
    }

    @Transactional
    public InstallmentDTO insert(InstallmentDTO  dto) {
        Installment installment = new Installment();
        copyDtoToEntity(dto, installment);
        installment = repository.save(installment);
        return new InstallmentDTO(installment);
    }

    @Transactional
    public InstallmentDTO update(Long id, InstallmentDTO dto) {
        try {
            Installment entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new InstallmentDTO(entity);
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
//
    private void copyDtoToEntity(InstallmentDTO dto, Installment entity) {
        entity.setInstallmentNumber(dto.getInstallmentNumber());
        entity.setStatus(dto.getStatus());
        entity.setPaymentDate(dto.getPaymentDate());
        entity.setValuePerInstallment(dto.getValuePerInstallment());
    }
}
