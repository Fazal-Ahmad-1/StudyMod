package com.ProjectAI.StudyMode.Controllers;

import com.ProjectAI.StudyMode.Services.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/overview/week/{id}")
    public ResponseEntity<?> weeklyOverview(@PathVariable Long id) {
        return new ResponseEntity<>(analyticsService.getWeeklyOverview(id), HttpStatus.OK);
    }
    @GetMapping("/pet/status/{id}")
    public ResponseEntity<?> getPetStatus(@PathVariable Long id) {
        return new ResponseEntity<>(analyticsService.getPetMood(id), HttpStatus.OK);
    }
}
