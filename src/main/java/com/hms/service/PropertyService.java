package com.hms.service;

import com.hms.entity.Property;
import com.hms.enums.PropertyStatus;
import com.hms.enums.PropertyType;
import com.hms.enums.RoleType;

import java.util.List;
import java.util.UUID;

public interface PropertyService {
    Property createProperty(Property property);

    Property updateProperty(UUID id, Property property);

    void deleteProperty(UUID id);

    Property getPropertyById(UUID id);

    List<Property> getAllProperties();

    List<Property> getPropertiesByType(PropertyType type);

    List<Property> getPropertiesByStatus(PropertyStatus status);

    List<Property> searchProperties(String keyword);

    List<Property> getAvailableProperties();

    List<Property> getPropertiesByOwner(UUID ownerId);

    Property updatePropertyStatus(UUID id, PropertyStatus status);

    List<Property> getPropertiesByPriceRange(Double minPrice, Double maxPrice);

    List<Property> getAvailablePropertiesForTenant(RoleType tenantType);

    List<Property> getSharedProperties(Integer minOccupants);
}