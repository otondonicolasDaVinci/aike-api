package com.tesis.aike.controller; // Asegúrate de usar el paquete correcto para tus controladores

import com.tesis.aike.model.entity.ReservationsEntity;
import com.tesis.aike.service.ReservationsService; // Asegúrate de que la ruta a tu servicio sea correcta
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationsController {

    @Autowired
    private ReservationsService reservationsService;

    @GetMapping
    public ResponseEntity<List<ReservationsEntity>> obtenerTodasLasReservas() {
        List<ReservationsEntity> reservations = reservationsService.obtenerTodasLasReservas();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationsEntity> obtenerReservaPorId(@PathVariable int id) {
        Optional<ReservationsEntity> reservation = reservationsService.obtenerReservaPorId(id);
        return reservation.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ReservationsEntity> crearReserva(@RequestBody ReservationsEntity reservation) {
        ReservationsEntity nuevaReserva = reservationsService.guardarReserva(reservation);
        return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationsEntity> actualizarReserva(@PathVariable int id, @RequestBody ReservationsEntity reservationActualizada) {
        Optional<ReservationsEntity> reservationExistente = reservationsService.obtenerReservaPorId(id);
        if (reservationExistente.isPresent()) {
            reservationActualizada.setId(id);
            ReservationsEntity reservaActualizada = reservationsService.guardarReserva(reservationActualizada);
            return new ResponseEntity<>(reservaActualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable int id) {
        if (reservationsService.obtenerReservaPorId(id).isPresent()) {
            reservationsService.eliminarReserva(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}