package com.ProjectAI.StudyMode.Controllers;

import com.ProjectAI.StudyMode.Services.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/ai")
public class AiController {
    @Autowired
    private AiService aiService;

    @PostMapping("/insight/weekly/{id}")
    public ResponseEntity<?> getWeeklyInsight(@PathVariable Long id){
        return new ResponseEntity<>(aiService.generateWeeklyInsight(id), HttpStatus.OK);
    }

}
