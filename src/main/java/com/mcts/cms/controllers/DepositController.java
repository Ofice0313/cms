package com.mcts.cms.controllers;

import com.mcts.cms.dto.DepositSummaryDTO;
import com.mcts.cms.dto.DepositVehicleClientDTO;
import com.mcts.cms.dto.InstallmentDTO;
import com.mcts.cms.dto.InstallmentPaymentDTO;
import com.mcts.cms.entities.enuns.StatusVehicle;
import com.mcts.cms.services.DepositServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/deposit")
public class DepositController {

    @Autowired
    private DepositServiceImpl service;

    @GetMapping(value = "/deposits")
    public ResponseEntity<Page<DepositVehicleClientDTO>> findAllPage(Pageable pageable) {
        Page<DepositVehicleClientDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/deposits/summary")
    public ResponseEntity<Page<DepositSummaryDTO>> findSummaryByVehicleStatus(
            @RequestParam(required = false) StatusVehicle vehicleStatus,
            Pageable pageable) {
        Page<DepositSummaryDTO> list = service.findSummaryByVehicleStatus(vehicleStatus, pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DepositVehicleClientDTO> findById(@PathVariable Long id) {
        DepositVehicleClientDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping(value = "/deposits")
    public ResponseEntity<DepositVehicleClientDTO> insert(@RequestBody DepositVehicleClientDTO dto) {

        if (dto.getDepositDate() == null) {
            dto.setDepositDate(LocalDate.now());
        }

        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DepositVehicleClientDTO> update(@PathVariable Long id, @RequestBody DepositVehicleClientDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * POST /deposits/{id}/generate-installments - Gera parcelas para um depósito
     */
    @PostMapping(value = "/{id}/generate-installments")
    public ResponseEntity<Void> generateInstallments(@PathVariable Long id) {
        service.generateInstallments(id);
        return ResponseEntity.ok().build();
    }

    /**
     * GET /deposits/{id}/installments - Lista parcelas de um depósito
     */
    @GetMapping("/{id}/installments")
    public ResponseEntity<List<InstallmentDTO>> getInstallments(@PathVariable Long id) {
        List<InstallmentDTO> installments = service.getInstallmentsByDepositId(id);
        return ResponseEntity.ok(installments);
    }

    /**
     * POST /deposits/{depositId}/installments/{installmentNumber}/pay - Paga uma parcela
     */
    @PostMapping("/{depositId}/installments/{installmentNumber}/pay")
    public ResponseEntity<Void> payInstallment(@PathVariable Long depositId,
                                               @PathVariable Integer installmentNumber,
                                               @RequestBody InstallmentPaymentDTO paymentDTO) {
        service.payInstallment(depositId, installmentNumber, paymentDTO.getPaymentDate());
        return ResponseEntity.ok().build();
    }


}
