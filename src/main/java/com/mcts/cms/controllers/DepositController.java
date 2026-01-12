package com.mcts.cms.controllers;

import com.mcts.cms.dto.DepositVehicleInstallmentClientDTO;
import com.mcts.cms.services.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/deposit")
public class DepositController {

    @Autowired
    private DepositService service;

    @GetMapping(value = "/deposits")
    public ResponseEntity<Page<DepositVehicleInstallmentClientDTO>> findAllPage(Pageable pageable) {
        Page<DepositVehicleInstallmentClientDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DepositVehicleInstallmentClientDTO> findById(@PathVariable Long id) {
        DepositVehicleInstallmentClientDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping(value = "/deposits")
    public ResponseEntity<DepositVehicleInstallmentClientDTO> insert(@RequestBody DepositVehicleInstallmentClientDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DepositVehicleInstallmentClientDTO> update(@PathVariable Long id, @RequestBody DepositVehicleInstallmentClientDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
