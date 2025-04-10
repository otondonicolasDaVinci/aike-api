package com.tesis.aike.controller; // Asegúrate de usar el paquete correcto para tus controladores

import com.tesis.aike.model.entity.NotificationsEntity;
import com.tesis.aike.service.NotificationsService; // Asegúrate de que la ruta a tu servicio sea correcta
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationsController {

    @Autowired
    private NotificationsService notificationsService;

    @GetMapping
    public ResponseEntity<List<NotificationsEntity>> obtenerTodasLasNotificaciones() {
        List<NotificationsEntity> notifications = notificationsService.obtenerTodasLasNotificaciones();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationsEntity> obtenerNotificacionPorId(@PathVariable int id) {
        Optional<NotificationsEntity> notification = notificationsService.obtenerNotificacionPorId(id);
        return notification.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<NotificationsEntity> crearNotificacion(@RequestBody NotificationsEntity notification) {
        NotificationsEntity nuevaNotificacion = notificationsService.guardarNotificacion(notification);
        return new ResponseEntity<>(nuevaNotificacion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationsEntity> actualizarNotificacion(@PathVariable int id, @RequestBody NotificationsEntity notificationActualizada) {
        Optional<NotificationsEntity> notificationExistente = notificationsService.obtenerNotificacionPorId(id);
        if (notificationExistente.isPresent()) {
            notificationActualizada.setId(id);
            NotificationsEntity notificacionActualizada = notificationsService.guardarNotificacion(notificationActualizada);
            return new ResponseEntity<>(notificacionActualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable int id) {
        if (notificationsService.obtenerNotificacionPorId(id).isPresent()) {
            notificationsService.eliminarNotificacion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}