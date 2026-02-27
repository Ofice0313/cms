package com.mcts.cms.repositories;

import com.mcts.cms.dto.VehicleStockDTO;
import com.mcts.cms.entities.Vehicle;
import com.mcts.cms.entities.enuns.StatusVehicle;
import com.mcts.cms.entities.enuns.Step;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface VehicleRepository extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {

    Page<Vehicle> findByStep(Step step, Pageable pageable);

    @Query(value = "select count(*) from tb_vehicle where status = 0", nativeQuery = true)
    long countStockVehicles();

    @Query(value = "select count(*) from tb_vehicle where status = 1", nativeQuery = true)
    long countSoldVehicles();

    @Query(
	    value = "select coalesce(sum("
		    + "coalesce(purchase_value, 0)"
		    + " + coalesce(hotel, 0)"
		    + " + coalesce(food, 0)"
		    + " + coalesce(fuel, 0)"
		    + " + coalesce(rights, 0)"
		    + " + coalesce(cp, 0)"
		    + " + coalesce(innater, 0)"
		    + " + coalesce(loading, 0)"
		    + " + coalesce(customs_broker, 0)"
		    + " + coalesce(driver, 0)"
		    + " + coalesce(inspection, 0)"
		    + " + coalesce(license_plate, 0)"
		    + "), 0) from tb_vehicle",
	    nativeQuery = true
    )
    BigDecimal sumTotalInvestment();

	@Query(
			"select new com.mcts.cms.dto.VehicleStockDTO("
					+ "concat(v.brand, ' ', v.model),"
					+ "v.year,"
					+ "v.status,"
					+ "v.step,"
					+ "(coalesce(v.purchaseValue, 0)"
					+ " + coalesce(v.hotel, 0)"
					+ " + coalesce(v.food, 0)"
					+ " + coalesce(v.fuel, 0)"
					+ " + coalesce(v.rights, 0)"
					+ " + coalesce(v.cp, 0)"
					+ " + coalesce(v.innater, 0)"
					+ " + coalesce(v.loading, 0)"
					+ " + coalesce(v.customsBroker, 0)"
					+ " + coalesce(v.driver, 0)"
					+ " + coalesce(v.inspection, 0)"
					+ " + coalesce(v.licensePlate, 0)"
					+ ")"
					+ ") "
					+ "from Vehicle v "
					+ "where v.status = :status"
	)
	Page<VehicleStockDTO> findByStatus(StatusVehicle status, Pageable pageable);

	@Query(
			"select new com.mcts.cms.dto.VehicleStockDTO("
					+ "concat(v.brand, ' ', v.model),"
					+ "v.year,"
					+ "v.status,"
					+ "v.step,"
					+ "(coalesce(v.purchaseValue, 0)"
					+ " + coalesce(v.hotel, 0)"
					+ " + coalesce(v.food, 0)"
					+ " + coalesce(v.fuel, 0)"
					+ " + coalesce(v.rights, 0)"
					+ " + coalesce(v.cp, 0)"
					+ " + coalesce(v.innater, 0)"
					+ " + coalesce(v.loading, 0)"
					+ " + coalesce(v.customsBroker, 0)"
					+ " + coalesce(v.driver, 0)"
					+ " + coalesce(v.inspection, 0)"
					+ " + coalesce(v.licensePlate, 0)"
					+ ")"
					+ ") "
					+ "from Vehicle v "
					+ "where v.step = :step"
	)
	Page<VehicleStockDTO> findByStepSummary(Step step, Pageable pageable);
}
