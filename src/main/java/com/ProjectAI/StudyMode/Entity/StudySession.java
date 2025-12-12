package com.ProjectAI.StudyMode.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class StudySession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String subject;
    private String tag;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Long duration;

    private boolean wasInterrupted;

    @Column(length = 1000)
    private String note;
}
