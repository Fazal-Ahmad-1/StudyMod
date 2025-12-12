package com.ProjectAI.StudyMode.Controllers;

import com.ProjectAI.StudyMode.Entity.StartSessionRequest;
import com.ProjectAI.StudyMode.Entity.StudySessionResponse;
import com.ProjectAI.StudyMode.Services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping("/start")
    public ResponseEntity<?> start(@RequestParam Long id,@RequestBody StartSessionRequest request){
        return new ResponseEntity<>(sessionService.startSession(id,request), HttpStatus.OK);
    }

    @PostMapping("/stop/{id}")
    public ResponseEntity<?> stop(@PathVariable Long id){
        return new ResponseEntity<>(sessionService.stopSession(id),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<StudySessionResponse>> getSessions(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        return ResponseEntity.ok(sessionService.getSessions(id,from, to));
    }
}
