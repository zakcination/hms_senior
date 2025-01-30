package com.hms.entity;

import com.hms.enums.MaintenanceRequestType;
import com.hms.enums.MaintenanceStatus;
import com.hms.enums.Priority;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintenanceRequest extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String requestNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaintenanceRequestType requestType;

    @Column(columnDefinition = "text", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaintenanceStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Column(nullable = false)
    private LocalDateTime requestDate;

    private LocalDateTime scheduledDate;

    private LocalDateTime completionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Column(columnDefinition = "jsonb")
    private String assignedStaff;

    private Double cost;

    @Column(columnDefinition = "text")
    private String notes;

    @Column(columnDefinition = "jsonb")
    private String attachments;

    @Column(columnDefinition = "jsonb")
    private String workHistory;

    @PrePersist
    private void generateRequestNumber() {
        if (requestNumber == null) {
            // Format: MR-YYYYMMDD-XXXX (XXXX is a random number)
            String dateStr = LocalDateTime.now().toString().substring(0, 10).replace("-", "");
            String random = String.format("%04d", (int) (Math.random() * 10000));
            this.requestNumber = "MR-" + dateStr + "-" + random;
        }
    }

    @PreUpdate
    private void validateRequest() {
        // Set request date if not set
        if (requestDate == null) {
            requestDate = LocalDateTime.now();
        }

        // Update completion date when status changes to COMPLETED
        if (status == MaintenanceStatus.COMPLETED && completionDate == null) {
            completionDate = LocalDateTime.now();
        }

        // Validate scheduled date is after request date
        if (scheduledDate != null && scheduledDate.isBefore(requestDate)) {
            throw new IllegalStateException("Scheduled date must be after request date");
        }
    }
}