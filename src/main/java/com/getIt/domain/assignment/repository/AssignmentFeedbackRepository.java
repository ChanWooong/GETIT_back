package com.getit.domain.assignment.repository;

import com.getit.domain.assignment.entity.AssignmentFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssignmentFeedbackRepository extends JpaRepository<AssignmentFeedback, Long> {

    List<AssignmentFeedback> findAllByAssignmentIdInOrderByCreatedAtAsc(List<Long> assignmentId);

    @Query("SELECT f.assignment.id, COUNT(f) FROM AssignmentFeedback f WHERE f.assignment.id IN :assignmentIds GROUP BY f.assignment.id")
    List<Object[]> countFeedbackByAssignmentIds(@Param("assignmentIds") List<Long> assignmentIds);
}

