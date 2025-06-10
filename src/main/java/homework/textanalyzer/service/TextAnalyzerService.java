package homework.textanalyzer.service;
import homework.textanalyzer.util.AnalysisType;
import homework.textanalyzer.util.CharacterSets;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Service for analyzing text to count vowels or consonants.
 * The analysis is case-insensitive for character counting.
 */
@Service // Mark this class as a Spring Service
public class TextAnalyzerService {

    /**
     * Analyzes the given text for the specified character type (vowels or consonants).
     *
     * @param text The input sentence to be analyzed.
     * @param type The type of analysis to perform: "vowels" or "consonants".
     * @return A map where keys are uppercase characters and values are their counts.
     * Returns an empty map if the input text is null or empty, or if the type is invalid.
     */
    public Map<Character, Integer> analyze(String text, AnalysisType type) { // Changed type to AnalysisType
        if (text == null || text.trim().isEmpty() || type == null) {
            return Collections.emptyMap();
        }

        Map<Character, Integer> counts = new LinkedHashMap<>();
        String normalizedText = text.toUpperCase();

        for (char ch : normalizedText.toCharArray()) {
            if (Character.isLetter(ch)) {
                switch (type) { // Use switch statement for better readability with enums
                    case VOWELS:
                        if (isVowel(ch)) {
                            counts.put(ch, counts.getOrDefault(ch, 0) + 1);
                        }
                        break;
                    case CONSONANTS:
                        if (!isVowel(ch)) {
                            counts.put(ch, counts.getOrDefault(ch, 0) + 1);
                        }
                        break;
                    // No 'default' needed if all enum cases are handled,
                    // or if you want to explicitly throw an exception for unhandled types.
                }
            }
        }
        return counts;
    }

    private boolean isVowel(char ch) {
        return CharacterSets.VOWELS.getCharacters().indexOf(ch) != -1;
    }
}