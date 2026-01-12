package com.mcts.cms.controllers;

import com.mcts.cms.dto.AcquisitionVehicleDTO;
import com.mcts.cms.services.AcquisitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/acquisition")
public class AcquisitionController {

    @Autowired
    private AcquisitionService service;

    @GetMapping(value = "/acquisitions")
    public ResponseEntity<Page<AcquisitionVehicleDTO>> findAllPage(Pageable pageable) {
        Page<AcquisitionVehicleDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AcquisitionVehicleDTO> findById(@PathVariable Long id) {
        AcquisitionVehicleDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping(value = "/acquisitions")
    public ResponseEntity<AcquisitionVehicleDTO> insert(@RequestBody AcquisitionVehicleDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AcquisitionVehicleDTO> update(@PathVariable Long id, @RequestBody AcquisitionVehicleDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
