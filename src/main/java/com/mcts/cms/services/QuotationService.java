package com.mcts.cms.services;

import com.mcts.cms.dto.QuotationDTO;
import com.mcts.cms.dto.QuotationSummaryDTO;
import com.mcts.cms.dto.VehicleDTO;
import com.mcts.cms.dto.VehicleStockDTO;
import com.mcts.cms.entities.Client;
import com.mcts.cms.entities.Quotation;
import com.mcts.cms.entities.Vehicle;
import com.mcts.cms.entities.enuns.StatusVehicle;
import com.mcts.cms.repositories.ClientRepository;
import com.mcts.cms.repositories.QuotationRepository;
import com.mcts.cms.repositories.VehicleRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuotationService {

    @Autowired
    private QuotationRepository repository;

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public Page<QuotationDTO> findAllPaged(Pageable pageable) {
        Page<Quotation> list = repository.findAll(pageable);
        return list.map(x -> new QuotationDTO(x));
    }

    @Transactional(readOnly = true)
    public Page<QuotationSummaryDTO> findQuotationSummary(Pageable pageable) {
        return repository.findQuotationSummary(pageable);
    }

    @Transactional(readOnly = true)
    public List<QuotationDTO> findAll() {
        List<Quotation> list = repository.findAll();
        return list.stream().map(x -> new QuotationDTO(x)).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public QuotationDTO findById(Long id) {
        Optional<Quotation> obj = repository.findById(id);
        Quotation entity = obj.orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
        return new QuotationDTO(entity);
    }

    @Transactional(readOnly = true)
    public QuotationDTO findDetails(Long id) {
        return findById(id);
    }

    @Transactional
    public QuotationDTO insert(QuotationDTO  dto) {
        Quotation quotation = new Quotation();
        copyDtoToEntity(dto, quotation);
        quotation = repository.save(quotation);
        return new QuotationDTO(quotation);
    }

    @Transactional
    public QuotationDTO update(Long id, QuotationDTO dto) {
        try {
            Quotation entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new QuotationDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Resource not found!");
        }
    }

    @Transactional
    public QuotationDTO patch(Long id, QuotationDTO dto) {
        try {
            Quotation entity = repository.getReferenceById(id);
            copyNonNullDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new QuotationDTO(entity);
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

    private void copyDtoToEntity(QuotationDTO dto, Quotation quotation) {
        quotation.setBrand(dto.getBrand());
        quotation.setModel(dto.getModel());
        quotation.setYear(dto.getYear());
        quotation.setAcquisitionDate(dto.getAcquisitionDate());
        quotation.setRegistrationDate(dto.getRegistrationDate());
        quotation.setStep(dto.getStep());
        quotation.setDriver(dto.getDriver());
        quotation.setInnater(dto.getInnater());
        quotation.setCp(dto.getCp());
        quotation.setInspection(dto.getInspection());
        quotation.setLoading(dto.getLoading());
        quotation.setRights(dto.getRights());
        quotation.setLicensePlate(dto.getLicensePlate());
        quotation.setCustomsBroker(dto.getCustomsBroker());
        quotation.setOrderDate(dto.getOrderDate());
        quotation.setPurchaseValue(dto.getPurchaseValue());
        quotation.setHotel(dto.getHotel());
        quotation.setFood(dto.getFood());
        quotation.setFuel(dto.getFuel());
        quotation.setSaleValue(dto.getSaleValue());
        quotation.setDiversos(dto.getDiversos());
        quotation.setObservations(dto.getObservations());
        if (dto.getClient().getId() == null) {
            throw new BusinessException("client_id is required");
        }
        Client client = clientRepository.getReferenceById(dto.getClient().getId());
        quotation.setClient(client);
    }

    private String normalizeFilter(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }

    private void copyNonNullDtoToEntity(QuotationDTO dto, Quotation quotation) {
        if (dto.getBrand() != null) {
            quotation.setBrand(dto.getBrand());
        }
        if (dto.getModel() != null) {
            quotation.setModel(dto.getModel());
        }
        if (dto.getYear() != null) {
            quotation.setYear(dto.getYear());
        }
        if (dto.getAcquisitionDate() != null) {
            quotation.setAcquisitionDate(dto.getAcquisitionDate());
        }
        if (dto.getRegistrationDate() != null) {
            quotation.setRegistrationDate(dto.getRegistrationDate());
        }
        if (dto.getStep() != null) {
            quotation.setStep(dto.getStep());
        }
        if (dto.getDriver() != null) {
            quotation.setDriver(dto.getDriver());
        }
        if (dto.getInnater() != null) {
            quotation.setInnater(dto.getInnater());
        }
        if (dto.getCp() != null) {
            quotation.setCp(dto.getCp());
        }
        if (dto.getInspection() != null) {
            quotation.setInspection(dto.getInspection());
        }
        if (dto.getLoading() != null) {
            quotation.setLoading(dto.getLoading());
        }
        if (dto.getRights() != null) {
            quotation.setRights(dto.getRights());
        }
        if (dto.getLicensePlate() != null) {
            quotation.setLicensePlate(dto.getLicensePlate());
        }
        if (dto.getCustomsBroker() != null) {
            quotation.setCustomsBroker(dto.getCustomsBroker());
        }
        if (dto.getOrderDate() != null) {
            quotation.setOrderDate(dto.getOrderDate());
        }
        if (dto.getPurchaseValue() != null) {
            quotation.setPurchaseValue(dto.getPurchaseValue());
        }
        if (dto.getHotel() != null) {
            quotation.setHotel(dto.getHotel());
        }
        if (dto.getFood() != null) {
            quotation.setFood(dto.getFood());
        }
        if (dto.getFuel() != null) {
            quotation.setFuel(dto.getFuel());
        }
        if (dto.getSaleValue() != null) {
            quotation.setSaleValue(dto.getSaleValue());
        }
        if (dto.getDiversos() != null) {
            quotation.setDiversos(dto.getDiversos());
        }
        if (dto.getObservations() != null) {
            quotation.setObservations(dto.getObservations());
        }
    }
}
