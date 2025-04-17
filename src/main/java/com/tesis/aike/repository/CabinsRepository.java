package com.tesis.aike.repository; // Asegúrate de usar el paquete correcto para tus repositorios

import com.tesis.aike.model.entity.CabinsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CabinsRepository extends JpaRepository<CabinsEntity, Integer> {
    // JpaRepository proporciona métodos CRUD básicos (save, findById, findAll, deleteById, etc.)
    // Puedes agregar métodos personalizados aquí si necesitas consultas específicas para la entidad Cabins.

    // Ejemplo de método personalizado para buscar cabañas por nombre (si lo necesitas)
    // List<CabinsEntity> findByName(String name);

    // Ejemplo de método personalizado para buscar cabañas disponibles
    // List<CabinsEntity> findByAvailableTrue();

    // Ejemplo de método personalizado para buscar cabañas con una capacidad específica
    // List<CabinsEntity> findByCapacity(int capacity);

    List<CabinsEntity> findByAvailableTrue();

}