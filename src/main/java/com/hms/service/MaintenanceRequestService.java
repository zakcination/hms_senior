package com.hms.service;

import com.hms.entity.MaintenanceRequest;
import com.hms.enums.MaintenanceRequestType;
import com.hms.enums.MaintenanceStatus;
import com.hms.enums.Priority;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MaintenanceRequestService {
    MaintenanceRequest createRequest(MaintenanceRequest request);

    MaintenanceRequest updateRequest(UUID id, MaintenanceRequest request);

    void deleteRequest(UUID id);

    MaintenanceRequest getRequestById(UUID id);

    MaintenanceRequest getRequestByNumber(String requestNumber);

    List<MaintenanceRequest> getAllRequests();

    List<MaintenanceRequest> getRequestsByUser(UUID userId);

    List<MaintenanceRequest> getRequestsByProperty(UUID propertyId);

    List<MaintenanceRequest> getRequestsByStatus(MaintenanceStatus status);

    List<MaintenanceRequest> getRequestsByType(MaintenanceRequestType type);

    List<MaintenanceRequest> getRequestsByPriority(Priority priority);

    List<MaintenanceRequest> getScheduledRequests(LocalDateTime start, LocalDateTime end);

    MaintenanceRequest updateRequestStatus(UUID id, MaintenanceStatus status);

    Double calculateAverageMaintenanceCost(UUID propertyId);
}