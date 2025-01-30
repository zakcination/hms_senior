package com.hms.repository;

import com.hms.entity.MaintenanceRequest;
import com.hms.entity.Property;
import com.hms.entity.User;
import com.hms.enums.MaintenanceRequestType;
import com.hms.enums.MaintenanceStatus;
import com.hms.enums.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, UUID> {
    Optional<MaintenanceRequest> findByRequestNumber(String requestNumber);

    List<MaintenanceRequest> findByUser(User user);

    List<MaintenanceRequest> findByProperty(Property property);

    List<MaintenanceRequest> findByStatus(MaintenanceStatus status);

    List<MaintenanceRequest> findByRequestType(MaintenanceRequestType requestType);

    List<MaintenanceRequest> findByPriority(Priority priority);

    List<MaintenanceRequest> findByScheduledDateBetween(LocalDateTime start, LocalDateTime end);

    List<MaintenanceRequest> findByUserId(UUID userId);

    List<MaintenanceRequest> findByPropertyId(UUID propertyId);

    @Query("SELECT mr FROM MaintenanceRequest mr WHERE mr.status = :status AND mr.priority = :priority")
    List<MaintenanceRequest> findByStatusAndPriority(@Param("status") MaintenanceStatus status,
            @Param("priority") Priority priority);

    @Query("SELECT mr FROM MaintenanceRequest mr WHERE mr.scheduledDate BETWEEN :startDate AND :endDate")
    List<MaintenanceRequest> findScheduledBetween(@Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT mr FROM MaintenanceRequest mr WHERE mr.status = 'IN_PROGRESS' AND mr.assignedStaff LIKE %:staffId%")
    List<MaintenanceRequest> findActiveRequestsByStaff(@Param("staffId") String staffId);

    @Query("SELECT COUNT(mr) FROM MaintenanceRequest mr WHERE mr.property.id = :propertyId AND mr.status IN ('PENDING', 'IN_PROGRESS')")
    Long countActiveRequestsByProperty(@Param("propertyId") UUID propertyId);

    @Query("SELECT AVG(mr.cost) FROM MaintenanceRequest mr WHERE mr.property.id = :propertyId AND mr.status = 'COMPLETED'")
    Double calculateAverageMaintenanceCost(@Param("propertyId") UUID propertyId);
}