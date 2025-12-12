package com.ProjectAI.StudyMode.Controllers;

import com.ProjectAI.StudyMode.Entity.SoundTrackType;
import com.ProjectAI.StudyMode.Services.SoundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/sounds")
public class SoundController {

    @Autowired
    private SoundService soundService;

    @GetMapping
    public ResponseEntity<?> list( @RequestParam(required = false) SoundTrackType type){
        try{
            return new ResponseEntity<>(soundService.getAll(type), HttpStatus.OK);
        }
        catch (Exception e){
            log.error("Error in /api/sounds ",e);
            throw e;
        }
    }
}
