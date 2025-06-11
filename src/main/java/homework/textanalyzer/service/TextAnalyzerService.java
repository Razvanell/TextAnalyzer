package homework.textanalyzer.service;

import homework.textanalyzer.util.AnalysisType;
import homework.textanalyzer.util.CharacterSets; // Assumed to contain definitions for VOWELS, CONSONANTS etc.
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Service for analyzing text to count vowels or consonants.
 * This service performs the core business logic of character counting.
 * The analysis is case-insensitive for character counting.
 */
@Service
public class TextAnalyzerService {

    /**
     * Analyzes the given text for the specified character type (vowels or consonants).
     *
     * @param text The input sentence to be analyzed.
     * @param type The type of analysis to perform: "vowels" or "consonants".
     * @return A map where keys are uppercase characters and values are their counts.
     * Returns an empty map if the input text is null, empty, or if the type is invalid.
     */
    public Map<Character, Integer> analyze(String text, AnalysisType type) {
        // Handle invalid or empty input to prevent errors and return a predictable empty result.
        if (text == null || text.trim().isEmpty() || type == null) {
            return Collections.emptyMap();
        }

        // Use LinkedHashMap to maintain the insertion order of characters, if important for consistency.
        Map<Character, Integer> counts = new LinkedHashMap<>();
        String normalizedText = text.toUpperCase();

        // Iterate over each character in the normalized text.
        for (char ch : normalizedText.toCharArray()) {
            // Only process if the character is an actual letter.
            if (Character.isLetter(ch)) {
                // Determine analysis based on the specified type.
                switch (type) {
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
                }
            }
        }
        return counts; // Return the map of character counts.
    }


    private boolean isVowel(char ch) {
        return CharacterSets.VOWELS.getCharacters().indexOf(ch) != -1;
    }
}
