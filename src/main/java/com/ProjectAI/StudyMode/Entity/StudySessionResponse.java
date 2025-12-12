package com.ProjectAI.StudyMode.Entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudySessionResponse {
        private Long id;
        private String subject;
        private String tag;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Long durationMinutes;
}

