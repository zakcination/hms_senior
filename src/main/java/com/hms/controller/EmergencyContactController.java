package com.hms.controller;

import com.hms.entity.EmergencyContact;
import com.hms.service.EmergencyContactService;
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
@RequestMapping("/api/v1/emergency-contacts")
@RequiredArgsConstructor
@Tag(name = "Emergency Contact Management", description = "APIs for managing university emergency contacts")
public class EmergencyContactController {

    private final EmergencyContactService emergencyContactService;

    @PostMapping
    @Operation(summary = "Create a new emergency contact", description = "Creates a new emergency contact in the system")
    public ResponseEntity<EmergencyContact> createContact(@RequestBody EmergencyContact contact) {
        return new ResponseEntity<>(emergencyContactService.createContact(contact), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an emergency contact", description = "Updates an existing emergency contact by ID")
    public ResponseEntity<EmergencyContact> updateContact(
            @Parameter(description = "Emergency contact ID") @PathVariable UUID id,
            @RequestBody EmergencyContact contact) {
        return ResponseEntity.ok(emergencyContactService.updateContact(id, contact));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an emergency contact", description = "Deletes an emergency contact by ID")
    public ResponseEntity<Void> deleteContact(
            @Parameter(description = "Emergency contact ID") @PathVariable UUID id) {
        emergencyContactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an emergency contact by ID", description = "Retrieves an emergency contact by its ID")
    public ResponseEntity<EmergencyContact> getContact(
            @Parameter(description = "Emergency contact ID") @PathVariable UUID id) {
        return ResponseEntity.ok(emergencyContactService.getContactById(id));
    }

    @GetMapping
    @Operation(summary = "Get all emergency contacts", description = "Retrieves all emergency contacts in the system")
    public ResponseEntity<List<EmergencyContact>> getAllContacts() {
        return ResponseEntity.ok(emergencyContactService.getAllContacts());
    }

    @GetMapping("/department/{department}")
    @Operation(summary = "Get contacts by department", description = "Retrieves all emergency contacts for a specific department")
    public ResponseEntity<List<EmergencyContact>> getContactsByDepartment(
            @Parameter(description = "Department name") @PathVariable String department) {
        return ResponseEntity.ok(emergencyContactService.getContactsByDepartment(department));
    }

    @GetMapping("/24x7")
    @Operation(summary = "Get 24x7 available contacts", description = "Retrieves all emergency contacts that are available 24x7")
    public ResponseEntity<List<EmergencyContact>> get24x7Contacts() {
        return ResponseEntity.ok(emergencyContactService.get24x7Contacts());
    }

    @GetMapping("/priority/{maxPriorityLevel}")
    @Operation(summary = "Get contacts by priority level", description = "Retrieves all emergency contacts with priority level less than or equal to specified level")
    public ResponseEntity<List<EmergencyContact>> getContactsByPriorityLevel(
            @Parameter(description = "Maximum priority level (1-5)") @PathVariable Integer maxPriorityLevel) {
        return ResponseEntity.ok(emergencyContactService.getContactsByPriorityLevel(maxPriorityLevel));
    }

    @GetMapping("/departments")
    @Operation(summary = "Get all departments", description = "Retrieves a list of all departments that have emergency contacts")
    public ResponseEntity<List<String>> getAllDepartments() {
        return ResponseEntity.ok(emergencyContactService.getAllDepartments());
    }

    @GetMapping("/search")
    @Operation(summary = "Search contacts", description = "Searches emergency contacts by service name, department, or description")
    public ResponseEntity<List<EmergencyContact>> searchContacts(
            @Parameter(description = "Search keyword") @RequestParam String keyword) {
        return ResponseEntity.ok(emergencyContactService.searchContacts(keyword));
    }

    @GetMapping("/sorted-by-priority")
    @Operation(summary = "Get contacts sorted by priority", description = "Retrieves all emergency contacts sorted by priority level and department")
    public ResponseEntity<List<EmergencyContact>> getAllContactsSortedByPriority() {
        return ResponseEntity.ok(emergencyContactService.getAllContactsSortedByPriority());
    }
}