package com.hms.repository;

import com.hms.entity.Payment;
import com.hms.entity.Property;
import com.hms.entity.User;
import com.hms.enums.PaymentStatus;
import com.hms.enums.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
        Optional<Payment> findByReferenceNumber(String referenceNumber);

        List<Payment> findByTenant(User tenant);

        List<Payment> findByProperty(Property property);

        List<Payment> findByStatus(PaymentStatus status);

        List<Payment> findByType(PaymentType type);

        List<Payment> findByDueDateBeforeAndStatus(LocalDate date, PaymentStatus status);

        List<Payment> findByTenantAndType(User tenant, PaymentType type);

        List<Payment> findByTenantId(UUID tenantId);

        List<Payment> findByPropertyId(UUID propertyId);

        List<Payment> findByLeaseId(UUID leaseId);

        List<Payment> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);

        List<Payment> findByDueDateBetweenAndStatus(LocalDate startDate, LocalDate endDate, PaymentStatus status);

        List<Payment> findByPropertyIdAndPaymentDateBetweenAndStatus(UUID propertyId, LocalDate startDate,
                        LocalDate endDate, PaymentStatus status);

        @Query("SELECT p FROM Payment p WHERE p.tenant.id = :tenantId AND p.dueDate <= :date AND p.status = 'PENDING'")
        List<Payment> findOverduePayments(@Param("tenantId") UUID tenantId, @Param("date") LocalDate date);

        @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.tenant.id = :tenantId AND p.status = 'COMPLETED'")
        Double calculateTotalPaymentsByUser(@Param("tenantId") UUID tenantId);

        @Query("SELECT p FROM Payment p WHERE p.property.id = :propertyId AND p.dueDate BETWEEN :startDate AND :endDate")
        List<Payment> findPaymentsByPropertyAndDateRange(@Param("propertyId") UUID propertyId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);
}