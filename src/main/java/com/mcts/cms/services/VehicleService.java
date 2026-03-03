package com.mcts.cms.services;

import com.mcts.cms.dto.VehicleDTO;
import com.mcts.cms.dto.VehicleStockDTO;
import com.mcts.cms.entities.Vehicle;
import com.mcts.cms.entities.enuns.StatusVehicle;
import com.mcts.cms.entities.enuns.Step;
import com.mcts.cms.repositories.VehicleRepository;
import com.mcts.cms.services.exceptions.DatabaseException;
import com.mcts.cms.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Page<VehicleDTO> searchByBrandOrModel(String brand, String model, Pageable pageable) {
        String normalizedBrand = normalizeFilter(brand);
        String normalizedModel = normalizeFilter(model);

        Specification<Vehicle> specification = Specification.where(null);

        if (normalizedBrand != null) {
            specification = specification.and((root, query, cb) -> cb.like(
                    cb.lower(root.get("brand")),
                    "%" + normalizedBrand.toLowerCase() + "%"
            ));
        }

        if (normalizedModel != null) {
            specification = specification.and((root, query, cb) -> cb.like(
                    cb.lower(root.get("model")),
                    "%" + normalizedModel.toLowerCase() + "%"
            ));
        }

        Page<Vehicle> list = repository.findAll(specification, pageable);
        return list.map(VehicleDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<VehicleDTO> searchByStep(Step step, Pageable pageable) {
        Page<Vehicle> list = repository.findByStep(step, pageable);
        return list.map(VehicleDTO::new);
    }

    @Transactional(readOnly = true)
    public List<VehicleDTO> findAll() {
        List<Vehicle> list = repository.findAll();
        return list.stream().map(x -> new VehicleDTO(x)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<VehicleStockDTO> findAllStockVehicles(Pageable pageable) {
        return repository.findByStatus(StatusVehicle.STOCK, pageable);
    }

    @Transactional(readOnly = true)
    public Page<VehicleStockDTO> findAllVehiclesByStep(Step step, Pageable pageable) {
        return repository.findByStepSummary(step, pageable);
    }


    @Transactional(readOnly = true)
    public VehicleDTO findById(Long id) {
        Optional<Vehicle> obj = repository.findById(id);
        Vehicle entity = obj.orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
        return new VehicleDTO(entity);
    }

    @Transactional(readOnly = true)
    public VehicleDTO findDetails(Long id) {
        return findById(id);
    }

    @Transactional(readOnly = true)
    public long countStockVehicles() {
        return repository.countStockVehicles();
    }

    @Transactional(readOnly = true)
    public long countSoldVehicles() {
        return repository.countSoldVehicles();
    }

    @Transactional(readOnly = true)
    public Page<VehicleStockDTO> findSoldVehicles(Pageable pageable) {
        return repository.findByStatus(StatusVehicle.SOLD, pageable);
    }

    @Transactional(readOnly = true)
    public BigDecimal totalInvestment() {
        return repository.sumTotalInvestment();
    }

    @Transactional(readOnly = true)
    public BigDecimal totalInvestmentByStatus(StatusVehicle status) {
        return repository.sumTotalInvestmentByStatus(status);
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

    @Transactional
    public VehicleDTO patch(Long id, VehicleDTO dto) {
        try {
            Vehicle entity = repository.getReferenceById(id);
            copyNonNullDtoToEntity(dto, entity);
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
        vehicle.setDriver(dto.getDriver());
        vehicle.setInnater(dto.getInnater());
        vehicle.setCp(dto.getCp());
        vehicle.setInspection(dto.getInspection());
        vehicle.setLoading(dto.getLoading());
        vehicle.setRights(dto.getRights());
        vehicle.setLicensePlate(dto.getLicensePlate());
        vehicle.setCustomsBroker(dto.getCustomsBroker());
        vehicle.setOrderDate(dto.getOrderDate());
        vehicle.setPurchaseValue(dto.getPurchaseValue());
        vehicle.setHotel(dto.getHotel());
        vehicle.setFood(dto.getFood());
        vehicle.setFuel(dto.getFuel());
        vehicle.setStep(dto.getStep());
        vehicle.setDiversos(dto.getDiversos());
        vehicle.setObservations(dto.getObservations());
    }

    private String normalizeFilter(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }

    private void copyNonNullDtoToEntity(VehicleDTO dto, Vehicle vehicle) {
        if (dto.getBrand() != null) {
            vehicle.setBrand(dto.getBrand());
        }
        if (dto.getModel() != null) {
            vehicle.setModel(dto.getModel());
        }
        if (dto.getYear() != null) {
            vehicle.setYear(dto.getYear());
        }
        if (dto.getAcquisitionDate() != null) {
            vehicle.setAcquisitionDate(dto.getAcquisitionDate());
        }
        if (dto.getRegistrationDate() != null) {
            vehicle.setRegistrationDate(dto.getRegistrationDate());
        }
        if (dto.getStatus() != null) {
            vehicle.setStatus(dto.getStatus());
        }
        if (dto.getDriver() != null) {
            vehicle.setDriver(dto.getDriver());
        }
        if (dto.getInnater() != null) {
            vehicle.setInnater(dto.getInnater());
        }
        if (dto.getCp() != null) {
            vehicle.setCp(dto.getCp());
        }
        if (dto.getInspection() != null) {
            vehicle.setInspection(dto.getInspection());
        }
        if (dto.getLoading() != null) {
            vehicle.setLoading(dto.getLoading());
        }
        if (dto.getRights() != null) {
            vehicle.setRights(dto.getRights());
        }
        if (dto.getLicensePlate() != null) {
            vehicle.setLicensePlate(dto.getLicensePlate());
        }
        if (dto.getCustomsBroker() != null) {
            vehicle.setCustomsBroker(dto.getCustomsBroker());
        }
        if (dto.getOrderDate() != null) {
            vehicle.setOrderDate(dto.getOrderDate());
        }
        if (dto.getPurchaseValue() != null) {
            vehicle.setPurchaseValue(dto.getPurchaseValue());
        }
        if (dto.getHotel() != null) {
            vehicle.setHotel(dto.getHotel());
        }
        if (dto.getFood() != null) {
            vehicle.setFood(dto.getFood());
        }
        if (dto.getFuel() != null) {
            vehicle.setFuel(dto.getFuel());
        }
        if (dto.getStep() != null) {
            vehicle.setStep(dto.getStep());
        }
        if (dto.getDiversos() != null) {
            vehicle.setDiversos(dto.getDiversos());
        }
        if (dto.getObservations() != null) {
            vehicle.setObservations(dto.getObservations());
        }
    }
}
