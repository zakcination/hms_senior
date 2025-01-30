package com.hms.service.impl;

import com.hms.entity.PricingPolicy;
import com.hms.enums.PolicyStatus;
import com.hms.enums.PropertyType;
import com.hms.enums.RoomType;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.PricingPolicyRepository;
import com.hms.service.PricingPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PricingPolicyServiceImpl implements PricingPolicyService {

    private final PricingPolicyRepository pricingPolicyRepository;

    @Override
    public PricingPolicy createPolicy(PricingPolicy policy) {
        return pricingPolicyRepository.save(policy);
    }

    @Override
    public PricingPolicy updatePolicy(UUID id, PricingPolicy policy) {
        PricingPolicy existingPolicy = getPolicyById(id);
        policy.setId(id);
        return pricingPolicyRepository.save(policy);
    }

    @Override
    public void deletePolicy(UUID id) {
        PricingPolicy policy = getPolicyById(id);
        pricingPolicyRepository.delete(policy);
    }

    @Override
    @Transactional(readOnly = true)
    public PricingPolicy getPolicyById(UUID id) {
        return pricingPolicyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pricing policy not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PricingPolicy> getAllPolicies() {
        return pricingPolicyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PricingPolicy> getPoliciesByProperty(UUID propertyId) {
        return pricingPolicyRepository.findByPropertyId(propertyId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PricingPolicy> getPoliciesByType(String type) {
        return pricingPolicyRepository.findByType(type);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PricingPolicy> getPoliciesByStatus(String status) {
        return pricingPolicyRepository.findByStatus(PolicyStatus.valueOf(status));
    }

    @Override
    public PricingPolicy activatePolicy(UUID id) {
        PricingPolicy policy = getPolicyById(id);
        policy.setStatus(PolicyStatus.ACTIVE);
        policy.setActivationDate(LocalDate.now());
        return pricingPolicyRepository.save(policy);
    }

    @Override
    public PricingPolicy deactivatePolicy(UUID id) {
        PricingPolicy policy = getPolicyById(id);
        policy.setStatus(PolicyStatus.INACTIVE);
        policy.setDeactivationDate(LocalDate.now());
        return pricingPolicyRepository.save(policy);
    }

    @Override
    public Double calculatePrice(UUID propertyId, Integer days) {
        List<PricingPolicy> activePolicies = pricingPolicyRepository.findByPropertyIdAndStatus(propertyId,
                PolicyStatus.ACTIVE);
        return activePolicies.stream()
                .mapToDouble(policy -> calculatePolicyPrice(policy, days))
                .sum();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PricingPolicy> searchPolicies(String keyword) {
        return pricingPolicyRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword,
                keyword);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PricingPolicy> getActivePolicies() {
        return pricingPolicyRepository.findByStatus(PolicyStatus.ACTIVE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PricingPolicy> checkPolicyConflicts(UUID propertyId) {
        return pricingPolicyRepository.findConflictingPolicies(propertyId);
    }

    @Override
    @Transactional(readOnly = true)
    public PricingPolicy getPolicyByNumber(String policyNumber) {
        return pricingPolicyRepository.findByPolicyNumber(policyNumber)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Pricing policy not found with number: " + policyNumber));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PricingPolicy> getPoliciesByPropertyType(PropertyType propertyType) {
        return pricingPolicyRepository.findByPropertyType(propertyType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PricingPolicy> getPoliciesByRoomType(RoomType roomType) {
        return pricingPolicyRepository.findByRoomType(roomType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PropertyType> getActivePropertyTypes(LocalDate date) {
        return pricingPolicyRepository.findActivePropertyTypes(date);
    }

    private double calculatePolicyPrice(PricingPolicy policy, int days) {
        double basePrice = policy.getBasePrice();
        double discount = policy.getDiscount() != null ? policy.getDiscount() : 0.0;
        return (basePrice * days) * (1 - discount / 100);
    }
}