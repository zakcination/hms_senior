package com.hms.entity;

import com.hms.enums.AuditActionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog extends BaseEntity {

    @Column(nullable = false)
    private String entityType;

    @Column(nullable = false)
    private UUID entityId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditActionType actionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performed_by", nullable = false)
    private User performedBy;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(columnDefinition = "text")
    private String details;

    @Column(name = "ip_address")
    private String ipAddress;

    @PrePersist
    private void initializeTimestamp() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }
}