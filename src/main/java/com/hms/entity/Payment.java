package com.hms.entity;

import com.hms.enums.PaymentStatus;
import com.hms.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lease_id")
    private Lease lease;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private User tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    private LocalDate paymentDate;
    private LocalDate dueDate;
    private LocalDate processedDate;
    private LocalDate cancelledDate;

    private String description;
    private String referenceNumber;
    private String cancellationReason;

    @PrePersist
    private void generateReferenceNumber() {
        if (referenceNumber == null) {
            // Format: PAY-YYYYMMDD-XXXX (XXXX is a random number)
            String dateStr = LocalDate.now().toString().replace("-", "");
            String random = String.format("%04d", (int) (Math.random() * 10000));
            this.referenceNumber = "PAY-" + dateStr + "-" + random;
        }
    }
}