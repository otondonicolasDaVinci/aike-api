package com.tesis.aike.controller;

import com.tesis.aike.model.dto.CabinDTO;
import com.tesis.aike.service.CabinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cabins")
public class CabinController {

    private final CabinService service;

    @Autowired
    public CabinController(CabinService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CabinDTO> create(@RequestBody CabinDTO cabin) {
        return ResponseEntity.ok(service.create(cabin));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CabinDTO> find(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<CabinDTO>> all() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CabinDTO> update(@PathVariable Integer id, @RequestBody CabinDTO cabin) {
        return ResponseEntity.ok(service.update(id, cabin));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
