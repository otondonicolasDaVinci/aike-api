package com.tesis.aike.controller; // Asegúrate de usar el paquete correcto para tus controladores

import com.tesis.aike.model.entity.PaymentsEntity;
import com.tesis.aike.service.PaymentsService; // Asegúrate de que la ruta a tu servicio sea correcta
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

    @Autowired
    private PaymentsService paymentsService;

    @GetMapping
    public ResponseEntity<List<PaymentsEntity>> obtenerTodosLosPagos() {
        List<PaymentsEntity> payments = paymentsService.obtenerTodosLosPagos();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentsEntity> obtenerPagoPorId(@PathVariable int id) {
        Optional<PaymentsEntity> payment = paymentsService.obtenerPagoPorId(id);
        return payment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<PaymentsEntity> crearPago(@RequestBody PaymentsEntity payment) {
        PaymentsEntity nuevoPago = paymentsService.guardarPago(payment);
        return new ResponseEntity<>(nuevoPago, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentsEntity> actualizarPago(@PathVariable int id, @RequestBody PaymentsEntity paymentActualizado) {
        Optional<PaymentsEntity> paymentExistente = paymentsService.obtenerPagoPorId(id);
        if (paymentExistente.isPresent()) {
            paymentActualizado.setId(id);
            PaymentsEntity pagoActualizado = paymentsService.guardarPago(paymentActualizado);
            return new ResponseEntity<>(pagoActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable int id) {
        if (paymentsService.obtenerPagoPorId(id).isPresent()) {
            paymentsService.eliminarPago(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}