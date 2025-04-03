package com.tesis.aike.repository;

import com.tesis.aike.model.entity.NotificationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationsEntity, Integer> {
    List<NotificationsEntity> findByUserId(int userId);

    List<NotificationsEntity> findByRead(boolean read);
}
