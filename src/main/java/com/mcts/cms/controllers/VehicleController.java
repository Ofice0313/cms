package com.mcts.cms.controllers;

import com.mcts.cms.dto.VehicleDTO;
import com.mcts.cms.services.VehiclePagingTestService;
import com.mcts.cms.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService service;

    @Autowired
    private VehiclePagingTestService pagingTestService;

    @GetMapping(value = "/vehicles")
    public ResponseEntity<Page<VehicleDTO>> findAllPage(Pageable pageable) {
        Page<VehicleDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/vehicles-paged-test")
    public ResponseEntity<Page<VehicleDTO>> findAllPageTest(Pageable pageable) {
        Page<VehicleDTO> list = pagingTestService.findAllPagedTest(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/vehicles-test")
    public ResponseEntity<List<VehicleDTO>> findAll() {
        List<VehicleDTO> list = service.findAll(); // Crie um método sem paginação
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<VehicleDTO> findById(@PathVariable Long id) {
        VehicleDTO dto = service.findById(id);
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

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
