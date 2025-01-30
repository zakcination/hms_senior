package com.hms.controller;

import com.hms.entity.PricingPolicy;
import com.hms.enums.PropertyType;
import com.hms.enums.RoomType;
import com.hms.service.PricingPolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pricing-policies")
@RequiredArgsConstructor
@Tag(name = "Pricing Policy Management", description = "APIs for managing pricing policies in the system")
public class PricingPolicyController {

    private final PricingPolicyService pricingPolicyService;

    @PostMapping
    @Operation(summary = "Create new pricing policy", description = "Creates a new pricing policy in the system")
    public ResponseEntity<PricingPolicy> createPolicy(@RequestBody PricingPolicy policy) {
        return new ResponseEntity<>(pricingPolicyService.createPolicy(policy), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update pricing policy", description = "Updates an existing pricing policy by ID")
    public ResponseEntity<PricingPolicy> updatePolicy(
            @Parameter(description = "Policy ID") @PathVariable UUID id,
            @RequestBody PricingPolicy policy) {
        return ResponseEntity.ok(pricingPolicyService.updatePolicy(id, policy));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete pricing policy", description = "Deletes a pricing policy by ID")
    public ResponseEntity<Void> deletePolicy(
            @Parameter(description = "Policy ID") @PathVariable UUID id) {
        pricingPolicyService.deletePolicy(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get pricing policy by ID", description = "Retrieves a pricing policy by its ID")
    public ResponseEntity<PricingPolicy> getPolicy(
            @Parameter(description = "Policy ID") @PathVariable UUID id) {
        return ResponseEntity.ok(pricingPolicyService.getPolicyById(id));
    }

    @GetMapping
    @Operation(summary = "Get all pricing policies", description = "Retrieves all pricing policies in the system")
    public ResponseEntity<List<PricingPolicy>> getAllPolicies() {
        return ResponseEntity.ok(pricingPolicyService.getAllPolicies());
    }

    @GetMapping("/property/{propertyId}")
    @Operation(summary = "Get policies by property", description = "Retrieves all pricing policies for a specific property")
    public ResponseEntity<List<PricingPolicy>> getPoliciesByProperty(
            @Parameter(description = "Property ID") @PathVariable UUID propertyId) {
        return ResponseEntity.ok(pricingPolicyService.getPoliciesByProperty(propertyId));
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Get policies by type", description = "Retrieves all pricing policies of a specific type")
    public ResponseEntity<List<PricingPolicy>> getPoliciesByType(
            @Parameter(description = "Policy type") @PathVariable String type) {
        return ResponseEntity.ok(pricingPolicyService.getPoliciesByType(type));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get policies by status", description = "Retrieves all pricing policies with a specific status")
    public ResponseEntity<List<PricingPolicy>> getPoliciesByStatus(
            @Parameter(description = "Policy status") @PathVariable String status) {
        return ResponseEntity.ok(pricingPolicyService.getPoliciesByStatus(status));
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate pricing policy", description = "Activates a pricing policy")
    public ResponseEntity<PricingPolicy> activatePolicy(
            @Parameter(description = "Policy ID") @PathVariable UUID id) {
        return ResponseEntity.ok(pricingPolicyService.activatePolicy(id));
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate pricing policy", description = "Deactivates a pricing policy")
    public ResponseEntity<PricingPolicy> deactivatePolicy(
            @Parameter(description = "Policy ID") @PathVariable UUID id) {
        return ResponseEntity.ok(pricingPolicyService.deactivatePolicy(id));
    }

    @GetMapping("/calculate/{propertyId}")
    @Operation(summary = "Calculate price", description = "Calculates the price for a property based on active pricing policies")
    public ResponseEntity<Double> calculatePrice(
            @Parameter(description = "Property ID") @PathVariable UUID propertyId,
            @Parameter(description = "Number of days") @RequestParam(required = false, defaultValue = "30") Integer days) {
        return ResponseEntity.ok(pricingPolicyService.calculatePrice(propertyId, days));
    }

    @GetMapping("/search")
    @Operation(summary = "Search pricing policies", description = "Searches pricing policies by name or description")
    public ResponseEntity<List<PricingPolicy>> searchPolicies(
            @Parameter(description = "Search keyword") @RequestParam String keyword) {
        return ResponseEntity.ok(pricingPolicyService.searchPolicies(keyword));
    }

    @GetMapping("/active")
    @Operation(summary = "Get active policies", description = "Retrieves all active pricing policies")
    public ResponseEntity<List<PricingPolicy>> getActivePolicies() {
        return ResponseEntity.ok(pricingPolicyService.getActivePolicies());
    }

    @GetMapping("/conflicts")
    @Operation(summary = "Check policy conflicts", description = "Checks for conflicting pricing policies")
    public ResponseEntity<List<PricingPolicy>> checkPolicyConflicts(
            @Parameter(description = "Property ID") @RequestParam UUID propertyId) {
        return ResponseEntity.ok(pricingPolicyService.checkPolicyConflicts(propertyId));
    }

    @GetMapping("/number/{policyNumber}")
    @Operation(summary = "Get a pricing policy by policy number", description = "Retrieves a pricing policy by its policy number")
    public ResponseEntity<PricingPolicy> getPolicyByNumber(
            @Parameter(description = "Policy number") @PathVariable String policyNumber) {
        return ResponseEntity.ok(pricingPolicyService.getPolicyByNumber(policyNumber));
    }

    @GetMapping("/property-type/{propertyType}")
    @Operation(summary = "Get pricing policies by property type", description = "Retrieves all pricing policies for a specific property type")
    public ResponseEntity<List<PricingPolicy>> getPoliciesByPropertyType(
            @Parameter(description = "Property type") @PathVariable PropertyType propertyType) {
        return ResponseEntity.ok(pricingPolicyService.getPoliciesByPropertyType(propertyType));
    }

    @GetMapping("/room-type/{roomType}")
    @Operation(summary = "Get pricing policies by room type", description = "Retrieves all pricing policies for a specific room type")
    public ResponseEntity<List<PricingPolicy>> getPoliciesByRoomType(
            @Parameter(description = "Room type") @PathVariable RoomType roomType) {
        return ResponseEntity.ok(pricingPolicyService.getPoliciesByRoomType(roomType));
    }

    @GetMapping("/active-property-types")
    @Operation(summary = "Get active property types", description = "Retrieves all property types that have active pricing policies for a specific date")
    public ResponseEntity<List<PropertyType>> getActivePropertyTypes(
            @Parameter(description = "Reference date (yyyy-MM-dd)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(pricingPolicyService.getActivePropertyTypes(date));
    }
}