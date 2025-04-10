package com.tesis.aike.repository; // Asegúrate de usar el paquete correcto para tus repositorios

import com.tesis.aike.model.entity.RolesEntity; // Asegúrate de que la ruta a tu entidad sea correcta
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, Integer> {
    // JpaRepository proporciona métodos CRUD básicos (save, findById, findAll, deleteById, etc.)
    // Puedes agregar métodos personalizados aquí si necesitas consultas específicas para la entidad Roles.

    // Ejemplo de método personalizado para buscar un rol por su nombre
    Optional<RolesEntity> findByName(String name);
}