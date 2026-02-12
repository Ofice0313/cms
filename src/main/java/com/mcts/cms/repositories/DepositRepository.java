package com.mcts.cms.repositories;

import com.mcts.cms.dto.DepositSummaryDTO;
import com.mcts.cms.entities.Deposit;
import com.mcts.cms.entities.enuns.StatusInstallment;
import com.mcts.cms.entities.enuns.StatusVehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepositRepository extends JpaRepository<Deposit, Long> {

    @Query("select new com.mcts.cms.dto.DepositSummaryDTO("
            + "d.id,"
            + "concat(c.firstName, ' ', c.lastName),"
            + "c.phone,"
            + "v.brand,"
            + "v.model,"
            + "v.year,"
            + "d.saleValue,"
            + "(coalesce(d.saleValue, 0) - coalesce(d.initialDepositValue, 0) - "
            + "coalesce(sum(case when i.status = :paidStatus then i.valuePerInstallment else 0 end), 0)),"
            + "d.totalInstallments,"
            + "d.paidInstallments,"
            + "min(case when i.status <> :paidStatus then i.dueDate else null end),"
            + "d.status"
            + ") "
            + "from Deposit d "
            + "join d.client c "
            + "join d.vehicle v "
            + "left join d.installments i "
            + "where (:vehicleStatus is null or v.status = :vehicleStatus) "
            + "group by d.id, c.firstName, c.lastName, c.phone, v.brand, v.model, v.year,"
            + "d.saleValue, d.initialDepositValue, d.totalInstallments, d.paidInstallments, d.status")
    Page<DepositSummaryDTO> findDepositSummary(StatusVehicle vehicleStatus,
            StatusInstallment paidStatus,
            Pageable pageable);
}
