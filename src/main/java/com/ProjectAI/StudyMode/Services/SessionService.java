package com.ProjectAI.StudyMode.Services;

import com.ProjectAI.StudyMode.Entity.StartSessionRequest;
import com.ProjectAI.StudyMode.Entity.StudySession;
import com.ProjectAI.StudyMode.Entity.StudySessionResponse;
import com.ProjectAI.StudyMode.Entity.User;
import com.ProjectAI.StudyMode.Repository.SessionRepository;
import com.ProjectAI.StudyMode.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessionService {
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser(){
        return userRepository.findById(1L).orElse(null);
    }

    public StudySessionResponse startSession(Long id,StartSessionRequest request){
        User user=userRepository.findById(id).orElse(null);

        if(sessionRepository.existsByUserAndEndTimeIsNull(user))
            throw new IllegalStateException("You already have an active session!");

        StudySession session=new StudySession();
        session.setUser(user);
        session.setSubject(request.getSubject());
        session.setTag(request.getTag());
        session.setStartTime(LocalDateTime.now());

        sessionRepository.save(session);
        return toResponse(session);
    }

    public StudySessionResponse stopSession(Long sessionId){
        StudySession session=sessionRepository.findById(sessionId).orElseThrow(()->new IllegalArgumentException("Session not found!"));

        if(session.getEndTime()!=null)
          throw new IllegalStateException("Session already stopped!");

        LocalDateTime end=LocalDateTime.now();

        long minutes= Duration.between(session.getStartTime(),end).toMinutes();
        session.setEndTime(end);
        session.setDuration(minutes);
        sessionRepository.save(session);
        return toResponse(session);
    }

    public List<StudySessionResponse> getSessions(Long id,LocalDateTime from, LocalDateTime to){
        User user=userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User not found"));
        return sessionRepository.findByUserAndStartTimeBetween(user,from,to)
                .stream().map(this::toResponse).toList();
    }

    private StudySessionResponse toResponse(StudySession session) {
        StudySessionResponse response=new StudySessionResponse();
        response.setId(session.getId());
        response.setSubject(session.getSubject());
        response.setTag(session.getTag());
        response.setStartTime(session.getStartTime());
        response.setEndTime(session.getEndTime());
        response.setDurationMinutes(session.getDuration());
        return response;
    }


}
