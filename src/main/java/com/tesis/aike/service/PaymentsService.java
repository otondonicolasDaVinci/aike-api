package com.tesis.aike.service; // Asegúrate de usar el paquete correcto para tus servicios

import com.tesis.aike.model.entity.PaymentsEntity; // Asegúrate de que la ruta a tu entidad sea correcta
import com.tesis.aike.repository.PaymentsRepository; // Asegúrate de que la ruta a tu repositorio sea correcta
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentsService {

    @Autowired
    private PaymentsRepository paymentsRepository;

    public List<PaymentsEntity> obtenerTodosLosPagos() {
        return paymentsRepository.findAll();
    }

    public Optional<PaymentsEntity> obtenerPagoPorId(int id) {
        return paymentsRepository.findById(id);
    }

    public List<PaymentsEntity> obtenerPagosPorReserva(int reservationId) {
        return paymentsRepository.findByReservationId(reservationId);
    }

    public List<PaymentsEntity> obtenerPagosPorMetodo(String paymentMethod) {
        return paymentsRepository.findByPaymentMethod(paymentMethod);
    }

    public List<PaymentsEntity> obtenerPagosPorEstado(String status) {
        return paymentsRepository.findByStatus(status);
    }

    public PaymentsEntity guardarPago(PaymentsEntity payment) {
        return paymentsRepository.save(payment);
    }

    public void eliminarPago(int id) {
        paymentsRepository.deleteById(id);
    }

    // Puedes agregar más métodos de lógica de negocio relacionados con los pagos aquí
}