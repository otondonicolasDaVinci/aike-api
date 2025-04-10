package com.tesis.aike.repository; // Asegúrate de usar el paquete correcto para tus repositorios

import com.tesis.aike.model.entity.NotificationsEntity; // Asegúrate de que la ruta a tu entidad sea correcta
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationsRepository extends JpaRepository<NotificationsEntity, Integer> {
    // JpaRepository proporciona métodos CRUD básicos (save, findById, findAll, deleteById, etc.)
    // Puedes agregar métodos personalizados aquí si necesitas consultas específicas para la entidad Notifications.

    // Ejemplo de método personalizado para buscar notificaciones por userId
    // List<NotificationsEntity> findByUserId(int userId);

    // Ejemplo de método personalizado para buscar notificaciones no leídas
    // List<NotificationsEntity> findByReadFalse();

    // Ejemplo de método personalizado para buscar notificaciones creadas después de una fecha específica
    // List<NotificationsEntity> findByCreatedAtAfter(Timestamp timestamp);
}