package homework.textanalyzer.controller;

import homework.textanalyzer.service.TextAnalyzerService;
import homework.textanalyzer.util.AnalysisType;
import homework.textanalyzer.model.AnalysisResponse;
import homework.textanalyzer.exception.TextLengthExceededException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/analyze")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class TextAnalyzerController {

    private final TextAnalyzerService textAnalyzerService;

    // This field is NOT final, so it's NOT included in the @RequiredArgsConstructor.
    // Spring will inject its value via @Value AFTER the constructor call.
    @Value("${text.analyzer.max-length:250}")
    private int maxTextLength;

    /**
     * REST endpoint to analyze text for vowel or consonant counts.
     *
     * @param type The type of analysis: "vowels" or "consonants".
     * @param text The text to analyze.
     * @return A ResponseEntity containing the custom AnalysisResponse object.
     * @throws TextLengthExceededException if the input text length exceeds the configured maximum.
     * (This exception is then caught by the GlobalExceptionHandler).
     */
    @GetMapping
    public ResponseEntity<AnalysisResponse> analyzeText(
            @RequestParam AnalysisType type,
            @RequestParam String text) {

        // Validate if the input text exceeds the maximum allowed length.
        if (text.length() > maxTextLength) {
            throw new TextLengthExceededException("Input text exceeds maximum allowed length of " + maxTextLength + " characters.");
        }

        // Handle empty text inputs specifically; return an empty result map.
        if (text.trim().isEmpty()) {
            return ResponseEntity.ok(new AnalysisResponse(Collections.emptyMap(), text, type));
        }

        // Perform the text analysis using the service.
        Map<Character, Integer> rawResult = textAnalyzerService.analyze(text, type);

        // Convert the Character keys from the raw result to String keys to match the expected JSON output format for the frontend and DTO.
        Map<String, Integer> result = rawResult.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> String.valueOf(entry.getKey()), // Convert Character to String
                        Map.Entry::getValue // Keep the integer count
                ));

        // Create the structured response object.
        AnalysisResponse response = new AnalysisResponse(result, text, type);

        // Return the response with an HTTP 200 OK status.
        return ResponseEntity.ok(response);
    }

}
