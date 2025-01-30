package com.hms.repository;

import com.hms.entity.EmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, UUID> {
    List<EmergencyContact> findByDepartment(String department);

    List<EmergencyContact> findByIsAvailable24x7(Boolean isAvailable24x7);

    List<EmergencyContact> findByPriorityLevelLessThanEqual(Integer maxPriorityLevel);

    @Query("SELECT DISTINCT e.department FROM EmergencyContact e ORDER BY e.department")
    List<String> findAllDepartments();

    @Query("SELECT e FROM EmergencyContact e WHERE LOWER(e.serviceName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(e.department) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<EmergencyContact> searchContacts(String keyword);

    @Query("SELECT e FROM EmergencyContact e ORDER BY e.priorityLevel ASC, e.department ASC")
    List<EmergencyContact> findAllOrderByPriorityAndDepartment();
}