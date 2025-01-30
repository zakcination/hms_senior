package com.hms.repository;

import com.hms.entity.Feedback;
import com.hms.entity.Property;
import com.hms.entity.User;
import com.hms.enums.FeedbackType;
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
public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {

    List<Feedback> findByUser(User user);

    List<Feedback> findByProperty(Property property);

    List<Feedback> findByCategory(String category);

    List<Feedback> findByRatingGreaterThanEqual(Integer minRating);

    List<Feedback> findByIsAnonymous(Boolean isAnonymous);

    List<Feedback> findByIsResolved(Boolean isResolved);

    List<Feedback> findByPropertyAndRatingGreaterThanEqual(Property property, Integer minRating);

    List<Feedback> findByRelatedEntityTypeAndRelatedEntityId(String entityType, UUID entityId);

    @Query("SELECT f FROM Feedback f WHERE f.adminResponse IS NULL")
    List<Feedback> findUnansweredFeedback();

    Page<Feedback> findByUserIdOrderBySubmittedAtDesc(UUID userId, Pageable pageable);

    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.relatedEntityType = :entityType AND f.relatedEntityId = :entityId")
    Double calculateAverageRating(String entityType, UUID entityId);

    @Query("SELECT f FROM Feedback f WHERE f.submittedAt >= :since")
    List<Feedback> findRecentFeedback(@Param("since") LocalDateTime since);

    @Query("SELECT f FROM Feedback f WHERE f.rating <= :rating")
    List<Feedback> findLowRatedFeedback(@Param("rating") Integer rating);

    @Query("SELECT COUNT(f) FROM Feedback f WHERE f.type = :type AND f.submittedAt BETWEEN :startDate AND :endDate")
    Long countFeedbackByTypeAndDateRange(@Param("type") FeedbackType type,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}