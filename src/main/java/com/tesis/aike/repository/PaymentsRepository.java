package com.tesis.aike.repository; // Asegúrate de usar el paquete correcto para tus repositorios

import com.tesis.aike.model.entity.PaymentsEntity; // Asegúrate de que la ruta a tu entidad sea correcta
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentsRepository extends JpaRepository<PaymentsEntity, Integer> {
    // JpaRepository proporciona métodos CRUD básicos (save, findById, findAll, deleteById, etc.)
    // Puedes agregar métodos personalizados aquí si necesitas consultas específicas para la entidad Payments.

    // Ejemplo de método personalizado para buscar pagos por ID de reserva
    List<PaymentsEntity> findByReservationId(int reservationId);

    // Ejemplo de método personalizado para buscar pagos por método de pago
    List<PaymentsEntity> findByPaymentMethod(String paymentMethod);

    // Ejemplo de método personalizado para buscar pagos con un estado específico
    List<PaymentsEntity> findByStatus(String status);

    // Ejemplo de método personalizado para buscar pagos realizados después de una fecha específica
    // List<PaymentsEntity> findByPaymentDateAfter(Timestamp paymentDate);
}