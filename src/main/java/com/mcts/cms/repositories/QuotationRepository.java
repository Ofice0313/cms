package com.mcts.cms.repositories;

import com.mcts.cms.dto.QuotationSummaryDTO;
import com.mcts.cms.entities.Quotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuotationRepository extends JpaRepository<Quotation, Long> {

	@Query("select new com.mcts.cms.dto.QuotationSummaryDTO(" 
			+ "q.id," 
			+ "q.brand," 
			+ "q.model," 
			+ "q.year," 
			+ "concat(c.firstName, ' ', c.lastName)," 
			+ "c.phone" 
			+ ") " 
			+ "from Quotation q " 
			+ "left join q.client c")
	Page<QuotationSummaryDTO> findQuotationSummary(Pageable pageable);
}
