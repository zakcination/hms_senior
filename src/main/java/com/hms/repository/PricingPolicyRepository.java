package com.hms.repository;

import com.hms.entity.PricingPolicy;
import com.hms.enums.PolicyStatus;
import com.hms.enums.PropertyType;
import com.hms.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PricingPolicyRepository extends JpaRepository<PricingPolicy, UUID> {

       List<PricingPolicy> findByPropertyId(UUID propertyId);

       List<PricingPolicy> findByType(String type);

       List<PricingPolicy> findByStatus(PolicyStatus status);

       List<PricingPolicy> findByPropertyIdAndStatus(UUID propertyId, PolicyStatus status);

       List<PricingPolicy> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name,
                     String description);

       Optional<PricingPolicy> findByPolicyNumber(String policyNumber);

       List<PricingPolicy> findByPropertyType(PropertyType propertyType);

       List<PricingPolicy> findByRoomType(RoomType roomType);

       @Query("SELECT DISTINCT p.propertyType FROM PricingPolicy p WHERE p.status = 'ACTIVE' AND p.effectiveFrom <= :date AND p.effectiveTo >= :date")
       List<PropertyType> findActivePropertyTypes(@Param("date") LocalDate date);

       @Query("SELECT p FROM PricingPolicy p WHERE p.propertyId = :propertyId AND p.status = 'ACTIVE' AND ((p.effectiveFrom BETWEEN p.effectiveFrom AND p.effectiveTo) OR (p.effectiveTo BETWEEN p.effectiveFrom AND p.effectiveTo))")
       List<PricingPolicy> findConflictingPolicies(@Param("propertyId") UUID propertyId);
}