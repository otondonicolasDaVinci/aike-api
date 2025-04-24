package com.tesis.aike.repository;

import com.tesis.aike.model.entity.CabinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CabinRepository extends JpaRepository<CabinEntity, Integer> {
    List<CabinEntity> findByAvailableTrue();

    List<CabinEntity> findAll();

    List<CabinEntity> findByName(String name);

    List<CabinEntity> findByCapacity(int capacity);

    List<CabinEntity> findByDescriptionContaining(String keyword);

    List<CabinEntity> findByAvailableTrueAndCapacityGreaterThan(int capacity);
}