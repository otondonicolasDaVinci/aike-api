package com.tesis.aike.repository; // Asegúrate de usar el paquete correcto para tus repositorios

import com.tesis.aike.model.entity.UsersEntity; // Asegúrate de que la ruta a tu entidad sea correcta
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {
    // JpaRepository proporciona métodos CRUD básicos (save, findById, findAll, deleteById, etc.)
    // Puedes agregar métodos personalizados aquí si necesitas consultas específicas para la entidad Users.

    // Ejemplo de método personalizado para buscar un usuario por su email
    Optional<UsersEntity> findByEmail(String email);

    // Ejemplo de método personalizado para buscar un usuario por su DNI
    Optional<UsersEntity> findByDni(String dni);

    // Ejemplo de método personalizado para buscar todos los usuarios con un role_id específico
    java.util.List<UsersEntity> findByRoleId(int roleId);
}