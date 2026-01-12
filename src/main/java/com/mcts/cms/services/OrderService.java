package com.mcts.cms.services;

import com.mcts.cms.dto.OrderAcquisitionVehicleDTO;
import com.mcts.cms.entities.Acquisition;
import com.mcts.cms.entities.Order;
import com.mcts.cms.entities.Vehicle;
import com.mcts.cms.repositories.AcquisitionRepository;
import com.mcts.cms.repositories.OrderRepository;
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
public class OrderService {


    @Autowired
    private OrderRepository repository;

    @Autowired
    private AcquisitionRepository acquisitionRepository;

    private VehicleRepository vehicleRepository;

    @Transactional(readOnly = true)
    public Page<OrderAcquisitionVehicleDTO> findAllPaged(Pageable pageable) {
        Page<Order> list = repository.findAll(pageable);
        return list.map(x -> new OrderAcquisitionVehicleDTO(x));
    }

    @Transactional(readOnly = true)
    public OrderAcquisitionVehicleDTO findById(Long id) {
        Optional<Order> obj = repository.findById(id);
        Order entity = obj.orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
        return new OrderAcquisitionVehicleDTO(entity);
    }

    @Transactional
    public OrderAcquisitionVehicleDTO insert(OrderAcquisitionVehicleDTO dto) {
        Order entity = new Order();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new OrderAcquisitionVehicleDTO(entity);
    }

    @Transactional
    public OrderAcquisitionVehicleDTO update(Long id, OrderAcquisitionVehicleDTO dto) {
        try {
            Order entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new OrderAcquisitionVehicleDTO(entity);
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

    private void copyDtoToEntity(OrderAcquisitionVehicleDTO dto, Order entity) {
        entity.setCp(dto.getCp());
        entity.setInnater(dto.getInnater());
        entity.setDriver(dto.getDriver());
        entity.setInspection(dto.getInspection());
        entity.setLoading(dto.getLoading());
        entity.setRights(dto.getRights());
        entity.setCustomsBroker(dto.getCustomsBroker());
        entity.setLicensePlate(dto.getLicensePlate());
        entity.setOrderDate(dto.getOrderDate());
        Acquisition acquisition = acquisitionRepository.getReferenceById(dto.getAcquisition().getId());
        Vehicle vehicle = vehicleRepository.getReferenceById(dto.getVehicle().getId());
        entity.setVehicle(vehicle);
        entity.setAcquisition(acquisition);
    }
}
