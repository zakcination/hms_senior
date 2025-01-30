package com.hms.controller;

import com.hms.entity.Lease;
import com.hms.enums.LeaseStatus;
import com.hms.service.LeaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/leases")
@RequiredArgsConstructor
@Tag(name = "Lease Management", description = "APIs for managing property leases in the system")
public class LeaseController {

    private final LeaseService leaseService;

    @PostMapping
    @Operation(summary = "Create a new lease", description = "Creates a new lease agreement in the system")
    public ResponseEntity<Lease> createLease(@RequestBody Lease lease) {
        return new ResponseEntity<>(leaseService.createLease(lease), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a lease", description = "Updates an existing lease by ID")
    public ResponseEntity<Lease> updateLease(
            @Parameter(description = "Lease ID") @PathVariable UUID id,
            @RequestBody Lease lease) {
        return ResponseEntity.ok(leaseService.updateLease(id, lease));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a lease", description = "Deletes a lease by ID")
    public ResponseEntity<Void> deleteLease(
            @Parameter(description = "Lease ID") @PathVariable UUID id) {
        leaseService.deleteLease(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a lease by ID", description = "Retrieves a lease by its ID")
    public ResponseEntity<Lease> getLease(
            @Parameter(description = "Lease ID") @PathVariable UUID id) {
        return ResponseEntity.ok(leaseService.getLeaseById(id));
    }

    @GetMapping
    @Operation(summary = "Get all leases", description = "Retrieves all leases in the system")
    public ResponseEntity<List<Lease>> getAllLeases() {
        return ResponseEntity.ok(leaseService.getAllLeases());
    }

    @GetMapping("/property/{propertyId}")
    @Operation(summary = "Get leases by property", description = "Retrieves all leases for a specific property")
    public ResponseEntity<List<Lease>> getLeasesByProperty(
            @Parameter(description = "Property ID") @PathVariable UUID propertyId) {
        return ResponseEntity.ok(leaseService.getLeasesByProperty(propertyId));
    }

    @GetMapping("/tenant/{tenantId}")
    @Operation(summary = "Get leases by tenant", description = "Retrieves all leases for a specific tenant")
    public ResponseEntity<List<Lease>> getLeasesByTenant(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId) {
        return ResponseEntity.ok(leaseService.getLeasesByTenant(tenantId));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get leases by status", description = "Retrieves all leases with a specific status")
    public ResponseEntity<List<Lease>> getLeasesByStatus(
            @Parameter(description = "Lease status") @PathVariable LeaseStatus status) {
        return ResponseEntity.ok(leaseService.getLeasesByStatus(status));
    }

    @GetMapping("/active")
    @Operation(summary = "Get active leases", description = "Retrieves all currently active leases")
    public ResponseEntity<List<Lease>> getActiveLeases() {
        return ResponseEntity.ok(leaseService.getActiveLeases());
    }

    @GetMapping("/expiring")
    @Operation(summary = "Get expiring leases", description = "Retrieves all leases expiring within the next 30 days")
    public ResponseEntity<List<Lease>> getExpiringLeases() {
        return ResponseEntity.ok(leaseService.getExpiringLeases());
    }

    @PatchMapping("/{id}/renew")
    @Operation(summary = "Renew lease", description = "Renews an existing lease for another term")
    public ResponseEntity<Lease> renewLease(
            @Parameter(description = "Lease ID") @PathVariable UUID id,
            @Parameter(description = "New end date") @RequestParam LocalDate newEndDate) {
        return ResponseEntity.ok(leaseService.renewLease(id, newEndDate));
    }

    @PatchMapping("/{id}/terminate")
    @Operation(summary = "Terminate lease", description = "Terminates an active lease")
    public ResponseEntity<Lease> terminateLease(
            @Parameter(description = "Lease ID") @PathVariable UUID id,
            @Parameter(description = "Termination date") @RequestParam LocalDate terminationDate) {
        return ResponseEntity.ok(leaseService.terminateLease(id, terminationDate));
    }

    @GetMapping("/search")
    @Operation(summary = "Search leases", description = "Searches leases by reference number or terms")
    public ResponseEntity<List<Lease>> searchLeases(
            @Parameter(description = "Search keyword") @RequestParam String keyword) {
        return ResponseEntity.ok(leaseService.searchLeases(keyword));
    }
}