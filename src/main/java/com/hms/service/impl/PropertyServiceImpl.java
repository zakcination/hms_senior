package com.hms.service.impl;

import com.hms.entity.Property;
import com.hms.enums.PropertyStatus;
import com.hms.enums.PropertyType;
import com.hms.enums.RoleType;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.PropertyRepository;
import com.hms.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    @Override
    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }

    @Override
    public Property updateProperty(UUID id, Property property) {
        Property existingProperty = getPropertyById(id);
        property.setId(id);
        return propertyRepository.save(property);
    }

    @Override
    public void deleteProperty(UUID id) {
        Property property = getPropertyById(id);
        propertyRepository.delete(property);
    }

    @Override
    @Transactional(readOnly = true)
    public Property getPropertyById(UUID id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Property> getPropertiesByType(PropertyType type) {
        return propertyRepository.findByPropertyType(type);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Property> getPropertiesByStatus(PropertyStatus status) {
        return propertyRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Property> searchProperties(String keyword) {
        return propertyRepository.searchProperties(keyword);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Property> getAvailableProperties() {
        return propertyRepository.findAvailableProperties();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Property> getPropertiesByOwner(UUID ownerId) {
        return propertyRepository.findByOwnerId(ownerId);
    }

    @Override
    public Property updatePropertyStatus(UUID id, PropertyStatus status) {
        Property property = getPropertyById(id);
        property.setStatus(status);
        return propertyRepository.save(property);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Property> getPropertiesByPriceRange(Double minPrice, Double maxPrice) {
        return propertyRepository.findByRentBetween(minPrice, maxPrice);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Property> getAvailablePropertiesForTenant(RoleType tenantType) {
        return propertyRepository.findAvailablePropertiesForTenant(tenantType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Property> getSharedProperties(Integer minOccupants) {
        return propertyRepository.findSharedProperties(minOccupants);
    }
}