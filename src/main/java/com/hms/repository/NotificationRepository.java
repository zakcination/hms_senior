package com.hms.repository;

import com.hms.entity.Notification;
import com.hms.entity.Notification.NotificationStatus;
import com.hms.entity.Notification.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    List<Notification> findByUserId(UUID userId);

    List<Notification> findByUserIdAndStatus(UUID userId, Notification.NotificationStatus status);

    List<Notification> findByUserIdAndNotificationType(UUID userId, Notification.NotificationType notificationType);

    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId AND n.status = 'UNREAD'")
    List<Notification> findUnreadNotifications(@Param("userId") UUID userId);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.user.id = :userId AND n.status = 'UNREAD'")
    Long countUnreadNotifications(@Param("userId") UUID userId);

    Page<Notification> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);

    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId AND n.notificationType = 'CRITICAL' AND n.status = 'UNREAD'")
    List<Notification> findImportantUnreadNotifications(@Param("userId") UUID userId);

    @Query("SELECT n FROM Notification n WHERE n.entityType = :entityType AND n.relatedEntityId = :entityId")
    List<Notification> findByRelatedEntity(@Param("entityType") String entityType, @Param("entityId") UUID entityId);

    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId AND n.createdAt >= :since")
    List<Notification> findRecentNotifications(@Param("userId") UUID userId, @Param("since") LocalDateTime since);

    void deleteByUserIdAndStatusAndCreatedAtBefore(UUID userId, Notification.NotificationStatus status,
            LocalDateTime date);
}