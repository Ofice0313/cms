package com.mcts.cms.services;

import com.mcts.cms.dto.DepositVehicleInstallmentClientDTO;
import com.mcts.cms.dto.InstallmentDTO;
import com.mcts.cms.entities.*;
import com.mcts.cms.entities.enuns.StatusDeposit;
import com.mcts.cms.entities.enuns.StatusInstallment;
import com.mcts.cms.repositories.*;
import com.mcts.cms.services.exceptions.BusinessException;
import com.mcts.cms.services.exceptions.DatabaseException;
import com.mcts.cms.services.exceptions.ResourceNotFoundException;
import com.mcts.cms.services.summaries.DepositSummary;
import com.mcts.cms.services.summaries.InstallmentSummary;
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

    /**
     * Cria um novo depósito com parcelas
     */
    public Deposit createDeposit(Deposit deposit, Integer numberOfInstallments) {
        validateDeposit(deposit);

        // Calcula o valor restante
        BigDecimal remaining = deposit.getRemaining();

        if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("O valor do depósito inicial já cobre o valor total da venda");
        }

        // Configura o depósito
        deposit.setTotalInstallments(numberOfInstallments);
        deposit.setPaidInstallments(0);
        deposit.setStatus(StatusDeposit.PENDING);

        // Salva o depósito primeiro
        Deposit savedDeposit = repository.save(deposit);

        // Cria as parcelas
        createInstallments(savedDeposit, numberOfInstallments);

        return repository.save(savedDeposit);
    }

    /**
     * Cria as parcelas do depósito
     */
    private void createInstallments(Deposit deposit, Integer numberOfInstallments) {
        if (numberOfInstallments == null || numberOfInstallments <= 0) {
            throw new BusinessException("Número de parcelas inválido");
        }

        BigDecimal remaining = deposit.getRemaining();
        BigDecimal installmentValue = remaining.divide(
                BigDecimal.valueOf(numberOfInstallments),
                2,
                RoundingMode.HALF_UP
        );

        // Ajusta a última parcela para compensar arredondamentos
        BigDecimal totalInstallments = installmentValue.multiply(
                BigDecimal.valueOf(numberOfInstallments)
        );
        BigDecimal difference = remaining.subtract(totalInstallments);

        List<Installment> installments = new ArrayList<>();
        LocalDate firstPaymentDate = LocalDate.now().plusMonths(1);

        for (int i = 1; i <= numberOfInstallments; i++) {
            Installment installment = new Installment();
            installment.setInstallmentNumber(i);

            // Ajusta a última parcela
            if (i == numberOfInstallments) {
                installmentValue = installmentValue.add(difference);
            }

            installment.setValuePerInstallment(installmentValue);
            installment.setPaymentDate(firstPaymentDate.plusMonths(i - 1));
            installment.setStatus(StatusInstallment.PENDING);
            installment.setDeposit(deposit);

            installments.add(installment);
        }

        deposit.setInstallments(installments);
    }

    /**
     * Processa o pagamento de uma parcela
     */
    public Installment payInstallment(Long depositId, Integer installmentNumber,
                                      LocalDate paymentDate, String observation) {

        Deposit deposit = repository.findById(depositId)
                .orElseThrow(() -> new EntityNotFoundException("Depósito não encontrado"));

        Installment installment = deposit.getInstallments().stream()
                .filter(i -> i.getInstallmentNumber().equals(installmentNumber))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Parcela não encontrada"));

        validateInstallmentPayment(installment);

        // Atualiza a parcela
        installment.setStatus(StatusInstallment.PAID);
        installment.setPaymentDate(paymentDate != null ? paymentDate : LocalDate.now());

        // Atualiza o depósito
        deposit.setPaidInstallments(deposit.getPaidInstallments() + 1);
        deposit.updateStatus();

        // Verifica se há parcelas atrasadas
        checkOverdueInstallments(deposit);

        installmentRepository.save(installment);
        repository.save(deposit);

//        Process log;
//        log.info("Parcela {}/{} do depósito {} paga com sucesso",
//                installmentNumber, deposit.getTotalInstallments(), depositId);

        return installment;
    }

    /**
     * Adiciona uma parcela extra (adiantamento)
     */
    public Installment addExtraInstallment(Long depositId, BigDecimal amount,
                                           LocalDate paymentDate, String observation) {

        Deposit deposit = repository.findById(depositId)
                .orElseThrow(() -> new EntityNotFoundException("Depósito não encontrado"));

        if (deposit.isFullyPaid()) {
            throw new BusinessException("Depósito já está totalmente pago");
        }

        // Cria nova parcela
        Installment extraInstallment = new Installment();
        extraInstallment.setInstallmentNumber(deposit.getInstallments().size() + 1);
        extraInstallment.setValuePerInstallment(amount);
        extraInstallment.setPaymentDate(paymentDate != null ? paymentDate : LocalDate.now());
        extraInstallment.setStatus(StatusInstallment.PAID);
        extraInstallment.setDeposit(deposit);

        // Adiciona à lista
        deposit.getInstallments().add(extraInstallment);
        deposit.setTotalInstallments(deposit.getTotalInstallments() + 1);
        deposit.setPaidInstallments(deposit.getPaidInstallments() + 1);
        deposit.updateStatus();

        repository.save(deposit);

        return extraInstallment;
    }

    /**
     * Cancela uma parcela
     */
    public void cancelInstallment(Long depositId, Integer installmentNumber, String reason) {
        Deposit deposit = repository.findById(depositId)
                .orElseThrow(() -> new EntityNotFoundException("Depósito não encontrado"));

        Installment installment = deposit.getInstallments().stream()
                .filter(i -> i.getInstallmentNumber().equals(installmentNumber))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Parcela não encontrada"));

        if (installment.getStatus() == StatusInstallment.PAID) {
            throw new BusinessException("Não é possível cancelar uma parcela já paga");
        }

        installment.setStatus(StatusInstallment.CANCELLED);

        // Recalcula as parcelas restantes
        recalculateRemainingInstallments(deposit);

        installmentRepository.save(installment);
        repository.save(deposit);
    }

    /**
     * Verifica parcelas atrasadas
     */
    public void checkOverdueInstallments(Deposit deposit) {
        LocalDate today = LocalDate.now();

        boolean hasOverdue = false;
        for (Installment installment : deposit.getInstallments()) {
            if (installment.getStatus() == StatusInstallment.PENDING &&
                    installment.getPaymentDate().isBefore(today)) {

                installment.setStatus(StatusInstallment.OVERDUE);
                hasOverdue = true;
                installmentRepository.save(installment);

//                log.warn("Parcela {} do depósito {} está atrasada",
//                        installment.getInstallmentNumber(), deposit.getId());
            }
        }

        if (hasOverdue && deposit.getStatus() != StatusDeposit.OVERDUE) {
            deposit.setStatus(StatusDeposit.OVERDUE);
            repository.save(deposit);
        }
    }

    /**
     * Recalcula parcelas após cancelamento
     */
    private void recalculateRemainingInstallments(Deposit deposit) {
        List<Installment> activeInstallments = deposit.getInstallments().stream()
                .filter(i -> i.getStatus() != StatusInstallment.CANCELLED)
                .collect(Collectors.toList());

        BigDecimal totalRemaining = deposit.getRemaining();
        BigDecimal totalPaidInInstallments = deposit.getInstallments().stream()
                .filter(i -> i.getStatus() == StatusInstallment.PAID)
                .map(Installment::getValuePerInstallment)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal remainingAfterPaid = totalRemaining.subtract(totalPaidInInstallments);

        if (activeInstallments.size() > 0) {
            BigDecimal newInstallmentValue = remainingAfterPaid.divide(
                    BigDecimal.valueOf(activeInstallments.size()),
                    2,
                    RoundingMode.HALF_UP
            );

            // Atualiza valores das parcelas pendentes
            int installmentNumber = 1;
            for (Installment installment : activeInstallments) {
                if (installment.getStatus() == StatusInstallment.PENDING) {
                    installment.setInstallmentNumber(installmentNumber++);
                    installment.setValuePerInstallment(newInstallmentValue);
                }
            }

            deposit.setTotalInstallments(activeInstallments.size());
        }
    }

    /**
     * Obtém resumo do depósito
     */
    public DepositSummary getSummary(Long depositId) {
        Deposit deposit = repository.findById(depositId)
                .orElseThrow(() -> new EntityNotFoundException("Depósito não encontrado"));

        return new DepositSummary(
                deposit.getId(),
                deposit.getSaleValue(),
                deposit.getInitialDepositValue(),
                deposit.getRemaining(),
                deposit.getTotalPaid(),
                deposit.getStatus(),
                deposit.getPaidInstallments(),
                deposit.getTotalInstallments(),
                deposit.getInstallments().stream()
                        .map(this::toInstallmentSummary)
                        .collect(Collectors.toList())
        );
    }

    private void validateDeposit(Deposit deposit) {
        if (deposit.getSaleValue() == null || deposit.getSaleValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Valor da venda inválido");
        }

        if (deposit.getInitialDepositValue() == null ||
                deposit.getInitialDepositValue().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Valor do depósito inicial inválido");
        }

        if (deposit.getInitialDepositValue().compareTo(deposit.getSaleValue()) > 0) {
            throw new BusinessException("Depósito inicial não pode ser maior que o valor da venda");
        }

        if (deposit.getVehicle() == null) {
            throw new BusinessException("Veículo é obrigatório");
        }

        if (deposit.getClient() == null) {
            throw new BusinessException("Cliente é obrigatório");
        }
    }

    private void validateInstallmentPayment(Installment installment) {
        if (installment.getStatus() == StatusInstallment.PAID) {
            throw new BusinessException("Esta parcela já foi paga");
        }

        if (installment.getStatus() == StatusInstallment.CANCELLED) {
            throw new BusinessException("Esta parcela foi cancelada");
        }
    }

    private InstallmentSummary toInstallmentSummary(Installment installment) {
        return new InstallmentSummary(
                installment.getInstallmentNumber(),
                installment.getValuePerInstallment(),
                installment.getPaymentDate(),
                installment.getStatus()
        );
    }

}
