package com.tesis.aike.controller;

import com.tesis.aike.model.entity.ReservationsEntity;
import com.tesis.aike.repository.ReservationsRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    private final ReservationsRepository reservationsRepository;

    public ReservationsController(ReservationsRepository reservationsRepository) {
        this.reservationsRepository = reservationsRepository;
    }

    // Obtener todas las reservas de un usuario por su ID
    @GetMapping("/user/{userId}")
    public List<ReservationsEntity> getReservationsByUserId(@PathVariable int userId) {
        return reservationsRepository.findByUserId(userId);
    }

    // Obtener información de una reserva específica por su ID
    @GetMapping("/{id}")
    public ReservationsEntity getReservationById(@PathVariable int id) {
        return reservationsRepository.findById(id).orElse(null);
    }

    // Crear una nueva reserva
    @PostMapping
    public ReservationsEntity createReservation(@RequestBody ReservationsEntity reservation) {
        return reservationsRepository.save(reservation);
    }
}
