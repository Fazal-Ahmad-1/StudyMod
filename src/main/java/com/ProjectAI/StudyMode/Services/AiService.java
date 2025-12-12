package com.ProjectAI.StudyMode.Services;

import com.ProjectAI.StudyMode.Entity.Analytics.AiAnalyticsDTO;
import com.ProjectAI.StudyMode.Entity.Analytics.OverviewDTO;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AiService {

    @Autowired
    private AnalyticsService analyticsService;
    @Autowired
    private Client client;

    public AiAnalyticsDTO generateWeeklyInsight(Long id){
        OverviewDTO overview=analyticsService.getWeeklyOverview(id);

        String prompt=buildPromptFromOverview(overview);

        String rawText= callGenAi(prompt);

        return parseInsight(rawText);
    }

    private String buildPromptFromOverview(OverviewDTO overview) {
        return """
                You are an AI study coach. I will give you data about a student's study activity for the last 7 days.

                Data:
                - Total minutes studied: %d
                - Average minutes per day: %.2f
                - Most productive day (highest minutes): %s

                Task:
                1. Write a short motivational summary (2–3 sentences).
                2. Then give 3 to 5 practical, specific action points to improve their study habits.
                3. Use simple language, direct and encouraging.
                4. Format your response like:

                Summary:
                <your summary here>

                Action Points:
                - point 1
                - point 2
                - point 3
                """.formatted(
                overview.getTotalMinutes(),
                overview.getAvgPerDay() != null ? overview.getAvgPerDay() : 0.0,
                overview.getMostProductiveDay() != null ? overview.getMostProductiveDay() : "N/A");

    }

    private String callGenAi(String prompt){
        try {
            GenerateContentResponse response =
                    client.models.generateContent(
                            "gemini-2.5-flash",
                            prompt,
                            null);

            return response.text();
        } catch (Exception e) {
            // Fallback in case of API failure
            return """
                    Summary:
                    I couldn't contact the AI service right now, but you studied a decent amount this week. Keep building the habit slowly and consistently.

                    Action Points:
                    - Keep a minimum daily target, even on busy days.
                    - Study your hardest subject during your most productive time.
                    - Review what you learned at the end of each day.
                    - We will shortly get back to you with ai insights
                    """;
        }
    }
    private AiAnalyticsDTO parseInsight(String raw) {
        // Very simple parser based on the format we requested in the prompt

        String summary = "";
        List<String> actions = new ArrayList<>();

        String[] lines = raw.split("\\r?\\n");
        boolean inSummary = false;
        boolean inActions = false;

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.equalsIgnoreCase("Summary:")) {
                inSummary = true;
                inActions = false;
                continue;
            }
            if (trimmed.equalsIgnoreCase("Action Points:")) {
                inSummary = false;
                inActions = true;
                continue;
            }

            if (inSummary) {
                summary += (summary.isEmpty() ? "" : " ") + trimmed;
            } else if (inActions) {
                if (trimmed.startsWith("-")) {
                    actions.add(trimmed.substring(1).trim());
                } else if (!trimmed.isEmpty()) {
                    // In case the model doesn't start with "- "
                    actions.add(trimmed);
                }
            }
        }

        if (summary.isEmpty()) {
            summary = raw; // fallback if parsing fails
        }

        if (actions.isEmpty()) {
            actions.add("Keep a consistent daily study routine.");
        }

        return AiAnalyticsDTO.builder()
                .summary(summary)
                .actionPoints(actions)
                .build();
    }


}
