package com.mcts.cms.controllers;

import com.mcts.cms.dto.SaleSummaryDTO;
import com.mcts.cms.dto.SaleVehicleClientDTO;
import com.mcts.cms.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/sale")
public class SaleController {

    @Autowired
    private SaleService service;

    @GetMapping(value = "/sales")
    public ResponseEntity<Page<SaleVehicleClientDTO>> findAllPage(Pageable pageable) {
        Page<SaleVehicleClientDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/metrics/total-profit")
    public ResponseEntity<java.math.BigDecimal> totalProfitForSoldVehicles() {
        return ResponseEntity.ok(service.totalProfitForSoldVehicles());
    }

    @GetMapping(value = "/sales/summary")
    public ResponseEntity<Page<SaleSummaryDTO>> findSaleSummary(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            Pageable pageable) {
        Page<SaleSummaryDTO> list = service.findSaleSummary(year, month, pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SaleVehicleClientDTO> findById(@PathVariable Long id) {
        SaleVehicleClientDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping(value = "/sales")
    public ResponseEntity<SaleVehicleClientDTO> insert(@RequestBody SaleVehicleClientDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<SaleVehicleClientDTO> update(@PathVariable Long id, @RequestBody SaleVehicleClientDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
