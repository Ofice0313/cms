package com.mcts.cms.services;

import com.mcts.cms.dto.OrderAcquisitionVehicleDTO;
import com.mcts.cms.dto.SaleOrderClientDTO;
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
public class SaleService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SaleRepository repository;

    @Transactional(readOnly = true)
    public Page<SaleOrderClientDTO> findAllPaged(Pageable pageable) {
        Page<Sale> list = repository.findAll(pageable);
        return list.map(x -> new SaleOrderClientDTO(x));
    }

    @Transactional(readOnly = true)
    public SaleOrderClientDTO findById(Long id) {
        Optional<Sale> obj = repository.findById(id);
        Sale entity = obj.orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
        return new SaleOrderClientDTO(entity);
    }

    @Transactional
    public SaleOrderClientDTO insert(SaleOrderClientDTO dto) {
        Sale entity = new Sale();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new SaleOrderClientDTO(entity);
    }

    @Transactional
    public SaleOrderClientDTO update(Long id, SaleOrderClientDTO dto) {
        try {
            Sale entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new SaleOrderClientDTO(entity);
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

    private void copyDtoToEntity(SaleOrderClientDTO dto, Sale entity) {
        entity.setSaleValue(dto.getSaleValue());
        entity.setObservations(dto.getObservations());
        entity.setSaleDate(dto.getSaleDate());
        Client client = clientRepository.getReferenceById(dto.getClient().getId());
        Order order = orderRepository.getReferenceById(dto.getOrder().getId());
        entity.setOrder(order);
        entity.setClient(client);
    }
}
