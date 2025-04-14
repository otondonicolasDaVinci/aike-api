package com.tesis.aike.repository;

import com.tesis.aike.model.entity.ReservationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ReservationsRepository extends JpaRepository<ReservationsEntity, Integer> {
    List<ReservationsEntity> findByUserId(int userId);

    List<ReservationsEntity> findByCabinId(int cabinId);

    List<ReservationsEntity> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(Date startDate, Date endDate);

    List<ReservationsEntity> findByStatus(String status);

    List<ReservationsEntity> findByStartDate(Date startDate);

    List<ReservationsEntity> findByEndDate(Date endDate);
}
