package homework.textanalyzer.model; // Consider moving this to a 'dto' package later, e.g., homework.textanalyzer.dto

import homework.textanalyzer.util.AnalysisType; // Needed if AnalysisType is included in the response
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO for encapsulating the text analysis result.
 * Provides a structured response for the frontend.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisResponse {
    private Map<String, Integer> characterCounts;
    private String originalText;
    private AnalysisType analysisType;
}
