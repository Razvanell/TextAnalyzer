package homework.textanalyzer.controller;

import homework.textanalyzer.service.TextAnalyzerService;
import homework.textanalyzer.util.AnalysisType;
import homework.textanalyzer.model.AnalysisResponse; // Importing your existing AnalysisResponse DTO
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/analyze")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class TextAnalyzerController {

    private final TextAnalyzerService textAnalyzerService;

    /**
     * REST endpoint to analyze text for vowel or consonant counts.
     * @param type The type of analysis: "vowels" or "consonants".
     * @param text The text to analyze.
     * @return A ResponseEntity containing the custom AnalysisResponse object.
     */
    @GetMapping
    public ResponseEntity<AnalysisResponse> analyzeText(
            @RequestParam AnalysisType type,
            @RequestParam String text) {

        Map<Character, Integer> rawResult = textAnalyzerService.analyze(text, type);

        // Convert Character keys to String keys for consistent JSON output
        Map<String, Integer> result = rawResult.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> String.valueOf(entry.getKey()),
                        Map.Entry::getValue
                ));

        AnalysisResponse response = new AnalysisResponse(result, text, type);

        return ResponseEntity.ok(response);
    }
}
