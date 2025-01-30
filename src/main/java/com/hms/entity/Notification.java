package com.hms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private UUID relatedEntityId;

    @Enumerated(EnumType.STRING)
    private EntityType entityType;

    @Column(columnDefinition = "text", nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime readAt;

    private LocalDateTime deletedAt;

    public enum EntityType {
        LEASE,
        PAYMENT,
        MAINTENANCE_REQUEST,
        PROPERTY,
        USER
    }

    public enum NotificationStatus {
        UNREAD,
        READ,
        DISMISSED
    }

    public enum NotificationType {
        INFO,
        WARNING,
        CRITICAL
    }

    @PrePersist
    private void initializeNotification() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (status == null) {
            status = NotificationStatus.UNREAD;
        }
    }

    @PreUpdate
    private void updateTimestamps() {
        if (status == NotificationStatus.READ && readAt == null) {
            readAt = LocalDateTime.now();
        }
        if (status == NotificationStatus.DISMISSED && deletedAt == null) {
            deletedAt = LocalDateTime.now();
        }
    }
}