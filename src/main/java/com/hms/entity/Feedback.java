package com.hms.entity;

import com.hms.enums.FeedbackType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "feedbacks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feedback extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "text")
    private String comment;

    @Column(nullable = false)
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(nullable = false)
    private Boolean isAnonymous = false;

    @Column(nullable = false)
    private Boolean isResolved = false;

    @Column(name = "entity_type")
    private String relatedEntityType;

    @Column(name = "entity_id")
    private UUID relatedEntityId;

    @Column(columnDefinition = "text")
    private String adminResponse;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FeedbackType type;

    @Column(nullable = false)
    private LocalDateTime submittedAt;

    @PrePersist
    private void prePersist() {
        if (submittedAt == null) {
            submittedAt = LocalDateTime.now();
        }
        if (isAnonymous == null) {
            isAnonymous = false;
        }
        if (isResolved == null) {
            isResolved = false;
        }
    }
}