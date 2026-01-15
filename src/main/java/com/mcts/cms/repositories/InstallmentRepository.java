package com.mcts.cms.repositories;

import com.mcts.cms.entities.Installment;
import com.mcts.cms.entities.enuns.StatusInstallment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface InstallmentRepository extends JpaRepository<Installment, Long> {


    List<Installment> findByDepositIdOrderByInstallmentNumberAsc(Long depositId);

    // CORRIGIDO: Use o enum diretamente, não string
    @Query("SELECT i FROM Installment i WHERE i.dueDate < :today AND i.status = :status")
    List<Installment> findOverdueInstallments(
            @Param("today") LocalDate today,
            @Param("status") StatusInstallment status);

    List<Installment> findByDepositIdAndStatus(Long depositId, StatusInstallment status);

    // CORRIGIDO: Use o enum StatusInstallment.PAID diretamente
    @Query("SELECT COALESCE(SUM(i.valuePerInstallment), 0) FROM Installment i " +
            "WHERE i.deposit.id = :depositId AND i.status = com.mcts.cms.entities.enuns.StatusInstallment.PAID")
    BigDecimal sumPaidInstallmentsByDepositId(@Param("depositId") Long depositId);

    // Métodos adicionais que você pode precisar
    List<Installment> findByStatus(StatusInstallment status);

    @Query("SELECT i FROM Installment i WHERE i.dueDate BETWEEN :startDate AND :endDate")
    List<Installment> findInstallmentsByDueDateRange(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(i) FROM Installment i WHERE i.deposit.id = :depositId AND i.status = :status")
    Long countByDepositIdAndStatus(@Param("depositId") Long depositId, @Param("status") StatusInstallment status);
}
