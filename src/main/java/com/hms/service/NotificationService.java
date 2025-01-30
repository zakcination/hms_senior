package com.hms.service;

import com.hms.entity.Notification;
import java.util.List;
import java.util.UUID;

public interface NotificationService {
    Notification createNotification(Notification notification);

    List<Notification> broadcastNotification(Notification notification, List<UUID> userIds);

    void deleteNotification(UUID id);

    Notification getNotificationById(UUID id);

    List<Notification> getAllNotifications();

    List<Notification> getUserNotifications(UUID userId);

    List<Notification> getUnreadNotifications(UUID userId);

    List<Notification> getNotificationsByType(String type);

    Notification markAsRead(UUID id);

    void markAllAsRead(UUID userId);

    List<Notification> searchNotifications(String keyword);

    void deleteUserNotifications(UUID userId);

    Long getUnreadNotificationCount(UUID userId);
}