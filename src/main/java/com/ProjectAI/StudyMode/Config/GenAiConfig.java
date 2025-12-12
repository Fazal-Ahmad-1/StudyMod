package com.ProjectAI.StudyMode.Config;

import com.google.genai.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Minimal GenAI client config.
 * Reads API key from property 'genai.api-key' or environment variable 'GOOGLE_API_KEY'.
 * If no key is provided, returns a Client() which will rely on default environment credentials.
 */
@Configuration
public class GenAiConfig {
    @Value("${genai.api-key}")
    private String apiKey;

    @Bean
    public Client geminiClient(){
        // Explicitly pass the API key from properties
        return new Client.Builder()
                .apiKey(apiKey)
                .build();
    }
}
