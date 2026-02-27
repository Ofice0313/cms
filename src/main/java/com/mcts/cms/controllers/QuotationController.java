package com.mcts.cms.controllers;

import com.mcts.cms.dto.QuotationDTO;
import com.mcts.cms.dto.QuotationSummaryDTO;
import com.mcts.cms.dto.VehicleDTO;
import com.mcts.cms.dto.VehicleStockDTO;
import com.mcts.cms.services.QuotationService;
import com.mcts.cms.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/quotation")
public class QuotationController {

    @Autowired
    private QuotationService service;

    @GetMapping(value = "/quotations")
    public ResponseEntity<Page<QuotationDTO>> findAllPage(Pageable pageable) {
        Page<QuotationDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/quotations/summary")
    public ResponseEntity<Page<QuotationSummaryDTO>> findQuotationSummary(Pageable pageable) {
        Page<QuotationSummaryDTO> list = service.findQuotationSummary(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/quotations-test")
    public ResponseEntity<List<QuotationDTO>> findAll() {
        List<QuotationDTO> list = service.findAll(); // Crie um método sem paginação
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<QuotationDTO> findById(@PathVariable Long id) {
        QuotationDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    // Detalhes da cotação, incluindo informações do cliente e do veículo
    @GetMapping(value = "/details/{id}")
    public ResponseEntity<QuotationDTO> findDetails(@PathVariable Long id) {
        QuotationDTO dto = service.findDetails(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping(value = "/quotations")
    public ResponseEntity<QuotationDTO> insert(@RequestBody QuotationDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<QuotationDTO> update(@PathVariable Long id, @RequestBody QuotationDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<QuotationDTO> patch(@PathVariable Long id, @RequestBody QuotationDTO dto) {
        dto = service.patch(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
