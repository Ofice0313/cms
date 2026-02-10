package com.mcts.cms.repositories;

import com.mcts.cms.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query(value = "select count(*) from tb_vehicle where status = 0", nativeQuery = true)
    long countStockVehicles();

    @Query(value = "select count(*) from tb_vehicle where status = 1", nativeQuery = true)
    long countSoldVehicles();

    @Query(
	    value = "select coalesce(sum("
		    + "coalesce(purchase_value, 0)"
		    + " + coalesce(travel, 0)"
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
}
