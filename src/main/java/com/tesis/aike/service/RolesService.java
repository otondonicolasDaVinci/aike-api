package com.tesis.aike.service; // Asegúrate de usar el paquete correcto para tus servicios

import com.tesis.aike.model.entity.RolesEntity; // Asegúrate de que la ruta a tu entidad sea correcta
import com.tesis.aike.repository.RolesRepository; // Asegúrate de que la ruta a tu repositorio sea correcta
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    public List<RolesEntity> obtenerTodosLosRoles() {
        return rolesRepository.findAll();
    }

    public Optional<RolesEntity> obtenerRolPorId(int id) {
        return rolesRepository.findById(id);
    }

    public Optional<RolesEntity> obtenerRolPorNombre(String name) {
        return rolesRepository.findByName(name);
    }

    public RolesEntity guardarRol(RolesEntity role) {
        return rolesRepository.save(role);
    }

    public void eliminarRol(int id) {
        rolesRepository.deleteById(id);
    }

    // Puedes agregar más métodos de lógica de negocio relacionados con los roles aquí
}