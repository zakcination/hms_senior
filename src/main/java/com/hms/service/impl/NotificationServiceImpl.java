package com.hms.service.impl;

import com.hms.entity.Notification;
import com.hms.entity.Notification.NotificationStatus;
import com.hms.entity.Notification.NotificationType;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.NotificationRepository;
import com.hms.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> broadcastNotification(Notification notification, List<UUID> userIds) {
        List<Notification> notifications = new ArrayList<>();
        for (UUID userId : userIds) {
            Notification newNotification = Notification.builder()
                    .user(notification.getUser())
                    .message(notification.getMessage())
                    .status(Notification.NotificationStatus.UNREAD)
                    .notificationType(notification.getNotificationType())
                    .entityType(notification.getEntityType())
                    .relatedEntityId(notification.getRelatedEntityId())
                    .build();
            notifications.add(notificationRepository.save(newNotification));
        }
        return notifications;
    }

    @Override
    public void deleteNotification(UUID id) {
        Notification notification = getNotificationById(id);
        notificationRepository.delete(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public Notification getNotificationById(UUID id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notification> getUserNotifications(UUID userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notification> getUnreadNotifications(UUID userId) {
        return notificationRepository.findUnreadNotifications(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notification> getNotificationsByType(String type) {
        return notificationRepository.findByUserIdAndNotificationType(null,
                Notification.NotificationType.valueOf(type));
    }

    @Override
    public Notification markAsRead(UUID id) {
        Notification notification = getNotificationById(id);
        notification.setStatus(Notification.NotificationStatus.READ);
        return notificationRepository.save(notification);
    }

    @Override
    public void markAllAsRead(UUID userId) {
        List<Notification> unreadNotifications = notificationRepository.findByUserIdAndStatus(userId,
                Notification.NotificationStatus.UNREAD);
        for (Notification notification : unreadNotifications) {
            notification.setStatus(Notification.NotificationStatus.READ);
            notificationRepository.save(notification);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notification> searchNotifications(String keyword) {
        // This is a simple implementation. You might want to enhance it based on your
        // requirements
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(null, PageRequest.of(0, 100)).getContent();
    }

    @Override
    public void deleteUserNotifications(UUID userId) {
        List<Notification> userNotifications = notificationRepository.findByUserId(userId);
        notificationRepository.deleteAll(userNotifications);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getUnreadNotificationCount(UUID userId) {
        return notificationRepository.countUnreadNotifications(userId);
    }
}