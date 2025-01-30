package com.hms.entity;

import com.hms.enums.PolicyStatus;
import com.hms.enums.PropertyType;
import com.hms.enums.RoomType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "pricing_policies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingPolicy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String policyNumber;

    private String name;
    private String description;
    private Double basePrice;
    private Double discount;

    @Enumerated(EnumType.STRING)
    private PolicyStatus status;

    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
    private LocalDate activationDate;
    private LocalDate deactivationDate;

    @Column(name = "property_id")
    private UUID propertyId;

    private String type;
    private Boolean hasStudentFundDiscount;

    @Column(nullable = false)
    private Double dailyRate;

    @Column(nullable = false)
    private Double monthlyRateWithUtilities;

    @Column(nullable = false)
    private Double monthlyRateWithoutUtilities;

    @Column(nullable = false)
    private Double studentFundDiscountRate;

    private Double otherDiscountRate;

    @Column(columnDefinition = "jsonb")
    private String utilityRates;

    @Column(columnDefinition = "jsonb")
    private String seasonalRates;

    @PrePersist
    private void generatePolicyNumber() {
        if (policyNumber == null) {
            // Format: POL-YYYYMMDD-XXXX (XXXX is a random number)
            String dateStr = LocalDate.now().toString().replace("-", "");
            String random = String.format("%04d", (int) (Math.random() * 10000));
            this.policyNumber = "POL-" + dateStr + "-" + random;
        }
    }

    @PreUpdate
    private void validatePolicy() {
        // Validate effective dates
        if (effectiveTo.isBefore(effectiveFrom)) {
            throw new IllegalStateException("Effective to date must be after effective from date");
        }

        // Set default student fund discount rate for dormitories
        if (propertyType == PropertyType.DORMITORY) {
            if (hasStudentFundDiscount && (studentFundDiscountRate == null || studentFundDiscountRate == 0.0)) {
                studentFundDiscountRate = 20.0; // 20% discount for student fund
            }
        } else {
            hasStudentFundDiscount = false;
            studentFundDiscountRate = 0.0;
        }
    }
}