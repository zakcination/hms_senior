package com.hms.service.impl;

import com.hms.entity.MaintenanceRequest;
import com.hms.enums.MaintenanceRequestType;
import com.hms.enums.MaintenanceStatus;
import com.hms.enums.Priority;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.MaintenanceRequestRepository;
import com.hms.service.MaintenanceRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceRequestServiceImpl implements MaintenanceRequestService {

    private final MaintenanceRequestRepository maintenanceRequestRepository;

    @Override
    public MaintenanceRequest createRequest(MaintenanceRequest request) {
        request.setStatus(MaintenanceStatus.PENDING);
        request.setRequestDate(LocalDateTime.now());
        return maintenanceRequestRepository.save(request);
    }

    @Override
    public MaintenanceRequest updateRequest(UUID id, MaintenanceRequest request) {
        MaintenanceRequest existingRequest = getRequestById(id);
        request.setId(id);
        return maintenanceRequestRepository.save(request);
    }

    @Override
    public void deleteRequest(UUID id) {
        MaintenanceRequest request = getRequestById(id);
        maintenanceRequestRepository.delete(request);
    }

    @Override
    @Transactional(readOnly = true)
    public MaintenanceRequest getRequestById(UUID id) {
        return maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance request not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public MaintenanceRequest getRequestByNumber(String requestNumber) {
        return maintenanceRequestRepository.findByRequestNumber(requestNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Maintenance request not found with number: " + requestNumber));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceRequest> getAllRequests() {
        return maintenanceRequestRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceRequest> getRequestsByUser(UUID userId) {
        return maintenanceRequestRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceRequest> getRequestsByProperty(UUID propertyId) {
        return maintenanceRequestRepository.findByPropertyId(propertyId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceRequest> getRequestsByStatus(MaintenanceStatus status) {
        return maintenanceRequestRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceRequest> getRequestsByType(MaintenanceRequestType type) {
        return maintenanceRequestRepository.findByRequestType(type);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceRequest> getRequestsByPriority(Priority priority) {
        return maintenanceRequestRepository.findByPriority(priority);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceRequest> getScheduledRequests(LocalDateTime start, LocalDateTime end) {
        return maintenanceRequestRepository.findByScheduledDateBetween(start, end);
    }

    @Override
    public MaintenanceRequest updateRequestStatus(UUID id, MaintenanceStatus status) {
        MaintenanceRequest request = getRequestById(id);
        request.setStatus(status);
        if (status == MaintenanceStatus.COMPLETED) {
            request.setCompletionDate(LocalDateTime.now());
        }
        return maintenanceRequestRepository.save(request);
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateAverageMaintenanceCost(UUID propertyId) {
        return maintenanceRequestRepository.calculateAverageMaintenanceCost(propertyId);
    }
}