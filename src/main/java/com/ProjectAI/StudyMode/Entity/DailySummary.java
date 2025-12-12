package com.ProjectAI.StudyMode.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class DailySummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private LocalDate date;

    private Long totalMinutes;
    private Long sessionsCount;
    private Long avgSessionLength;
    private Long longestStreakMinutes;
}
