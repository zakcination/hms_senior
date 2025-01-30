package com.hms.repository;

import com.hms.entity.AuditLog;
import com.hms.entity.User;
import com.hms.enums.AuditActionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {

        List<AuditLog> findByPerformedBy(User user);

        List<AuditLog> findByActionType(AuditActionType actionType);

        List<AuditLog> findByEntityTypeAndEntityId(String entityType, UUID entityId);

        List<AuditLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

        List<AuditLog> findByPerformedByAndActionType(User user, AuditActionType actionType);

        @Query("SELECT a FROM AuditLog a WHERE a.timestamp BETWEEN :startDate AND :endDate")
        List<AuditLog> findByDateRange(@Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        Page<AuditLog> findByPerformedByOrderByTimestampDesc(User performedBy, Pageable pageable);

        @Query("SELECT a FROM AuditLog a WHERE a.entityType = :entityType AND a.timestamp >= :since")
        List<AuditLog> findRecentActivityByEntityType(@Param("entityType") String entityType,
                        @Param("since") LocalDateTime since);

        @Query("SELECT DISTINCT a.ipAddress FROM AuditLog a WHERE a.performedBy.id = :userId AND a.ipAddress IS NOT NULL")
        List<String> findDistinctIpAddressesByUser(@Param("userId") UUID userId);

        @Query("SELECT a FROM AuditLog a WHERE a.performedBy.id = :userId AND a.actionType IN ('LOGIN', 'LOGOUT')")
        List<AuditLog> findUserLoginHistory(@Param("userId") UUID userId);
}