package com.ProjectAI.StudyMode.Repository;

import com.ProjectAI.StudyMode.Entity.StudySession;
import com.ProjectAI.StudyMode.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface SessionRepository extends JpaRepository<StudySession,Long> {

    List<StudySession> findByUserAndStartTimeBetween(User user, LocalDateTime from, LocalDateTime to);
    boolean existsByUserAndEndTimeIsNull(User user);
}
