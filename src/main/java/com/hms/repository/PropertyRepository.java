package com.hms.repository;

import com.hms.entity.Property;
import com.hms.enums.PropertyStatus;
import com.hms.enums.PropertyType;
import com.hms.enums.RoleType;
import com.hms.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PropertyRepository extends JpaRepository<Property, UUID> {
    Optional<Property> findByPropertyNumber(String propertyNumber);

    List<Property> findByPropertyType(PropertyType propertyType);

    List<Property> findByStatus(PropertyStatus status);

    List<Property> findByRoomType(RoomType roomType);

    Optional<Property> findByName(String name);

    @Query("SELECT p FROM Property p JOIN p.allowedTenantTypes t WHERE t = :tenantType")
    List<Property> findByAllowedTenantType(RoleType tenantType);

    List<Property> findByRentLessThanEqual(Double maxRent);

    List<Property> findByIsShared(Boolean isShared);

    @Query("SELECT p FROM Property p WHERE p.status = :status AND :tenantType MEMBER OF p.allowedTenantTypes")
    List<Property> findAvailablePropertiesForTenant(PropertyStatus status, RoleType tenantType);

    List<Property> findByIsSharedAndMaxOccupantsGreaterThanEqual(Boolean isShared, Integer minOccupants);

    List<Property> findByOwnerId(UUID ownerId);

    @Query("SELECT p FROM Property p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword% OR p.address LIKE %:keyword%")
    List<Property> searchProperties(@Param("keyword") String keyword);

    @Query("SELECT p FROM Property p WHERE p.status = 'AVAILABLE' AND p.allowedTenantTypes LIKE %:tenantType%")
    List<Property> findAvailablePropertiesForTenant(@Param("tenantType") RoleType tenantType);

    @Query("SELECT p FROM Property p WHERE p.isShared = true AND p.maxOccupants >= :minOccupants")
    List<Property> findSharedProperties(@Param("minOccupants") Integer minOccupants);

    List<Property> findByRentBetween(Double minPrice, Double maxPrice);

    @Query("SELECT p FROM Property p WHERE p.status = 'AVAILABLE'")
    List<Property> findAvailableProperties();
}