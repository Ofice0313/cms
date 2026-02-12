package com.mcts.cms.services;

import com.mcts.cms.dto.DepositSummaryDTO;
import com.mcts.cms.dto.DepositVehicleClientDTO;
import com.mcts.cms.dto.InstallmentDTO;
import com.mcts.cms.entities.*;
import com.mcts.cms.entities.enuns.StatusDeposit;
import com.mcts.cms.entities.enuns.StatusInstallment;
import com.mcts.cms.entities.enuns.StatusVehicle;
import com.mcts.cms.repositories.*;
import com.mcts.cms.services.domain.DepositDomainService;
import com.mcts.cms.services.exceptions.BusinessException;
import com.mcts.cms.services.exceptions.DatabaseException;
import com.mcts.cms.services.exceptions.ResourceNotFoundException;
import com.mcts.cms.services.interfaces.DepositService;
import com.mcts.cms.services.interfaces.InstallmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepositServiceImpl implements DepositService {

    @Autowired
    private InstallmentRepository installmentRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private DepositRepository repository;

    @Autowired
    private VehicleRepository vehicleRepository;

//    @Autowired
//    private InstallmentService installmentService;

    @Autowired
    private DepositDomainService depositDomainService;

    @Transactional(readOnly = true)
    public Page<DepositVehicleClientDTO> findAllPaged(Pageable pageable) {
        Page<Deposit> list = repository.findAll(pageable);
        return list.map(x -> new DepositVehicleClientDTO(x));
    }

    @Transactional(readOnly = true)
    public Page<DepositSummaryDTO> findSummaryByVehicleStatus(StatusVehicle vehicleStatus, Pageable pageable) {
        return repository.findDepositSummary(vehicleStatus, StatusInstallment.PAID, pageable);
    }

    @Transactional(readOnly = true)
    public DepositVehicleClientDTO findById(Long id) {
        Optional<Deposit> obj = repository.findById(id);
        Deposit entity = obj.orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
        return new DepositVehicleClientDTO(entity);
    }

    @Transactional
    public DepositVehicleClientDTO insert(DepositVehicleClientDTO dto) {

        if (dto.getSaleValue() == null || dto.getInitialDepositValue() == null) {
            throw new BusinessException("Sale value and initial deposit value are required");
        }

        if (dto.getSaleValue().compareTo(dto.getInitialDepositValue()) <= 0) {
            throw new BusinessException("Sale value must be greater than initial deposit");
        }

        Deposit entity = new Deposit();
        copyDtoToEntity(dto, entity);

        entity.calculateRemaining();
        entity.updateStatus();

        // Salva primeiro o depósito
        entity = repository.save(entity);

        // Gera parcelas AUTOMATICAMENTE se totalInstallments > 0
        if (entity.getTotalInstallments() != null && entity.getTotalInstallments() > 0) {
            generateInstallmentsForDeposit(entity);

            // Recarrega o depósito com as parcelas
            entity = repository.findById(entity.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Deposit not found after generating installments"));
        }
        return new DepositVehicleClientDTO(entity);
    }

    private void generateInstallmentsForDeposit(Deposit deposit) {
        // Validações
        if (deposit.getTotalInstallments() == null || deposit.getTotalInstallments() <= 0) {
            throw new BusinessException("Total installments must be specified and greater than zero");
        }

        if (deposit.getInstallments() != null && !deposit.getInstallments().isEmpty()) {
            throw new BusinessException("Installments have already been generated for this deposit");
        }

        if (deposit.getRemainingAmount() == null || deposit.getRemainingAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("No remaining amount to generate installments");
        }

        // Usa o serviço de domínio para calcular as parcelas
        List<Installment> installments = depositDomainService.calculateInstallments(deposit);

        // Salva as parcelas
        installmentRepository.saveAll(installments);

        // Atualiza o depósito
        deposit.setInstallments(installments);
        repository.save(deposit);
    }

    @Transactional
    public DepositVehicleClientDTO update(Long id, DepositVehicleClientDTO dto) {
        try {
            Deposit entity = repository.getReferenceById(id);

            // Valida se pode atualizar
            if (entity.getStatus() == StatusDeposit.COMPLETED ||
                    entity.getStatus() == StatusDeposit.CANCELLED) {
                throw new BusinessException("Cannot update a completed or cancelled deposit");
            }

            copyDtoToEntity(dto, entity);

            // Recalcula valores
            entity.calculateRemaining();
            entity.updateStatus();

            entity = repository.save(entity);
            return new DepositVehicleClientDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Resource not found!");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!repository.existsById(id)) {
            throw new ResourceNotFoundException("Resource not found!");
        }

        Deposit deposit = repository.findById(id).orElse(null);
        if (deposit != null && deposit.getStatus() == StatusDeposit.IN_PROGRESS) {
            throw new BusinessException("Cannot delete a deposit in progress");
        }

        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Referential integrity failure");
        }
    }

//    private void copyDtoToEntity(DepositVehicleClientDTO dto, Deposit entity) {
//        entity.setDepositDate(dto.getDepositDate());
//        entity.setObservations(dto.getObservations());
//        entity.setStatus(dto.getStatus());
//        entity.setInitialDepositValue(dto.getInitialDepositValue());
//        entity.setSaleValue(dto.getSaleValue());
//        Client client = clientRepository.getReferenceById(dto.getClient().getId());
//
//        Vehicle vehicle = vehicleRepository.getReferenceById(dto.getVehicle().getId());
//        if(vehicle.getStatus().equals(StatusVehicle.SOLD) || vehicle.getStatus().equals(StatusVehicle.IN_DEPOSIT)) {
//            throw new BusinessException("The vehicle has already been SOLD or IN_DEPOSIT");
//        }
//        entity.setVehicle(vehicle);
//        entity.setClient(client);
//        vehicle.setStatus(StatusVehicle.IN_DEPOSIT);
//    }

    private void copyDtoToEntity(DepositVehicleClientDTO dto, Deposit entity) {
        entity.setDepositDate(dto.getDepositDate());
        entity.setObservations(dto.getObservations());
        entity.setStatus(dto.getStatus());
        entity.setInitialDepositValue(dto.getInitialDepositValue());
        entity.setSaleValue(dto.getSaleValue());

        // Define datas de vencimento
        if (dto.getDueDate() != null) {
            entity.setDueDate(dto.getDueDate());
        } else if (dto.getDepositDate() != null) {
            // Data padrão: 6 meses após o depósito
            entity.setDueDate(dto.getDepositDate().plusMonths(6));
        }

        entity.setTotalInstallments(dto.getTotalInstallments());
        entity.setPaidInstallments(dto.getPaidInstallments() != null ? dto.getPaidInstallments() : 0);

        // Busca cliente e veículo
        Client client = clientRepository.findById(dto.getClient().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        entity.setClient(client);

        Vehicle vehicle = vehicleRepository.findById(dto.getVehicle().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));

        // Valida status do veículo
        if (vehicle.getStatus() == StatusVehicle.SOLD || vehicle.getStatus() == StatusVehicle.IN_DEPOSIT) {
            throw new BusinessException("The vehicle has already been SOLD or is IN_DEPOSIT");
        }

        entity.setVehicle(vehicle);
        vehicle.setStatus(StatusVehicle.IN_DEPOSIT);
    }

    @Override
    public void generateInstallments(Long depositId) {
        Deposit deposit = repository.findById(depositId)
                .orElseThrow(() -> new ResourceNotFoundException("Deposit not found"));

        // Usa o serviço de domínio
        List<Installment> installments = depositDomainService.calculateInstallments(deposit);

        for (Installment installment : installments) {
            deposit.addInstallment(installment);
        }

        repository.save(deposit);
    }

    @Transactional
    public void payInstallment(Long depositId, Integer installmentNumber, LocalDate paymentDate) {
        Deposit deposit = repository.findById(depositId)
                .orElseThrow(() -> new ResourceNotFoundException("Deposit not found"));

        // Encontra a parcela
        Installment installment = deposit.getInstallments().stream()
                .filter(i -> i.getInstallmentNumber().equals(installmentNumber))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Installment not found"));

        // Validações
        if (installment.getStatus() == StatusInstallment.PAID) {
            throw new BusinessException("Installment already paid");
        }

        // Atualiza a parcela
        installment.setStatus(StatusInstallment.PAID);
        installment.setPaymentDate(paymentDate);

        // Atualiza o depósito
        deposit.setPaidInstallments(deposit.getPaidInstallments() + 1);
        deposit.calculateRemaining();
        deposit.updateStatus();

        // Se estiver completamente pago, marca veículo como vendido
        if (deposit.isFullyPaid()) {
            deposit.markAsPaid();
        }

        repository.save(deposit);
        installmentRepository.save(installment);
    }

    @Override
    public List<InstallmentDTO> getInstallmentsByDepositId(Long depositId) {
        List<Installment> installments = installmentRepository.findByDepositIdOrderByInstallmentNumberAsc(depositId);
        return installments.stream()
                .map(InstallmentDTO::new)
                .collect(Collectors.toList());
    }
}
