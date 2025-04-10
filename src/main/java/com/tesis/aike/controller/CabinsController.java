package com.tesis.aike.controller; // Asegúrate de usar el paquete correcto para tus controladores

import com.tesis.aike.model.entity.CabinsEntity;
import com.tesis.aike.service.CabinsService; // Asegúrate de que la ruta a tu servicio sea correcta
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cabins")
public class CabinsController {

    @Autowired
    private CabinsService cabinsService;

    @GetMapping
    public ResponseEntity<List<CabinsEntity>> obtenerTodasLasCabinas() {
        List<CabinsEntity> cabins = cabinsService.obtenerTodasLasCabinas();
        return new ResponseEntity<>(cabins, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CabinsEntity> obtenerCabinaPorId(@PathVariable int id) {
        Optional<CabinsEntity> cabin = Optional.ofNullable(cabinsService.obtenerCabinaPorId(id));
        return cabin.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<CabinsEntity> crearCabina(@RequestBody CabinsEntity cabin) {
        CabinsEntity nuevaCabina = cabinsService.guardarCabina(cabin);
        return new ResponseEntity<>(nuevaCabina, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CabinsEntity> actualizarCabina(@PathVariable int id, @RequestBody CabinsEntity cabinActualizada) {
        Optional<CabinsEntity> cabinExistente = Optional.ofNullable(cabinsService.obtenerCabinaPorId(id));
        if (cabinExistente.isPresent()) {
            cabinActualizada.setId(id);
            CabinsEntity cabinaActualizada = cabinsService.guardarCabina(cabinActualizada);
            return new ResponseEntity<>(cabinaActualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCabina(@PathVariable int id) {
        if (cabinsService.obtenerCabinaPorId(id).isPresent()) {
            cabinsService.eliminarCabina(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}