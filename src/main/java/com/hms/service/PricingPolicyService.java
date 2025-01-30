package com.hms.service;

import com.hms.entity.PricingPolicy;
import com.hms.enums.PropertyType;
import com.hms.enums.RoomType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PricingPolicyService {
    PricingPolicy createPolicy(PricingPolicy policy);

    PricingPolicy updatePolicy(UUID id, PricingPolicy policy);

    void deletePolicy(UUID id);

    PricingPolicy getPolicyById(UUID id);

    List<PricingPolicy> getAllPolicies();

    List<PricingPolicy> getPoliciesByProperty(UUID propertyId);

    List<PricingPolicy> getPoliciesByType(String type);

    List<PricingPolicy> getPoliciesByStatus(String status);

    PricingPolicy activatePolicy(UUID id);

    PricingPolicy deactivatePolicy(UUID id);

    Double calculatePrice(UUID propertyId, Integer days);

    List<PricingPolicy> searchPolicies(String keyword);

    List<PricingPolicy> getActivePolicies();

    List<PricingPolicy> checkPolicyConflicts(UUID propertyId);

    PricingPolicy getPolicyByNumber(String policyNumber);

    List<PricingPolicy> getPoliciesByPropertyType(PropertyType propertyType);

    List<PricingPolicy> getPoliciesByRoomType(RoomType roomType);

    List<PropertyType> getActivePropertyTypes(LocalDate date);
}