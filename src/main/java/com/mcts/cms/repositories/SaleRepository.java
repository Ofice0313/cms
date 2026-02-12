package com.mcts.cms.repositories;

import com.mcts.cms.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(
	    value = "select coalesce(sum("
		    + "coalesce(s.sale_value, 0) - ("
		    + "coalesce(v.purchase_value, 0)"
		    + " + coalesce(v.travel, 0)"
		    + " + coalesce(v.rights, 0)"
		    + " + coalesce(v.cp, 0)"
		    + " + coalesce(v.innater, 0)"
		    + " + coalesce(v.loading, 0)"
		    + " + coalesce(v.customs_broker, 0)"
		    + " + coalesce(v.driver, 0)"
		    + " + coalesce(v.inspection, 0)"
		    + " + coalesce(v.license_plate, 0)"
		    + ")"
		    + "), 0)"
					+ " from tb_sale s"
					+ " join tb_vehicle v on v.id = s.vehicle_id"
		    + " where v.status = 1",
	    nativeQuery = true
    )
    BigDecimal sumProfitForSoldVehicles();
}
