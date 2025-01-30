package com.hms.service.impl;

import com.hms.entity.Lease;
import com.hms.enums.LeaseStatus;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.LeaseRepository;
import com.hms.service.LeaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class LeaseServiceImpl implements LeaseService {

    private final LeaseRepository leaseRepository;

    @Override
    public Lease createLease(Lease lease) {
        lease.setStatus(LeaseStatus.PENDING);
        return leaseRepository.save(lease);
    }

    @Override
    public Lease updateLease(UUID id, Lease lease) {
        Lease existingLease = getLeaseById(id);
        lease.setId(id);
        return leaseRepository.save(lease);
    }

    @Override
    public void deleteLease(UUID id) {
        Lease lease = getLeaseById(id);
        leaseRepository.delete(lease);
    }

    @Override
    @Transactional(readOnly = true)
    public Lease getLeaseById(UUID id) {
        return leaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lease not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lease> getAllLeases() {
        return leaseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lease> getLeasesByProperty(UUID propertyId) {
        return leaseRepository.findByPropertyId(propertyId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lease> getLeasesByTenant(UUID tenantId) {
        return leaseRepository.findByTenantId(tenantId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lease> getLeasesByStatus(LeaseStatus status) {
        return leaseRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lease> getActiveLeases() {
        return leaseRepository.findByStatus(LeaseStatus.ACTIVE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lease> getExpiringLeases() {
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysFromNow = today.plusDays(30);
        return leaseRepository.findLeasesExpiringBetween(today, thirtyDaysFromNow);
    }

    @Override
    public Lease renewLease(UUID id, LocalDate newEndDate) {
        Lease lease = getLeaseById(id);
        if (lease.getStatus() != LeaseStatus.ACTIVE) {
            throw new IllegalStateException("Only active leases can be renewed");
        }
        lease.setEndDate(newEndDate);
        lease.setStatus(LeaseStatus.RENEWED);
        lease.setRenewalDate(LocalDate.now());
        return leaseRepository.save(lease);
    }

    @Override
    public Lease terminateLease(UUID id, LocalDate terminationDate) {
        Lease lease = getLeaseById(id);
        if (lease.getStatus() != LeaseStatus.ACTIVE) {
            throw new IllegalStateException("Only active leases can be terminated");
        }
        lease.setEndDate(terminationDate);
        lease.setStatus(LeaseStatus.TERMINATED);
        lease.setTerminationDate(LocalDate.now());
        return leaseRepository.save(lease);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lease> searchLeases(String keyword) {
        // Implement search functionality based on lease number or other relevant fields
        return leaseRepository.findByLeaseNumberContainingIgnoreCase(keyword);
    }
}