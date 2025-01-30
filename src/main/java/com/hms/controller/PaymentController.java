package com.hms.controller;

import com.hms.entity.Payment;
import com.hms.enums.PaymentStatus;
import com.hms.enums.PaymentType;
import com.hms.service.PaymentService;
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
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(name = "Payment Management", description = "APIs for managing payments in the system")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @Operation(summary = "Create new payment", description = "Creates a new payment in the system")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        return new ResponseEntity<>(paymentService.createPayment(payment), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update payment", description = "Updates an existing payment by ID")
    public ResponseEntity<Payment> updatePayment(
            @Parameter(description = "Payment ID") @PathVariable UUID id,
            @RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.updatePayment(id, payment));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete payment", description = "Deletes a payment by ID")
    public ResponseEntity<Void> deletePayment(
            @Parameter(description = "Payment ID") @PathVariable UUID id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payment by ID", description = "Retrieves a payment by its ID")
    public ResponseEntity<Payment> getPayment(
            @Parameter(description = "Payment ID") @PathVariable UUID id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @GetMapping
    @Operation(summary = "Get all payments", description = "Retrieves all payments in the system")
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/lease/{leaseId}")
    @Operation(summary = "Get payments by lease", description = "Retrieves all payments for a specific lease")
    public ResponseEntity<List<Payment>> getPaymentsByLease(
            @Parameter(description = "Lease ID") @PathVariable UUID leaseId) {
        return ResponseEntity.ok(paymentService.getPaymentsByLease(leaseId));
    }

    @GetMapping("/tenant/{tenantId}")
    @Operation(summary = "Get payments by tenant", description = "Retrieves all payments made by a specific tenant")
    public ResponseEntity<List<Payment>> getPaymentsByTenant(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId) {
        return ResponseEntity.ok(paymentService.getPaymentsByTenant(tenantId));
    }

    @GetMapping("/property/{propertyId}")
    @Operation(summary = "Get payments by property", description = "Retrieves all payments for a specific property")
    public ResponseEntity<List<Payment>> getPaymentsByProperty(
            @Parameter(description = "Property ID") @PathVariable UUID propertyId) {
        return ResponseEntity.ok(paymentService.getPaymentsByProperty(propertyId));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get payments by status", description = "Retrieves all payments with a specific status")
    public ResponseEntity<List<Payment>> getPaymentsByStatus(
            @Parameter(description = "Payment status") @PathVariable PaymentStatus status) {
        return ResponseEntity.ok(paymentService.getPaymentsByStatus(status));
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Get payments by type", description = "Retrieves all payments of a specific type")
    public ResponseEntity<List<Payment>> getPaymentsByType(
            @Parameter(description = "Payment type") @PathVariable PaymentType type) {
        return ResponseEntity.ok(paymentService.getPaymentsByType(type));
    }

    @GetMapping("/date-range")
    @Operation(summary = "Get payments by date range", description = "Retrieves all payments within a specific date range")
    public ResponseEntity<List<Payment>> getPaymentsByDateRange(
            @Parameter(description = "Start date") @RequestParam LocalDate startDate,
            @Parameter(description = "End date") @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(paymentService.getPaymentsByDateRange(startDate, endDate));
    }

    @PatchMapping("/{id}/process")
    @Operation(summary = "Process payment", description = "Processes a pending payment")
    public ResponseEntity<Payment> processPayment(
            @Parameter(description = "Payment ID") @PathVariable UUID id) {
        return ResponseEntity.ok(paymentService.processPayment(id));
    }

    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Cancel payment", description = "Cancels a pending payment")
    public ResponseEntity<Payment> cancelPayment(
            @Parameter(description = "Payment ID") @PathVariable UUID id,
            @Parameter(description = "Cancellation reason") @RequestParam(required = false) String reason) {
        return ResponseEntity.ok(paymentService.cancelPayment(id, reason));
    }

    @GetMapping("/overdue")
    @Operation(summary = "Get overdue payments", description = "Retrieves all overdue payments")
    public ResponseEntity<List<Payment>> getOverduePayments() {
        return ResponseEntity.ok(paymentService.getOverduePayments());
    }

    @GetMapping("/upcoming")
    @Operation(summary = "Get upcoming payments", description = "Retrieves all upcoming payments due within the next 30 days")
    public ResponseEntity<List<Payment>> getUpcomingPayments() {
        return ResponseEntity.ok(paymentService.getUpcomingPayments());
    }

    @GetMapping("/stats/total/{propertyId}")
    @Operation(summary = "Get total payments", description = "Calculates total payments received for a property")
    public ResponseEntity<Double> getTotalPayments(
            @Parameter(description = "Property ID") @PathVariable UUID propertyId,
            @Parameter(description = "Start date") @RequestParam LocalDate startDate,
            @Parameter(description = "End date") @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(paymentService.calculateTotalPayments(propertyId, startDate, endDate));
    }
}