package com.tesis.aike.controller; // Asegúrate de usar el paquete correcto para tus controladores

import com.tesis.aike.model.entity.RolesEntity;
import com.tesis.aike.service.RolesService; // Asegúrate de que la ruta a tu servicio sea correcta
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    @Autowired
    private RolesService rolesService;

    @GetMapping
    public ResponseEntity<List<RolesEntity>> obtenerTodosLosRoles() {
        List<RolesEntity> roles = rolesService.obtenerTodosLosRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolesEntity> obtenerRolPorId(@PathVariable int id) {
        Optional<RolesEntity> role = rolesService.obtenerRolPorId(id);
        return role.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<RolesEntity> crearRol(@RequestBody RolesEntity role) {
        RolesEntity nuevoRol = rolesService.guardarRol(role);
        return new ResponseEntity<>(nuevoRol, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolesEntity> actualizarRol(@PathVariable int id, @RequestBody RolesEntity roleActualizado) {
        Optional<RolesEntity> roleExistente = rolesService.obtenerRolPorId(id);
        if (roleExistente.isPresent()) {
            roleActualizado.setId(id);
            RolesEntity rolActualizado = rolesService.guardarRol(roleActualizado);
            return new ResponseEntity<>(rolActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable int id) {
        if (rolesService.obtenerRolPorId(id).isPresent()) {
            rolesService.eliminarRol(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}