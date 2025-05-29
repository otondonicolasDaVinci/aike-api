package com.tesis.aike.repository; // Asegúrate de usar el paquete correcto para tus repositorios

import com.tesis.aike.model.entity.UsersEntity; // Asegúrate de que la ruta a tu entidad sea correcta
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {

    Optional<UsersEntity> findByEmail(String email);

    Optional<UsersEntity> findByDni(String dni);

    java.util.List<UsersEntity> findByRoleId(Long roleId); // TODO: ???

    Optional<UsersEntity> findByName(String name);
}