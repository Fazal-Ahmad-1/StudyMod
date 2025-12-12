package com.ProjectAI.StudyMode.Repository;

import com.ProjectAI.StudyMode.Entity.SoundTrack;
import com.ProjectAI.StudyMode.Entity.SoundTrackType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoundTrackRepository extends JpaRepository<SoundTrack,Long> {

    List<SoundTrack> findByType(SoundTrackType type);
}
