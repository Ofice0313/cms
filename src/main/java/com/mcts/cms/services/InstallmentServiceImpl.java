package com.mcts.cms.services;

import com.mcts.cms.dto.InstallmentDTO;
import com.mcts.cms.entities.Deposit;
import com.mcts.cms.entities.Installment;
import com.mcts.cms.entities.Vehicle;
import com.mcts.cms.entities.enuns.StatusDeposit;
import com.mcts.cms.entities.enuns.StatusInstallment;
import com.mcts.cms.entities.enuns.StatusVehicle;
import com.mcts.cms.repositories.DepositRepository;
import com.mcts.cms.repositories.InstallmentRepository;
import com.mcts.cms.services.domain.DepositDomainService;
import com.mcts.cms.services.exceptions.BusinessException;
import com.mcts.cms.services.exceptions.DatabaseException;
import com.mcts.cms.services.exceptions.ResourceNotFoundException;
import com.mcts.cms.services.interfaces.InstallmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstallmentServiceImpl implements InstallmentService {

    @Autowired
    private InstallmentRepository repository;

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private DepositDomainService depositDomainService;

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

    @Transactional
    public InstallmentDTO patch(Long id, InstallmentDTO dto) {
        try {
            Installment entity = repository.getReferenceById(id);

            if (dto.getInstallmentNumber() != null) {
                entity.setInstallmentNumber(dto.getInstallmentNumber());
            }
            if (dto.getStatus() != null) {
                entity.setStatus(dto.getStatus());
            }
            if (dto.getPaymentDate() != null) {
                entity.setPaymentDate(dto.getPaymentDate());
            }
            if (dto.getDueDate() != null) {
                entity.setDueDate(dto.getDueDate());
            }
            if (dto.getValuePerInstallment() != null) {
                entity.setValuePerInstallment(dto.getValuePerInstallment());
            }
            if (dto.getObservations() != null) {
                entity.setObservations(dto.getObservations());
            }

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
        entity.setDueDate(dto.getDueDate());
        entity.setValuePerInstallment(dto.getValuePerInstallment());
        entity.setObservations(dto.getObservations());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> notifyDueInstallments() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Installment> dueInstallments = repository.findByDueDateAndStatus(today, StatusInstallment.PENDING);

        return dueInstallments.stream()
                .map(installment -> "Parcela " + installment.getInstallmentNumber()
                        + " do depósito " + installment.getDeposit().getId()
                        + " vence hoje (" + installment.getDueDate().format(formatter) + ").")
                .collect(Collectors.toList());
    }

    @Override
    public InstallmentDTO payInstallment(Long installmentId, LocalDate paymentDate) {
        Installment installment = repository.findById(installmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Installment not found with id: " + installmentId));

        Deposit deposit = installment.getDeposit();

        // Validações
        if (installment.getStatus() == StatusInstallment.PAID) {
            throw new BusinessException("This installment has already been paid");
        }

        if (deposit.getStatus() == StatusDeposit.CANCELLED) {
            throw new BusinessException("Cannot pay installment of a cancelled deposit");
        }

        if (deposit.getStatus() == StatusDeposit.COMPLETED) {
            throw new BusinessException("Deposit is already completed");
        }

        // Verifica se a data de pagamento é anterior à data atual
        if (paymentDate.isAfter(LocalDate.now())) {
            throw new BusinessException("Payment date cannot be in the future");
        }

        // Atualiza a parcela
        installment.setPaymentDate(paymentDate);
        installment.setStatus(StatusInstallment.PAID);

        // Atualiza o depósito
        deposit.setPaidInstallments(deposit.getPaidInstallments() + 1);
        deposit.calculateRemaining();
        deposit.updateStatus();

        // Verifica se o veículo deve ser marcado como vendido
        if (deposit.isFullyPaid()) {
            Vehicle vehicle = deposit.getVehicle();
            vehicle.setStatus(StatusVehicle.SOLD);
        }

        // Salva as alterações
        installment = repository.save(installment);
        depositRepository.save(deposit);

        return new InstallmentDTO(installment);
    }

    @Override
    public List<InstallmentDTO> findByDepositId(Long depositId) {
        List<Installment> installments = repository.findByDepositIdOrderByInstallmentNumberAsc(depositId);
        return installments.stream()
                .map(InstallmentDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void generateInstallments(Deposit deposit) {
        List<Installment> installments = depositDomainService.calculateInstallments(deposit);

        // Salva as parcelas
        repository.saveAll(installments);

        // Atualiza o depósito
        deposit.setInstallments(installments);
        depositRepository.save(deposit);
    }

    @Override
    public BigDecimal calculateInstallmentValue(BigDecimal totalAmount, Integer totalInstallments) {
        if (totalInstallments == null || totalInstallments <= 0) {
            throw new IllegalArgumentException("Total installments must be greater than zero");
        }

        return totalAmount.divide(BigDecimal.valueOf(totalInstallments), 2, RoundingMode.HALF_UP);
    }

    // Método para verificar e atualizar parcelas vencidas
    @Scheduled(cron = "0 0 0 * * ?") // Executa diariamente à meia-noite
    @Transactional
    public void checkOverdueInstallments() {
        LocalDate today = LocalDate.now();

        List<Installment> overdueInstallments = repository
                .findOverdueInstallments(today, StatusInstallment.PENDING);

        for (Installment installment : overdueInstallments) {
            installment.setStatus(StatusInstallment.OVERDUE);
            repository.save(installment);

            // Atualiza o status do depósito se necessário
            Deposit deposit = installment.getDeposit();
            if (deposit.getStatus() != StatusDeposit.OVERDUE) {
                deposit.setStatus(StatusDeposit.OVERDUE);
                depositRepository.save(deposit);
            }
        }
    }
}
