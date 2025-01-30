package com.hms.entity;

import com.hms.enums.VisitStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "visitor_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitorLog extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String visitorPassNumber;

    @Column(nullable = false)
    private String visitorName;

    @Column(nullable = false)
    private String visitorIdentification;

    @Column(nullable = false)
    private String contactNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_user_id", nullable = false)
    private User hostUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Column(nullable = false)
    private LocalDateTime scheduledArrival;

    @Column(nullable = false)
    private LocalDateTime scheduledDeparture;

    private LocalDateTime actualArrival;

    private LocalDateTime actualDeparture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VisitStatus status;

    @Column(columnDefinition = "text")
    private String purpose;

    @Column(columnDefinition = "jsonb")
    private String vehicleDetails;

    @Column(columnDefinition = "jsonb")
    private String additionalVisitors;

    private String approvedBy;

    private LocalDateTime approvedAt;

    @Column(columnDefinition = "text")
    private String notes;

    @PrePersist
    private void generateVisitorPassNumber() {
        if (visitorPassNumber == null) {
            // Format: VP-YYYYMMDD-XXXX (XXXX is a random number)
            String dateStr = LocalDateTime.now().toString().substring(0, 10).replace("-", "");
            String random = String.format("%04d", (int) (Math.random() * 10000));
            this.visitorPassNumber = "VP-" + dateStr + "-" + random;
        }
    }

    @PreUpdate
    private void updateTimestamps() {
        if (status == VisitStatus.CHECKED_IN && actualArrival == null) {
            actualArrival = LocalDateTime.now();
        }
        if (status == VisitStatus.CHECKED_OUT && actualDeparture == null) {
            actualDeparture = LocalDateTime.now();
        }
    }
}