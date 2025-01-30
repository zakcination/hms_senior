package com.hms.service;

import com.hms.entity.Lease;
import com.hms.enums.LeaseStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LeaseService {
    Lease createLease(Lease lease);

    Lease updateLease(UUID id, Lease lease);

    void deleteLease(UUID id);

    Lease getLeaseById(UUID id);

    List<Lease> getAllLeases();

    List<Lease> getLeasesByProperty(UUID propertyId);

    List<Lease> getLeasesByTenant(UUID tenantId);

    List<Lease> getLeasesByStatus(LeaseStatus status);

    List<Lease> getActiveLeases();

    List<Lease> getExpiringLeases();

    Lease renewLease(UUID id, LocalDate newEndDate);

    Lease terminateLease(UUID id, LocalDate terminationDate);

    List<Lease> searchLeases(String keyword);
}