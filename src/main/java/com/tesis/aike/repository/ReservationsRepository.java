package com.tesis.aike.repository; // Asegúrate de usar el paquete correcto para tus repositorios

import com.tesis.aike.model.entity.ReservationsEntity; // Asegúrate de que la ruta a tu entidad sea correcta
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ReservationsRepository extends JpaRepository<ReservationsEntity, Integer> {
    // JpaRepository proporciona métodos CRUD básicos (save, findById, findAll, deleteById, etc.)
    // Puedes agregar métodos personalizados aquí si necesitas consultas específicas para la entidad Reservations.

    // Ejemplo de método personalizado para buscar reservas por ID de usuario
    List<ReservationsEntity> findByUserId(int userId);

    // Ejemplo de método personalizado para buscar reservas por ID de cabaña
    List<ReservationsEntity> findByCabinId(int cabinId);

    // Ejemplo de método personalizado para buscar reservas dentro de un rango de fechas
    List<ReservationsEntity> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(Date startDate, Date endDate);

    // Ejemplo de método personalizado para buscar reservas con un estado específico
    List<ReservationsEntity> findByStatus(String status);

    // Ejemplo de método personalizado para buscar reservas que comienzan en una fecha específica
    List<ReservationsEntity> findByStartDate(Date startDate);

    // Ejemplo de método personalizado para buscar reservas que terminan en una fecha específica
    List<ReservationsEntity> findByEndDate(Date endDate);
}