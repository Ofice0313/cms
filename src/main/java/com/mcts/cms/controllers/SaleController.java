package com.mcts.cms.controllers;

import com.mcts.cms.dto.SaleOrderClientDTO;
import com.mcts.cms.services.OrderService;
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
    public ResponseEntity<Page<SaleOrderClientDTO>> findAllPage(Pageable pageable) {
        Page<SaleOrderClientDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SaleOrderClientDTO> findById(@PathVariable Long id) {
        SaleOrderClientDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping(value = "/sales")
    public ResponseEntity<SaleOrderClientDTO> insert(@RequestBody SaleOrderClientDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<SaleOrderClientDTO> update(@PathVariable Long id, @RequestBody SaleOrderClientDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
