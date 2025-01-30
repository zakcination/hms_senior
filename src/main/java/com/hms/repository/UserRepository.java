package com.hms.repository;

import com.hms.entity.User;
import com.hms.enums.RoleGroup;
import com.hms.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailVerificationToken(String token);

    Optional<User> findByPasswordResetToken(String token);

    Optional<User> findByNationalId(String nationalId);

    Optional<User> findByNuid(String nuid);

    boolean existsByEmail(String email);

    boolean existsByNationalId(String nationalId);

    boolean existsByNuid(String nuid);

    List<User> findByRoleGroup(RoleGroup roleGroup);

    List<User> findByRoleType(RoleType roleType);

    List<User> findByDepartment(String department);

    @Query("SELECT u FROM User u WHERE u.roleType = 'TENANT' AND u.id NOT IN (SELECT l.tenant.id FROM Lease l WHERE l.status = 'ACTIVE')")
    List<User> findAvailableTenants();

    @Query("SELECT u FROM User u WHERE u.roleGroup = 'MAINTENANCE' AND u.id NOT IN (SELECT CAST(mr.assignedStaff AS uuid) FROM MaintenanceRequest mr WHERE mr.status = 'IN_PROGRESS')")
    List<User> findAvailableMaintenanceStaff();

    @Query("SELECT u FROM User u WHERE u.property.id = :propertyId")
    List<User> findByPropertyId(@Param("propertyId") UUID propertyId);

    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> searchUsers(@Param("keyword") String keyword);
}