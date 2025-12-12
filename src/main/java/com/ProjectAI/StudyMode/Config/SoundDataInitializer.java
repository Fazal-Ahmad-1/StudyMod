package com.ProjectAI.StudyMode.Config;

import com.ProjectAI.StudyMode.Entity.SoundTrack;
import com.ProjectAI.StudyMode.Entity.SoundTrackType;
import com.ProjectAI.StudyMode.Repository.SoundTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SoundDataInitializer implements CommandLineRunner {

    @Autowired
    private SoundTrackRepository soundTrackRepository;

    @Override
    public void run(String...args){
        // Helper method to add sound only if it doesn't exist
        addSound("Rain2", SoundTrackType.RAIN, "/sounds/Rain2v1.mp3", true);
        addSound("Tapping1", SoundTrackType.RAIN, "/sounds/Tapping1v1.mp3", true);
        addSound("Wind1", SoundTrackType.RAIN, "/sounds/Wind1v1.mp3", true);
        // --- ADD YOUR NEW SOUNDS HERE ---
        addSound("Campfire", SoundTrackType.FAN, "/sounds/Campfire1v1.mp3", true);
    }

    private void addSound(String name, SoundTrackType type, String url, boolean loop) {
        // Only save if it doesn't exist in DB
        if (soundTrackRepository.findByType(type).stream().noneMatch(s -> s.getName().equals(name))) {
            soundTrackRepository.save(SoundTrack.builder()
                    .name(name)
                    .type(type)
                    .fileUrl(url) // This must match the file name in static/sounds
                    .loopable(loop)
                    .premium(false)
                    .build());
            System.out.println("Added sound: " + name);
        }
    }
}
