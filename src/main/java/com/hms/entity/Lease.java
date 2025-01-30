package com.hms.entity;

import com.hms.enums.LeaseStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "leases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lease {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String leaseNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private User tenant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaseStatus status;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private LocalDate checkInDate;

    @Column(nullable = false)
    private LocalDate checkOutDate;

    private LocalDate terminationDate;
    private LocalDate renewalDate;

    @Column(nullable = false)
    private Double monthlyRent;

    @Column(nullable = false)
    private Double securityDeposit;

    @Column(nullable = false)
    private Integer leaseTerm;

    @Column(columnDefinition = "TEXT")
    private String terms;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(unique = true)
    private String contractNumber;

    @Column(nullable = false)
    private Boolean reservationStatus;

    private Double penalties;

    @Column(nullable = false)
    private Double deposit;

    @OneToMany(mappedBy = "lease", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Payment> paymentRecords = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        if (leaseNumber == null) {
            // Format: LSE-YYYYMMDD-XXXX (XXXX is a random number)
            String dateStr = LocalDate.now().toString().replace("-", "");
            String random = String.format("%04d", (int) (Math.random() * 10000));
            this.leaseNumber = "LSE-" + dateStr + "-" + random;
        }
    }

    @PreUpdate
    private void validateLeaseRules() {
        // Ensure check-out date is after check-in date
        if (checkOutDate != null && checkInDate != null && checkOutDate.isBefore(checkInDate)) {
            throw new IllegalStateException("Check-out date must be after check-in date");
        }

        // Set deposit based on property rules
        if (property != null) {
            if (property.getRequiresDeposit()) {
                if (deposit == null || deposit == 0.0) {
                    this.deposit = property.getDepositAmount();
                }
            } else {
                this.deposit = 0.0;
            }
        }
    }
}