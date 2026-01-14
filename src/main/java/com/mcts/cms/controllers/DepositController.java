package com.mcts.cms.controllers;

import com.mcts.cms.dto.DepositVehicleInstallmentClientDTO;
import com.mcts.cms.entities.Deposit;
import com.mcts.cms.services.DepositService;
import com.mcts.cms.services.requests.CreateDepositRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    //PostMapping
    public ResponseEntity<Deposit> createDeposit(@Valid @RequestBody CreateDepositRequest request) {
        // Converte request para entidade
        Deposit deposit = new Deposit();
        deposit.setSaleValue(request.getSaleValue());
        deposit.setInitialDepositValue(request.getInitialDepositValue());
        deposit.setObservations(request.getObservations());
        deposit.setDueDate(request.getDueDate());

        // Aqui você precisaria buscar Vehicle e Client pelos IDs
        // deposit.setVehicle(vehicleService.findById(request.getVehicleId()));
        // deposit.setClient(clientService.findById(request.getClientId()));

        Deposit createdDeposit = service.createDeposit(
                deposit,
                request.getNumberOfInstallments()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(createdDeposit);
    }
}
