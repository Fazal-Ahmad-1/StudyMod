package com.ProjectAI.StudyMode.Repository;

import com.ProjectAI.StudyMode.Entity.DailySummary;
import com.ProjectAI.StudyMode.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailySummaryRepository extends JpaRepository<DailySummary,Long> {
    List<DailySummary> findByUserAndDateBetween(User user, LocalDate from, LocalDate to);
}
