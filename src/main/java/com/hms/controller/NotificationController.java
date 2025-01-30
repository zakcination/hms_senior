package com.hms.controller;

import com.hms.entity.Notification;
import com.hms.service.NotificationService;
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
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification Management", description = "APIs for managing notifications in the system")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    @Operation(summary = "Create new notification", description = "Creates a new notification in the system")
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        return new ResponseEntity<>(notificationService.createNotification(notification), HttpStatus.CREATED);
    }

    @PostMapping("/broadcast")
    @Operation(summary = "Broadcast notification", description = "Sends a notification to multiple users")
    public ResponseEntity<List<Notification>> broadcastNotification(
            @RequestBody Notification notification,
            @Parameter(description = "List of user IDs") @RequestParam List<UUID> userIds) {
        return ResponseEntity.ok(notificationService.broadcastNotification(notification, userIds));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete notification", description = "Deletes a notification by ID")
    public ResponseEntity<Void> deleteNotification(
            @Parameter(description = "Notification ID") @PathVariable UUID id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get notification by ID", description = "Retrieves a notification by its ID")
    public ResponseEntity<Notification> getNotification(
            @Parameter(description = "Notification ID") @PathVariable UUID id) {
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }

    @GetMapping
    @Operation(summary = "Get all notifications", description = "Retrieves all notifications in the system")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user notifications", description = "Retrieves all notifications for a specific user")
    public ResponseEntity<List<Notification>> getUserNotifications(
            @Parameter(description = "User ID") @PathVariable UUID userId) {
        return ResponseEntity.ok(notificationService.getUserNotifications(userId));
    }

    @GetMapping("/user/{userId}/unread")
    @Operation(summary = "Get unread notifications", description = "Retrieves all unread notifications for a specific user")
    public ResponseEntity<List<Notification>> getUnreadNotifications(
            @Parameter(description = "User ID") @PathVariable UUID userId) {
        return ResponseEntity.ok(notificationService.getUnreadNotifications(userId));
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Get notifications by type", description = "Retrieves all notifications of a specific type")
    public ResponseEntity<List<Notification>> getNotificationsByType(
            @Parameter(description = "Notification type") @PathVariable String type) {
        return ResponseEntity.ok(notificationService.getNotificationsByType(type));
    }

    @PatchMapping("/{id}/read")
    @Operation(summary = "Mark notification as read", description = "Marks a notification as read")
    public ResponseEntity<Notification> markAsRead(
            @Parameter(description = "Notification ID") @PathVariable UUID id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    @PatchMapping("/user/{userId}/read-all")
    @Operation(summary = "Mark all notifications as read", description = "Marks all notifications for a user as read")
    public ResponseEntity<Void> markAllAsRead(
            @Parameter(description = "User ID") @PathVariable UUID userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search notifications", description = "Searches notifications by content")
    public ResponseEntity<List<Notification>> searchNotifications(
            @Parameter(description = "Search keyword") @RequestParam String keyword) {
        return ResponseEntity.ok(notificationService.searchNotifications(keyword));
    }

    @DeleteMapping("/user/{userId}")
    @Operation(summary = "Delete user notifications", description = "Deletes all notifications for a specific user")
    public ResponseEntity<Void> deleteUserNotifications(
            @Parameter(description = "User ID") @PathVariable UUID userId) {
        notificationService.deleteUserNotifications(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}/count")
    @Operation(summary = "Get unread notification count", description = "Gets the count of unread notifications for a user")
    public ResponseEntity<Long> getUnreadNotificationCount(
            @Parameter(description = "User ID") @PathVariable UUID userId) {
        return ResponseEntity.ok(notificationService.getUnreadNotificationCount(userId));
    }
}