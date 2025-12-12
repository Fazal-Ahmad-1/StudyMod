package com.ProjectAI.StudyMode.Entity.Analytics;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AiAnalyticsDTO {
        private String summary;
        private List<String> actionPoints;
}
