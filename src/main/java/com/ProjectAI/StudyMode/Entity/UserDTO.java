package com.ProjectAI.StudyMode.Entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String timezone;
}
