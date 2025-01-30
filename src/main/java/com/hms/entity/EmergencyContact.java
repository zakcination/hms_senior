package com.hms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "emergency_contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyContact extends BaseEntity {

    @Column(nullable = false)
    private String serviceName;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String primaryPhone;

    private String secondaryPhone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String workingHours;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private Boolean isAvailable24x7;

    @Column(columnDefinition = "text")
    private String emergencyProcedures;

    private String website;

    @Column(columnDefinition = "text")
    private String additionalInformation;

    @Column(nullable = false)
    private Integer priorityLevel; // 1 (highest) to 5 (lowest)

    @PrePersist
    private void initializeContact() {
        if (isAvailable24x7 == null) {
            isAvailable24x7 = false;
        }
        if (priorityLevel == null) {
            priorityLevel = 5;
        }
    }
}