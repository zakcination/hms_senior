package com.hms.entity;

import com.hms.enums.PropertyStatus;
import com.hms.enums.PropertyType;
import com.hms.enums.RoleType;
import com.hms.enums.RoomType;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "properties")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String propertyNumber;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyType propertyType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(nullable = false)
    private Integer totalRooms = 0;

    @Column(nullable = false)
    private Integer availableRooms = 0;

    @Column(nullable = false)
    private Double area;

    @Column(nullable = false)
    private Double rent;

    @Column(nullable = false)
    private Boolean requiresDeposit = false;

    private Double depositAmount;

    @Column(columnDefinition = "jsonb")
    private String amenities;

    @Column(columnDefinition = "jsonb")
    private String rules;

    @Column(columnDefinition = "jsonb")
    private String images;

    @Column(columnDefinition = "jsonb")
    private String utilities;

    @Column(columnDefinition = "jsonb")
    private String maintenanceHistory;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Lease> leases = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "property_allowed_tenant_types", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "tenant_type")
    @Enumerated(EnumType.STRING)
    private Set<RoleType> allowedTenantTypes = new HashSet<>();

    @Column(nullable = false)
    private Integer maxOccupants;

    @Column(nullable = false)
    private Boolean isShared;

    @PrePersist
    private void generatePropertyNumber() {
        if (propertyNumber == null) {
            // Format: PROP-YYYYMMDD-XXXX (XXXX is a random number)
            String dateStr = LocalDate.now().toString().replace("-", "");
            String random = String.format("%04d", (int) (Math.random() * 10000));
            this.propertyNumber = "PROP-" + dateStr + "-" + random;
        }
    }

    @PreUpdate
    private void validatePropertyRules() {
        // Ensure available rooms don't exceed total rooms
        if (availableRooms > totalRooms) {
            availableRooms = totalRooms;
        }

        // Set deposit amount to 0 if deposit is not required
        if (!requiresDeposit) {
            depositAmount = 0.0;
        }

        // Validate property rules based on property type
        switch (propertyType) {
            case DORMITORY:
                isShared = true;
                requiresDeposit = false;
                allowedTenantTypes.clear();
                allowedTenantTypes.add(RoleType.STUDENT);
                break;
            case HV_APARTMENT:
            case CAMPUS_APARTMENT:
            case NL_APARTMENT:
                isShared = false;
                requiresDeposit = true;
                allowedTenantTypes.clear();
                allowedTenantTypes.add(RoleType.FACULTY);
                allowedTenantTypes.add(RoleType.STAFF);
                break;
            case TOWNHOUSE:
            case COTTAGE:
                isShared = false;
                requiresDeposit = true;
                allowedTenantTypes.clear();
                allowedTenantTypes.add(RoleType.FACULTY);
                break;
        }
    }

    // Explicit getter methods to ensure availability during compilation
    public Boolean getRequiresDeposit() {
        return requiresDeposit;
    }

    public Double getDepositAmount() {
        return depositAmount;
    }
}