package com.hms.service.impl;

import com.hms.entity.VisitorLog;
import com.hms.enums.VisitStatus;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.VisitorLogRepository;
import com.hms.service.VisitorLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class VisitorLogServiceImpl implements VisitorLogService {

    private final VisitorLogRepository visitorLogRepository;

    @Override
    public VisitorLog createVisitorLog(VisitorLog visitorLog) {
        visitorLog.setStatus(VisitStatus.SCHEDULED);
        return visitorLogRepository.save(visitorLog);
    }

    @Override
    public VisitorLog updateVisitorLog(UUID id, VisitorLog visitorLog) {
        VisitorLog existingVisitorLog = getVisitorLogById(id);
        visitorLog.setId(id);
        return visitorLogRepository.save(visitorLog);
    }

    @Override
    public void deleteVisitorLog(UUID id) {
        VisitorLog visitorLog = getVisitorLogById(id);
        visitorLogRepository.delete(visitorLog);
    }

    @Override
    @Transactional(readOnly = true)
    public VisitorLog getVisitorLogById(UUID id) {
        return visitorLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor log not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public VisitorLog getVisitorLogByPassNumber(String passNumber) {
        return visitorLogRepository.findByVisitorPassNumber(passNumber)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Visitor log not found with pass number: " + passNumber));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisitorLog> getAllVisitorLogs() {
        return visitorLogRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisitorLog> getVisitorLogsByHostUserId(UUID hostUserId) {
        return visitorLogRepository.findByHostUserId(hostUserId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisitorLog> getVisitorLogsByPropertyId(UUID propertyId) {
        return visitorLogRepository.findByPropertyId(propertyId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisitorLog> getVisitorLogsByStatus(VisitStatus status) {
        return visitorLogRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisitorLog> getScheduledVisitorLogs(LocalDateTime start, LocalDateTime end) {
        return visitorLogRepository.findByScheduledArrivalBetween(start, end);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisitorLog> getActualVisitorLogs(LocalDateTime start, LocalDateTime end) {
        return visitorLogRepository.findByActualArrivalBetween(start, end);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisitorLog> getUpcomingVisits() {
        return visitorLogRepository.findUpcomingVisits(LocalDateTime.now());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisitorLog> getCurrentVisitors() {
        return visitorLogRepository.findCurrentVisitors();
    }

    @Override
    @Transactional(readOnly = true)
    public Long countCurrentVisitorsByProperty(UUID propertyId) {
        return visitorLogRepository.countCurrentVisitorsByProperty(propertyId);
    }

    @Override
    public VisitorLog checkInVisitor(UUID id) {
        VisitorLog visitorLog = getVisitorLogById(id);
        if (visitorLog.getStatus() != VisitStatus.APPROVED) {
            throw new IllegalStateException("Visitor must be approved before check-in");
        }
        visitorLog.setStatus(VisitStatus.CHECKED_IN);
        visitorLog.setActualArrival(LocalDateTime.now());
        return visitorLogRepository.save(visitorLog);
    }

    @Override
    public VisitorLog checkOutVisitor(UUID id) {
        VisitorLog visitorLog = getVisitorLogById(id);
        if (visitorLog.getStatus() != VisitStatus.CHECKED_IN) {
            throw new IllegalStateException("Visitor must be checked in before check-out");
        }
        visitorLog.setStatus(VisitStatus.CHECKED_OUT);
        visitorLog.setActualDeparture(LocalDateTime.now());
        return visitorLogRepository.save(visitorLog);
    }

    @Override
    public VisitorLog approveVisit(UUID id) {
        VisitorLog visitorLog = getVisitorLogById(id);
        if (visitorLog.getStatus() != VisitStatus.SCHEDULED) {
            throw new IllegalStateException("Only scheduled visits can be approved");
        }
        visitorLog.setStatus(VisitStatus.APPROVED);
        visitorLog.setApprovedAt(LocalDateTime.now());
        return visitorLogRepository.save(visitorLog);
    }

    @Override
    public VisitorLog rejectVisit(UUID id) {
        VisitorLog visitorLog = getVisitorLogById(id);
        if (visitorLog.getStatus() != VisitStatus.SCHEDULED) {
            throw new IllegalStateException("Only scheduled visits can be rejected");
        }
        visitorLog.setStatus(VisitStatus.REJECTED);
        return visitorLogRepository.save(visitorLog);
    }
}