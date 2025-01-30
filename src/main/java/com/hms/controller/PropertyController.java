package com.hms.controller;

import com.hms.entity.Property;
import com.hms.enums.PropertyStatus;
import com.hms.enums.PropertyType;
import com.hms.enums.RoleType;
import com.hms.service.PropertyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/properties")
@RequiredArgsConstructor
@Tag(name = "Property Management", description = "APIs for managing properties in the system")
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping
    @Operation(summary = "Create a new property", description = "Creates a new property in the system")
    public ResponseEntity<Property> createProperty(@RequestBody Property property) {
        return new ResponseEntity<>(propertyService.createProperty(property), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a property", description = "Updates an existing property by ID")
    public ResponseEntity<Property> updateProperty(
            @Parameter(description = "Property ID") @PathVariable UUID id,
            @RequestBody Property property) {
        return ResponseEntity.ok(propertyService.updateProperty(id, property));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a property", description = "Deletes a property by ID")
    public ResponseEntity<Void> deleteProperty(
            @Parameter(description = "Property ID") @PathVariable UUID id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a property by ID", description = "Retrieves a property by its ID")
    public ResponseEntity<Property> getProperty(
            @Parameter(description = "Property ID") @PathVariable UUID id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }

    @GetMapping
    @Operation(summary = "Get all properties", description = "Retrieves all properties in the system")
    public ResponseEntity<List<Property>> getAllProperties() {
        return ResponseEntity.ok(propertyService.getAllProperties());
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Get properties by type", description = "Retrieves all properties of a specific type")
    public ResponseEntity<List<Property>> getPropertiesByType(
            @Parameter(description = "Property type") @PathVariable PropertyType type) {
        return ResponseEntity.ok(propertyService.getPropertiesByType(type));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get properties by status", description = "Retrieves all properties with a specific status")
    public ResponseEntity<List<Property>> getPropertiesByStatus(
            @Parameter(description = "Property status") @PathVariable PropertyStatus status) {
        return ResponseEntity.ok(propertyService.getPropertiesByStatus(status));
    }

    @GetMapping("/search")
    @Operation(summary = "Search properties", description = "Searches properties by name, address, or description")
    public ResponseEntity<List<Property>> searchProperties(
            @Parameter(description = "Search keyword") @RequestParam String keyword) {
        return ResponseEntity.ok(propertyService.searchProperties(keyword));
    }

    @GetMapping("/available")
    @Operation(summary = "Get available properties", description = "Retrieves all available properties for lease")
    public ResponseEntity<List<Property>> getAvailableProperties() {
        return ResponseEntity.ok(propertyService.getAvailableProperties());
    }

    @GetMapping("/owner/{ownerId}")
    @Operation(summary = "Get properties by owner", description = "Retrieves all properties owned by a specific user")
    public ResponseEntity<List<Property>> getPropertiesByOwner(
            @Parameter(description = "Owner ID") @PathVariable UUID ownerId) {
        return ResponseEntity.ok(propertyService.getPropertiesByOwner(ownerId));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update property status", description = "Updates the status of a property")
    public ResponseEntity<Property> updatePropertyStatus(
            @Parameter(description = "Property ID") @PathVariable UUID id,
            @Parameter(description = "Property status") @RequestParam PropertyStatus status) {
        return ResponseEntity.ok(propertyService.updatePropertyStatus(id, status));
    }

    @GetMapping("/price-range")
    @Operation(summary = "Get properties by price range", description = "Retrieves properties within a specified price range")
    public ResponseEntity<List<Property>> getPropertiesByPriceRange(
            @Parameter(description = "Minimum price") @RequestParam Double minPrice,
            @Parameter(description = "Maximum price") @RequestParam Double maxPrice) {
        return ResponseEntity.ok(propertyService.getPropertiesByPriceRange(minPrice, maxPrice));
    }

    @GetMapping("/available/{tenantType}")
    @Operation(summary = "Get available properties for tenant type", description = "Retrieves available properties suitable for a specific tenant type")
    public ResponseEntity<List<Property>> getAvailablePropertiesForTenant(
            @Parameter(description = "Tenant type") @PathVariable RoleType tenantType) {
        return ResponseEntity.ok(propertyService.getAvailablePropertiesForTenant(tenantType));
    }

    @GetMapping("/shared")
    @Operation(summary = "Get shared properties", description = "Retrieves shared properties with minimum occupants")
    public ResponseEntity<List<Property>> getSharedProperties(
            @Parameter(description = "Minimum occupants") @RequestParam Integer minOccupants) {
        return ResponseEntity.ok(propertyService.getSharedProperties(minOccupants));
    }
}