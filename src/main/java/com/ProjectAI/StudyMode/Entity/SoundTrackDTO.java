package com.ProjectAI.StudyMode.Entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SoundTrackDTO {
    private Long id;
    private String name;
    private SoundTrackType type;
    private String fileUrl;
    private boolean loopable;
}
