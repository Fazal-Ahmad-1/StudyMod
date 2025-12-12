package com.ProjectAI.StudyMode.Entity.Analytics;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OverviewDTO {
    private Long totalMinutes;
    private Double avgPerDay;
    private String mostProductiveDay;

    private List<SubjectStat> topSubjects;

    @Data
    @Builder
    public static class SubjectStat {
        private String subject;
        private Long minutes;
    }
}
