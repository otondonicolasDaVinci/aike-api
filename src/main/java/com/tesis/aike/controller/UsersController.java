package com.tesis.aike.controller; // Asegúrate de usar el paquete correcto para tus controladores

import com.tesis.aike.model.entity.UsersEntity;
import com.tesis.aike.service.UsersService; // Asegúrate de que la ruta a tu servicio sea correcta
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    public ResponseEntity<List<UsersEntity>> obtenerTodosLosUsuarios() {
        List<UsersEntity> users = usersService.obtenerTodosLosUsuarios();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersEntity> obtenerUsuarioPorId(@PathVariable int id) {
        Optional<UsersEntity> user = usersService.obtenerUsuarioPorId(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<UsersEntity> crearUsuario(@RequestBody UsersEntity user) {
        UsersEntity nuevoUsuario = usersService.guardarUsuario(user);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsersEntity> actualizarUsuario(@PathVariable int id, @RequestBody UsersEntity userActualizado) {
        Optional<UsersEntity> userExistente = usersService.obtenerUsuarioPorId(id);
        if (userExistente.isPresent()) {
            userActualizado.setId(id);
            UsersEntity usuarioActualizado = usersService.guardarUsuario(userActualizado);
            return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable int id) {
        if (usersService.obtenerUsuarioPorId(id).isPresent()) {
            usersService.eliminarUsuario(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}