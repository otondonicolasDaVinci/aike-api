package com.tesis.aike.service;

import com.tesis.aike.model.entity.NotificationsEntity;
import com.tesis.aike.repository.NotificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationsRepository notificationsRepository;

    public NotificationsEntity guardarNotificacion(NotificationsEntity notification) {
        return notificationsRepository.save(notification);
    }

    public NotificationsEntity obtenerNotificacionPorId(int id) {
        return notificationsRepository.findById(id).orElse(null);
    }

    public List<NotificationsEntity> obtenerNotificacionesPorUsuario(int userId) {
        // Si agregaste el método personalizado findByUserId al repositorio
        // return notificationsRepository.findByUserId(userId);
        return null; // Implementa la lógica si es necesario
    }

    public List<NotificationsEntity> obtenerNotificacionesNoLeidas() {
        // Si agregaste el método personalizado findByReadFalse al repositorio
        // return notificationsRepository.findByReadFalse();
        return null; // Implementa la lógica si es necesario
    }

    // ... otros métodos relacionados con las notificaciones
}