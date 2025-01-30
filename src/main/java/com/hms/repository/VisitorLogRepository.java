package com.hms.repository;

import com.hms.entity.VisitorLog;
import com.hms.enums.VisitStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VisitorLogRepository extends JpaRepository<VisitorLog, UUID> {

    Optional<VisitorLog> findByVisitorPassNumber(String visitorPassNumber);

    List<VisitorLog> findByHostUserId(UUID hostUserId);

    List<VisitorLog> findByPropertyId(UUID propertyId);

    List<VisitorLog> findByStatus(VisitStatus status);

    List<VisitorLog> findByScheduledArrivalBetween(LocalDateTime start, LocalDateTime end);

    List<VisitorLog> findByActualArrivalBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT v FROM VisitorLog v WHERE v.status = 'APPROVED' AND v.scheduledArrival > :now")
    List<VisitorLog> findUpcomingVisits(LocalDateTime now);

    @Query("SELECT v FROM VisitorLog v WHERE v.status = 'CHECKED_IN' AND v.actualDeparture IS NULL")
    List<VisitorLog> findCurrentVisitors();

    @Query("SELECT COUNT(v) FROM VisitorLog v WHERE v.property.id = :propertyId AND v.status = 'CHECKED_IN'")
    Long countCurrentVisitorsByProperty(UUID propertyId);

    Page<VisitorLog> findByHostUserIdOrderByScheduledArrivalDesc(UUID hostUserId, Pageable pageable);

    @Query("SELECT v FROM VisitorLog v WHERE v.visitorIdentification = :identification AND v.status IN ('SCHEDULED', 'CHECKED_IN')")
    List<VisitorLog> findActiveVisitsByIdentification(String identification);
}