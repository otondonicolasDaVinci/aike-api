package com.tesis.aike.repository;

import com.tesis.aike.model.entity.ReservationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationsRepository extends JpaRepository<ReservationsEntity, Integer> {
    List<ReservationsEntity> findByUserId(Long userId);

    List<ReservationsEntity> findByCabinId(Long cabinId);

    List<ReservationsEntity> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate startDate, LocalDate endDate);

    List<ReservationsEntity> findByStatus(String status);

    List<ReservationsEntity> findByStartDate(LocalDate startDate);

    List<ReservationsEntity> findByEndDate(LocalDate endDate);

    boolean existsByCabinIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Long cabinId, LocalDate startDate, LocalDate endDate);
}
