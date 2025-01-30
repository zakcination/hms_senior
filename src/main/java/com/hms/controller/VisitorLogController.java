package com.hms.controller;

import com.hms.entity.VisitorLog;
import com.hms.enums.VisitStatus;
import com.hms.service.VisitorLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/visitor-logs")
@RequiredArgsConstructor
@Tag(name = "Visitor Log Management", description = "APIs for managing visitor logs")
public class VisitorLogController {

    private final VisitorLogService visitorLogService;

    @PostMapping
    @Operation(summary = "Create a new visitor log", description = "Creates a new visitor log in the system")
    public ResponseEntity<VisitorLog> createVisitorLog(@RequestBody VisitorLog visitorLog) {
        return new ResponseEntity<>(visitorLogService.createVisitorLog(visitorLog), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a visitor log", description = "Updates an existing visitor log by ID")
    public ResponseEntity<VisitorLog> updateVisitorLog(
            @Parameter(description = "Visitor log ID") @PathVariable UUID id,
            @RequestBody VisitorLog visitorLog) {
        return ResponseEntity.ok(visitorLogService.updateVisitorLog(id, visitorLog));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a visitor log", description = "Deletes a visitor log by ID")
    public ResponseEntity<Void> deleteVisitorLog(
            @Parameter(description = "Visitor log ID") @PathVariable UUID id) {
        visitorLogService.deleteVisitorLog(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a visitor log by ID", description = "Retrieves a visitor log by its ID")
    public ResponseEntity<VisitorLog> getVisitorLog(
            @Parameter(description = "Visitor log ID") @PathVariable UUID id) {
        return ResponseEntity.ok(visitorLogService.getVisitorLogById(id));
    }

    @GetMapping("/pass/{passNumber}")
    @Operation(summary = "Get a visitor log by pass number", description = "Retrieves a visitor log by its pass number")
    public ResponseEntity<VisitorLog> getVisitorLogByPassNumber(
            @Parameter(description = "Visitor pass number") @PathVariable String passNumber) {
        return ResponseEntity.ok(visitorLogService.getVisitorLogByPassNumber(passNumber));
    }

    @GetMapping
    @Operation(summary = "Get all visitor logs", description = "Retrieves all visitor logs in the system")
    public ResponseEntity<List<VisitorLog>> getAllVisitorLogs() {
        return ResponseEntity.ok(visitorLogService.getAllVisitorLogs());
    }

    @GetMapping("/host/{hostUserId}")
    @Operation(summary = "Get visitor logs by host user", description = "Retrieves all visitor logs for a specific host user")
    public ResponseEntity<List<VisitorLog>> getVisitorLogsByHostUser(
            @Parameter(description = "Host user ID") @PathVariable UUID hostUserId) {
        return ResponseEntity.ok(visitorLogService.getVisitorLogsByHostUserId(hostUserId));
    }

    @GetMapping("/property/{propertyId}")
    @Operation(summary = "Get visitor logs by property", description = "Retrieves all visitor logs for a specific property")
    public ResponseEntity<List<VisitorLog>> getVisitorLogsByProperty(
            @Parameter(description = "Property ID") @PathVariable UUID propertyId) {
        return ResponseEntity.ok(visitorLogService.getVisitorLogsByPropertyId(propertyId));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get visitor logs by status", description = "Retrieves all visitor logs with a specific status")
    public ResponseEntity<List<VisitorLog>> getVisitorLogsByStatus(
            @Parameter(description = "Visit status") @PathVariable VisitStatus status) {
        return ResponseEntity.ok(visitorLogService.getVisitorLogsByStatus(status));
    }

    @GetMapping("/scheduled")
    @Operation(summary = "Get scheduled visitor logs", description = "Retrieves all visitor logs scheduled between start and end dates")
    public ResponseEntity<List<VisitorLog>> getScheduledVisitorLogs(
            @Parameter(description = "Start date-time (yyyy-MM-dd'T'HH:mm:ss)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @Parameter(description = "End date-time (yyyy-MM-dd'T'HH:mm:ss)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(visitorLogService.getScheduledVisitorLogs(start, end));
    }

    @GetMapping("/actual")
    @Operation(summary = "Get actual visitor logs", description = "Retrieves all visitor logs with actual visits between start and end dates")
    public ResponseEntity<List<VisitorLog>> getActualVisitorLogs(
            @Parameter(description = "Start date-time (yyyy-MM-dd'T'HH:mm:ss)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @Parameter(description = "End date-time (yyyy-MM-dd'T'HH:mm:ss)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(visitorLogService.getActualVisitorLogs(start, end));
    }

    @GetMapping("/upcoming")
    @Operation(summary = "Get upcoming visits", description = "Retrieves all upcoming approved visits")
    public ResponseEntity<List<VisitorLog>> getUpcomingVisits() {
        return ResponseEntity.ok(visitorLogService.getUpcomingVisits());
    }

    @GetMapping("/current")
    @Operation(summary = "Get current visitors", description = "Retrieves all visitors currently checked in")
    public ResponseEntity<List<VisitorLog>> getCurrentVisitors() {
        return ResponseEntity.ok(visitorLogService.getCurrentVisitors());
    }

    @GetMapping("/property/{propertyId}/count")
    @Operation(summary = "Count current visitors by property", description = "Counts the number of visitors currently checked in at a property")
    public ResponseEntity<Long> countCurrentVisitorsByProperty(
            @Parameter(description = "Property ID") @PathVariable UUID propertyId) {
        return ResponseEntity.ok(visitorLogService.countCurrentVisitorsByProperty(propertyId));
    }

    @PostMapping("/{id}/check-in")
    @Operation(summary = "Check in visitor", description = "Checks in a visitor")
    public ResponseEntity<VisitorLog> checkInVisitor(
            @Parameter(description = "Visitor log ID") @PathVariable UUID id) {
        return ResponseEntity.ok(visitorLogService.checkInVisitor(id));
    }

    @PostMapping("/{id}/check-out")
    @Operation(summary = "Check out visitor", description = "Checks out a visitor")
    public ResponseEntity<VisitorLog> checkOutVisitor(
            @Parameter(description = "Visitor log ID") @PathVariable UUID id) {
        return ResponseEntity.ok(visitorLogService.checkOutVisitor(id));
    }

    @PostMapping("/{id}/approve")
    @Operation(summary = "Approve visit", description = "Approves a scheduled visit")
    public ResponseEntity<VisitorLog> approveVisit(
            @Parameter(description = "Visitor log ID") @PathVariable UUID id) {
        return ResponseEntity.ok(visitorLogService.approveVisit(id));
    }

    @PostMapping("/{id}/reject")
    @Operation(summary = "Reject visit", description = "Rejects a scheduled visit")
    public ResponseEntity<VisitorLog> rejectVisit(
            @Parameter(description = "Visitor log ID") @PathVariable UUID id) {
        return ResponseEntity.ok(visitorLogService.rejectVisit(id));
    }
}