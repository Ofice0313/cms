package com.mcts.cms.controllers;

import com.mcts.cms.dto.OrderAcquisitionVehicleDTO;
import com.mcts.cms.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping(value = "/orders")
    public ResponseEntity<Page<OrderAcquisitionVehicleDTO>> findAllPage(Pageable pageable) {
        Page<OrderAcquisitionVehicleDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderAcquisitionVehicleDTO> findById(@PathVariable Long id) {
        OrderAcquisitionVehicleDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping(value = "/orders")
    public ResponseEntity<OrderAcquisitionVehicleDTO> insert(@RequestBody OrderAcquisitionVehicleDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<OrderAcquisitionVehicleDTO> update(@PathVariable Long id, @RequestBody OrderAcquisitionVehicleDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
