package com.mcts.cms.repositories;

import com.mcts.cms.dto.DepositSummaryDTO;
import com.mcts.cms.entities.Deposit;
import com.mcts.cms.entities.enuns.StatusInstallment;
import com.mcts.cms.entities.enuns.StatusVehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
            + "and (:firstName is null or lower(c.firstName) like lower(concat('%',:firstName,'%'))) "
            + "and (:lastName is null or lower(c.lastName) like lower(concat('%',:lastName,'%'))) "
            + "and (:brand is null or lower(v.brand) like lower(concat('%',:brand,'%'))) "
            + "and (:model is null or lower(v.model) like lower(concat('%',:model,'%'))) "
            + "group by d.id, c.firstName, c.lastName, c.phone, v.brand, v.model, v.year,"
            + "d.saleValue, d.initialDepositValue, d.totalInstallments, d.paidInstallments, d.status")
    Page<DepositSummaryDTO> findDepositSummaryByFilters(
            StatusVehicle vehicleStatus,
            @Param("paidStatus") StatusInstallment paidStatus,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("brand") String brand,
            @Param("model") String model,
            Pageable pageable);

    @Query("select d from Deposit d join d.client c join d.vehicle v "
            + "where (:firstName is null or lower(c.firstName) like lower(concat('%',:firstName,'%'))) "
            + "and (:lastName is null or lower(c.lastName) like lower(concat('%',:lastName,'%'))) "
            + "and (:brand is null or lower(v.brand) like lower(concat('%',:brand,'%'))) "
            + "and (:model is null or lower(v.model) like lower(concat('%',:model,'%')))")
    Page<Deposit> searchByClientFirstLastBrandModel(@Param("firstName") String firstName,
                                                    @Param("lastName") String lastName,
                                                    @Param("brand") String brand,
                                                    @Param("model") String model,
                                                    Pageable pageable);

    @Query(value = "SELECT d.* FROM tb_deposit d "
            + "JOIN tb_client c ON d.client_id = c.id "
            + "JOIN tb_vehicle v ON d.vehicle_id = v.id "
            + "WHERE (:firstName IS NULL OR lower(coalesce(c.first_name, '')) LIKE lower(concat('%', :firstName, '%'))) "
            + "AND (:lastName IS NULL OR lower(coalesce(c.last_name, '')) LIKE lower(concat('%', :lastName, '%'))) "
            + "AND (:brand IS NULL OR lower(coalesce(v.brand, '')) LIKE lower(concat('%', :brand, '%'))) "
            + "AND (:model IS NULL OR lower(coalesce(v.model, '')) LIKE lower(concat('%', :model, '%')))",
            countQuery = "SELECT count(d.*) FROM tb_deposit d "
                    + "JOIN tb_client c ON d.client_id = c.id "
                    + "JOIN tb_vehicle v ON d.vehicle_id = v.id "
                    + "WHERE (:firstName IS NULL OR lower(coalesce(c.first_name, '')) LIKE lower(concat('%', :firstName, '%'))) "
                    + "AND (:lastName IS NULL OR lower(coalesce(c.last_name, '')) LIKE lower(concat('%', :lastName, '%'))) "
                    + "AND (:brand IS NULL OR lower(coalesce(v.brand, '')) LIKE lower(concat('%', :brand, '%'))) "
                    + "AND (:model IS NULL OR lower(coalesce(v.model, '')) LIKE lower(concat('%', :model, '%')))",
            nativeQuery = true)
    Page<Deposit> searchByClientFirstLastBrandModelNative(@Param("firstName") String firstName,
                                                          @Param("lastName") String lastName,
                                                          @Param("brand") String brand,
                                                          @Param("model") String model,
                                                          Pageable pageable);
}
