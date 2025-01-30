package com.hms.entity;

import com.hms.enums.RoleGroup;
import com.hms.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String middleName;

    @Column(unique = true, nullable = false)
    private String nationalId;

    @Column(unique = true)
    private String nuid;

    @Column(nullable = false)
    private String citizenship;

    @Column(nullable = false)
    private String residencyCode;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IdentityDocType identityDocType;

    @Column(nullable = false)
    private String identityDocNo;

    @Column(nullable = false)
    private LocalDate identityIssueDate;

    @Column(nullable = false, unique = true)
    private String email;

    private String localPhone;

    private String mobilePhone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleGroup roleGroup;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    private String position;

    private String department;

    @Column(columnDefinition = "jsonb")
    private String familyMembers;

    @Column(columnDefinition = "jsonb")
    private String domesticStaff;

    @Column(columnDefinition = "jsonb")
    private String vehicles;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private boolean emailVerified = false;

    private String emailVerificationToken;

    private String passwordResetToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    private LocalDateTime lastLoginDate;

    @PrePersist
    private void prePersist() {
        if (!emailVerified && emailVerificationToken == null) {
            emailVerificationToken = UUID.randomUUID().toString();
        }
    }

    public enum IdentityDocType {
        NATIONAL_ID,
        PASSPORT,
        DRIVERS_LICENSE,
        STUDENT_ID
    }
}