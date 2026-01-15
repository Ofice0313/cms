package com.mcts.cms.controllers;

import com.mcts.cms.dto.InstallmentDTO;
import com.mcts.cms.dto.InstallmentPaymentDTO;
import com.mcts.cms.entities.Installment;
import com.mcts.cms.entities.enuns.StatusInstallment;
import com.mcts.cms.repositories.InstallmentRepository;
import com.mcts.cms.services.InstallmentServiceImpl;
import com.mcts.cms.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/installment")
public class InstallmentController {

    @Autowired
    private InstallmentServiceImpl service;

    @Autowired
    private InstallmentRepository installmentRepository;

    @GetMapping(value = "/installments")
    public ResponseEntity<Page<InstallmentDTO>> findAllPage(Pageable pageable) {
        Page<InstallmentDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

//    @GetMapping(value = "/{id}")
//    public ResponseEntity<InstallmentDTO> findById(@PathVariable Long id) {
//        InstallmentDTO dto = service.findById(id);
//        return ResponseEntity.ok().body(dto);
//    }

    @PostMapping(value = "/installments")
    public ResponseEntity<InstallmentDTO> insert(@RequestBody InstallmentDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<InstallmentDTO> update(@PathVariable Long id, @RequestBody InstallmentDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<InstallmentDTO> payInstallment(
            @PathVariable Long id,
            @Valid @RequestBody InstallmentPaymentDTO paymentDTO) {

        InstallmentDTO paidInstallment = service.payInstallment(id, paymentDTO.getPaymentDate());
        return ResponseEntity.ok(paidInstallment);
    }

    @GetMapping("/deposit/{depositId}")
    public ResponseEntity<List<InstallmentDTO>> getInstallmentsByDeposit(@PathVariable Long depositId) {
        List<InstallmentDTO> installments = service.findByDepositId(depositId);
        return ResponseEntity.ok(installments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstallmentDTO> findById(@PathVariable Long id) {
        Installment installment = installmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Installment not found"));
        return ResponseEntity.ok(new InstallmentDTO(installment));
    }

    @GetMapping("/deposit/{depositId}/status/{status}")
    public ResponseEntity<List<InstallmentDTO>> getInstallmentsByDepositAndStatus(
            @PathVariable Long depositId,
            @PathVariable StatusInstallment status) {

        List<Installment> installments = installmentRepository.findByDepositIdAndStatus(depositId, status);
        List<InstallmentDTO> dtos = installments.stream()
                .map(InstallmentDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

}
