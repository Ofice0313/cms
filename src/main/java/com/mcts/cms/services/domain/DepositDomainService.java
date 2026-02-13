package com.mcts.cms.services.domain;

import com.mcts.cms.entities.Deposit;
import com.mcts.cms.entities.Installment;
import com.mcts.cms.entities.enuns.StatusInstallment;
import com.mcts.cms.services.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DepositDomainService {

    public List<Installment> calculateInstallments(Deposit deposit) {
        validateInstallmentGeneration(deposit);

        BigDecimal remaining = deposit.getRemainingAmount();
        Integer totalInstallments = deposit.getTotalInstallments();

        // Calcula valor de cada parcela
        BigDecimal installmentValue = remaining.divide(
                BigDecimal.valueOf(totalInstallments),
                2,
                RoundingMode.HALF_UP
        );

        // Ajusta diferença de arredondamento na última parcela
        BigDecimal totalCalculated = installmentValue.multiply(BigDecimal.valueOf(totalInstallments));
        BigDecimal difference = remaining.subtract(totalCalculated);

        List<Installment> installments = new ArrayList<>();

        LocalDate baseDate = deposit.getDepositDate() != null ? deposit.getDepositDate() : LocalDate.now();
        // Data da primeira parcela: 1 mês após a data do depósito
        LocalDate firstDueDate = baseDate.plusMonths(1);

        for (int i = 1; i <= totalInstallments; i++) {
            Installment installment = new Installment();
            installment.setInstallmentNumber(i);

            // Calcula data de vencimento: primeira parcela + (i-1) meses
            installment.setDueDate(firstDueDate.plusMonths(i - 1));

            // Ajusta última parcela se houver diferença de arredondamento
            BigDecimal finalValue = installmentValue;
            if (i == totalInstallments && difference.compareTo(BigDecimal.ZERO) != 0) {
                finalValue = finalValue.add(difference);
            }

            installment.setValuePerInstallment(finalValue);
            installment.setStatus(StatusInstallment.PENDING);
            installment.setObservations(deposit.getObservations());
            installment.setDeposit(deposit);
            installments.add(installment);
        }

        return installments;
    }

    private void validateInstallmentGeneration(Deposit deposit) {
        if (deposit.getTotalInstallments() == null || deposit.getTotalInstallments() <= 0) {
            throw new BusinessException("Total installments must be specified and greater than zero");
        }

        if (!deposit.getInstallments().isEmpty()) {
            throw new BusinessException("Installments have already been generated for this deposit");
        }

        if (deposit.getRemainingAmount() == null || deposit.getRemainingAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("No remaining amount to generate installments");
        }
    }
}
