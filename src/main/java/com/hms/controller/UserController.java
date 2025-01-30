package com.hms.controller;

import com.hms.entity.User;
import com.hms.service.UserService;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for managing users in the system")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user in the system")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "Updates an existing user by ID")
    public ResponseEntity<User> updateUser(
            @Parameter(description = "User ID") @PathVariable UUID id,
            @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Deletes a user by ID")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "User ID") @PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by ID", description = "Retrieves a user by their ID")
    public ResponseEntity<User> getUser(
            @Parameter(description = "User ID") @PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves all users in the system")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/role/{role}")
    @Operation(summary = "Get users by role", description = "Retrieves all users with a specific role")
    public ResponseEntity<List<User>> getUsersByRole(
            @Parameter(description = "User role") @PathVariable String role) {
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }

    @GetMapping("/property/{propertyId}")
    @Operation(summary = "Get users by property", description = "Retrieves all users associated with a specific property")
    public ResponseEntity<List<User>> getUsersByProperty(
            @Parameter(description = "Property ID") @PathVariable UUID propertyId) {
        return ResponseEntity.ok(userService.getUsersByProperty(propertyId));
    }

    @GetMapping("/search")
    @Operation(summary = "Search users", description = "Searches users by name or email")
    public ResponseEntity<List<User>> searchUsers(
            @Parameter(description = "Search keyword") @RequestParam String keyword) {
        return ResponseEntity.ok(userService.searchUsers(keyword));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update user status", description = "Updates the status of a user (active/inactive)")
    public ResponseEntity<User> updateUserStatus(
            @Parameter(description = "User ID") @PathVariable UUID id,
            @Parameter(description = "User status") @RequestParam boolean active) {
        return ResponseEntity.ok(userService.updateUserStatus(id, active));
    }

    @PatchMapping("/{id}/password")
    @Operation(summary = "Update user password", description = "Updates the password of a user")
    public ResponseEntity<Void> updatePassword(
            @Parameter(description = "User ID") @PathVariable UUID id,
            @RequestBody String newPassword) {
        userService.updatePassword(id, newPassword);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verify-email/{token}")
    @Operation(summary = "Verify email", description = "Verifies user's email address using verification token")
    public ResponseEntity<Void> verifyEmail(
            @Parameter(description = "Verification token") @PathVariable String token) {
        userService.verifyEmail(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Request password reset", description = "Sends password reset link to user's email")
    public ResponseEntity<Void> requestPasswordReset(
            @Parameter(description = "User email") @RequestParam String email) {
        userService.requestPasswordReset(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password/{token}")
    @Operation(summary = "Reset password", description = "Resets user's password using reset token")
    public ResponseEntity<Void> resetPassword(
            @Parameter(description = "Reset token") @PathVariable String token,
            @RequestBody String newPassword) {
        userService.resetPassword(token, newPassword);
        return ResponseEntity.ok().build();
    }
}