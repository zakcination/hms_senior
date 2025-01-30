package com.hms.repository;

import com.hms.entity.Lease;
import com.hms.entity.Property;
import com.hms.entity.User;
import com.hms.enums.LeaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LeaseRepository extends JpaRepository<Lease, UUID> {
        List<Lease> findByTenant(User tenant);

        List<Lease> findByProperty(Property property);

        List<Lease> findByStatus(LeaseStatus status);

        List<Lease> findByEndDateBefore(LocalDate date);

        Optional<Lease> findByLeaseNumber(String leaseNumber);

        List<Lease> findByTenantAndStatus(User tenant, LeaseStatus status);

        Optional<Lease> findByContractNumber(String contractNumber);

        List<Lease> findByTenantId(UUID tenantId);

        List<Lease> findByPropertyId(UUID propertyId);

        List<Lease> findByLeaseNumberContainingIgnoreCase(String keyword);

        @Query("SELECT l FROM Lease l WHERE l.checkOutDate >= :date AND l.status = 'ACTIVE'")
        List<Lease> findActiveLeasesByDate(@Param("date") LocalDate date);

        @Query("SELECT l FROM Lease l WHERE l.tenant.id = :tenantId AND l.status = 'ACTIVE'")
        List<Lease> findActiveLeasesByTenantId(@Param("tenantId") UUID tenantId);

        @Query("SELECT l FROM Lease l WHERE l.property.id = :propertyId AND l.status = 'ACTIVE'")
        List<Lease> findActiveLeasesByPropertyId(@Param("propertyId") UUID propertyId);

        @Query("SELECT COUNT(l) > 0 FROM Lease l WHERE l.property.id = :propertyId " +
                        "AND l.status = 'ACTIVE' AND :checkInDate < l.checkOutDate AND :checkOutDate > l.checkInDate")
        boolean isPropertyAvailableForPeriod(@Param("propertyId") UUID propertyId,
                        @Param("checkInDate") LocalDate checkInDate,
                        @Param("checkOutDate") LocalDate checkOutDate);

        @Query("SELECT l FROM Lease l WHERE l.endDate BETWEEN :startDate AND :endDate")
        List<Lease> findLeasesExpiringBetween(@Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);

        @Query("SELECT l FROM Lease l WHERE l.endDate < :date")
        List<Lease> findExpiredLeases(@Param("date") LocalDate date);

        @Query("SELECT l FROM Lease l WHERE l.status = 'ACTIVE'")
        List<Lease> findActiveLeases();
}