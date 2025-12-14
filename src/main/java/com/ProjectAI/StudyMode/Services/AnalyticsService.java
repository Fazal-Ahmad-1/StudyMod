package com.ProjectAI.StudyMode.Services;

import com.ProjectAI.StudyMode.Entity.Analytics.OverviewDTO;
import com.ProjectAI.StudyMode.Entity.StudySession;
import com.ProjectAI.StudyMode.Entity.User;
import com.ProjectAI.StudyMode.Repository.SessionRepository;
import com.ProjectAI.StudyMode.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private UserRepository userRepository;

    public OverviewDTO getWeeklyOverview(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        // 1. Get dates for the last 7 days
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekAgo = now.minusDays(7);

        // 2. Fetch all raw sessions
        List<StudySession> sessions = sessionRepository.findByUserAndStartTimeBetween(user, weekAgo, now);

        // 3. Calculate Total Minutes
        long totalMinutes = sessions.stream()
                .mapToLong(s -> s.getDuration() == null ? 0 : s.getDuration())
                .sum();

        // 4. Calculate Average (Total / 7 days)
        double avg = (double) totalMinutes / 7.0;

        // 5. Find Most Productive Day
        // Group sessions by Date, sum their duration, find the max
        String bestDay = sessions.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getStartTime().toLocalDate(),
                        Collectors.summingLong(s -> s.getDuration() == null ? 0 : s.getDuration())
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> entry.getKey().toString() + " (" + entry.getValue() + "m)")
                .orElse("No Data");

        // 2. --- NEW LOGIC: Top 3 Subjects ---
        List<OverviewDTO.SubjectStat> topSubjects = sessions.stream()
                // Group by Subject
                .collect(Collectors.groupingBy(
                        s -> s.getSubject().toUpperCase(), // Normalize case
                        Collectors.summingLong(s -> s.getDuration() == null ? 0 : s.getDuration())
                ))
                .entrySet().stream()
                // Sort Descending by Minutes
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                // Keep Top 3
                .limit(3)
                // Map to DTO
                .map(e -> OverviewDTO.SubjectStat.builder()
                        .subject(e.getKey())
                        .minutes(e.getValue())
                        .build())
                .collect(Collectors.toList());

        return OverviewDTO.builder()
                .totalMinutes(totalMinutes)
                .avgPerDay(avg)
                .mostProductiveDay(bestDay)
                .topSubjects(topSubjects)
                .build();
    }


    public String getPetMood(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        // Check last 3 days of activity
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeDaysAgo = now.minusDays(3);

        List<StudySession> sessions = sessionRepository.findByUserAndStartTimeBetween(user, threeDaysAgo, now);

        if (sessions.isEmpty()) {
            return "CRITICAL"; // No study in 3 days
        }

        long totalMinutes = sessions.stream().mapToLong(s -> s.getDuration() == null ? 0 : s.getDuration()).sum();
        double dailyAvg = totalMinutes / 3.0;

        if (dailyAvg > 60) return "HYPER";  // Crushing it
        if (dailyAvg > 20) return "STABLE"; // Good consistency
        return "LOW_POWER";                 // Needs more charge
    }


    public Long getGhostDuration(Long userId, String mode) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        StudySession ghostSession = null;

        if ("BEST".equalsIgnoreCase(mode)) {
            ghostSession = sessionRepository.findFirstByUserAndDurationIsNotNullOrderByDurationDesc(user).orElse(null);
        } else {
            // Default to LAST session
            ghostSession = sessionRepository.findFirstByUserAndEndTimeIsNotNullOrderByEndTimeDesc(user).orElse(null);
        }

        // Return duration in seconds (database stores minutes, so convert if needed,
        // assuming your DB stores minutes based on SessionService.java code)
        // Looking at SessionService.java: session.setDuration(minutes);
        // So we return minutes * 60 for the timer logic.
        return ghostSession != null ? ghostSession.getDuration() * 60 : 0L;
    }
}