package com.ProjectAI.StudyMode.Services;

import com.ProjectAI.StudyMode.Entity.SoundTrack;
import com.ProjectAI.StudyMode.Entity.SoundTrackDTO;
import com.ProjectAI.StudyMode.Entity.SoundTrackType;
import com.ProjectAI.StudyMode.Repository.SoundTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoundService {

    @Autowired
    private SoundTrackRepository soundTrackRepository;

    public List<SoundTrackDTO> getAll(SoundTrackType type){

        List<SoundTrack>sounds;
        if(type!=null)
        {
            sounds=soundTrackRepository.findByType(type);
        }
        else{
            sounds=soundTrackRepository.findAll();
        }
        return sounds.stream()
                .map(s -> SoundTrackDTO.builder()
                        .id(s.getId())
                        .name(s.getName())
                        .type(s.getType())
                        .fileUrl(s.getFileUrl())
                        .loopable(s.isLoopable())
                        .build())
                .toList();
    }

}
