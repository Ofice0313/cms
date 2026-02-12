package com.mcts.cms.controllers;

import com.mcts.cms.dto.VehicleDTO;
import com.mcts.cms.dto.VehicleStockDTO;
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
@RequestMapping(value = "/api/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService service;

    @GetMapping(value = "/vehicles")
    public ResponseEntity<Page<VehicleDTO>> findAllPage(Pageable pageable) {
        Page<VehicleDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/vehicles-test")
    public ResponseEntity<List<VehicleDTO>> findAll() {
        List<VehicleDTO> list = service.findAll(); // Crie um método sem paginação
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/vehicles/stock")
    public ResponseEntity<Page<VehicleStockDTO>> findAllStockVehicles(Pageable pageable) {
        Page<VehicleStockDTO> list = service.findAllStockVehicles(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<VehicleDTO> findById(@PathVariable Long id) {
        VehicleDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/details/{id}")
    public ResponseEntity<VehicleDTO> findDetails(@PathVariable Long id) {
        VehicleDTO dto = service.findDetails(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping(value = "/vehicles")
    public ResponseEntity<VehicleDTO> insert(@RequestBody VehicleDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<VehicleDTO> update(@PathVariable Long id, @RequestBody VehicleDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<VehicleDTO> patch(@PathVariable Long id, @RequestBody VehicleDTO dto) {
        dto = service.patch(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/metrics/stock-count")
    public ResponseEntity<Long> countStockVehicles() {
        return ResponseEntity.ok(service.countStockVehicles());
    }

    @GetMapping(value = "/metrics/sold-count")
    public ResponseEntity<Long> countSoldVehicles() {
        return ResponseEntity.ok(service.countSoldVehicles());
    }

    @GetMapping(value = "/metrics/total-investment")
    public ResponseEntity<BigDecimal> totalInvestment() {
        return ResponseEntity.ok(service.totalInvestment());
    }

}
