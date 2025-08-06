package com.tesis.aike.controller;

import com.tesis.aike.exception.CabinAlreadyReservedException;
import com.tesis.aike.model.dto.ReservationDTO;
import com.tesis.aike.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationsController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationDTO>> byUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reservationService.findByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> byId(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.findById(id));
    }

    @GetMapping("/cabin/{cabinId}")
    public ResponseEntity<List<ReservationDTO>> byCabin(@PathVariable Long cabinId) {
        return ResponseEntity.ok(reservationService.findByCabinId(cabinId));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReservationDTO dto) {
        try {
            return ResponseEntity.ok(reservationService.create(dto));
        } catch (CabinAlreadyReservedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of(
                            "message", e.getMessage(),
                            "reservations", e.getReservations()
                    )
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> update(@PathVariable Long id, @RequestBody ReservationDTO dto) {
        return ResponseEntity.ok(reservationService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> all() {
        return ResponseEntity.ok(reservationService.findAll());
    }

}
