package com.hms.service;

import com.hms.entity.VisitorLog;
import com.hms.enums.VisitStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface VisitorLogService {
    VisitorLog createVisitorLog(VisitorLog visitorLog);

    VisitorLog updateVisitorLog(UUID id, VisitorLog visitorLog);

    void deleteVisitorLog(UUID id);

    VisitorLog getVisitorLogById(UUID id);

    VisitorLog getVisitorLogByPassNumber(String passNumber);

    List<VisitorLog> getAllVisitorLogs();

    List<VisitorLog> getVisitorLogsByHostUserId(UUID hostUserId);

    List<VisitorLog> getVisitorLogsByPropertyId(UUID propertyId);

    List<VisitorLog> getVisitorLogsByStatus(VisitStatus status);

    List<VisitorLog> getScheduledVisitorLogs(LocalDateTime start, LocalDateTime end);

    List<VisitorLog> getActualVisitorLogs(LocalDateTime start, LocalDateTime end);

    List<VisitorLog> getUpcomingVisits();

    List<VisitorLog> getCurrentVisitors();

    Long countCurrentVisitorsByProperty(UUID propertyId);

    VisitorLog checkInVisitor(UUID id);

    VisitorLog checkOutVisitor(UUID id);

    VisitorLog approveVisit(UUID id);

    VisitorLog rejectVisit(UUID id);
}