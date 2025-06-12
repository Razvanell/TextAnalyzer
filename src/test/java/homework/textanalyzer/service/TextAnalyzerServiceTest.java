package homework.textanalyzer.service;

import homework.textanalyzer.util.AnalysisType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the TextAnalyzerService.
 * These tests focus on the business logic of the service in isolation,
 * ensuring character counting is correct for various inputs and analysis types.
 */
class TextAnalyzerServiceTest {

    private TextAnalyzerService textAnalyzerService;

    @BeforeEach
    void setUp() {
        textAnalyzerService = new TextAnalyzerService();
    }

    @Test
    @DisplayName("Should correctly count vowels in a mixed-case string")
    void analyze_vowels_mixedCase() {
        String text = "Hello World";
        AnalysisType type = AnalysisType.VOWELS;
        Map<Character, Integer> result = textAnalyzerService.analyze(text, type);

        assertNotNull(result);
        // Corrected: There are only 2 unique vowels (E, O) in "Hello World"
        assertEquals(2, result.size(), "Should contain 2 unique vowels: E, O");
        assertEquals(1, result.get('E'), "Count for E should be 1");
        assertEquals(2, result.get('O'), "Count for O should be 2");
        assertNull(result.get('A'), "Count for A should be null as it's not present");
    }

    @Test
    @DisplayName("Should correctly count consonants in a mixed-case string")
    void analyze_consonants_mixedCase() {
        String text = "Hello World";
        AnalysisType type = AnalysisType.CONSONANTS;
        Map<Character, Integer> result = textAnalyzerService.analyze(text, type);

        assertNotNull(result);
        // Corrected: There are 5 unique consonants (H, L, W, R, D) in "Hello World"
        assertEquals(5, result.size(), "Should contain 5 unique consonants: H, L, W, R, D");
        assertEquals(1, result.get('H'), "Count for H should be 1");
        assertEquals(3, result.get('L'), "Count for L should be 3");
        assertEquals(1, result.get('W'), "Count for W should be 1");
        assertEquals(1, result.get('R'), "Count for R should be 1");
        assertEquals(1, result.get('D'), "Count for D should be 1");
        assertNull(result.get('A'), "Count for A should be null as it's a vowel");
    }

    @Test
    @DisplayName("Should return an empty map for an empty string")
    void analyze_emptyString() {
        String text = "";
        AnalysisType type = AnalysisType.VOWELS;
        Map<Character, Integer> result = textAnalyzerService.analyze(text, type);

        assertNotNull(result);
        assertTrue(result.isEmpty(), "Result map should be empty for an empty string");
    }

    @Test
    @DisplayName("Should return an empty map for a null string")
    void analyze_nullString() {
        String text = null;
        AnalysisType type = AnalysisType.VOWELS;
        Map<Character, Integer> result = textAnalyzerService.analyze(text, type);

        assertNotNull(result);
        assertTrue(result.isEmpty(), "Result map should be empty for a null string");
    }

    @Test
    @DisplayName("Should return an empty map for a string with only non-letter characters")
    void analyze_nonLetterCharacters() {
        String text = "123!@#$%";
        AnalysisType type = AnalysisType.VOWELS;
        Map<Character, Integer> result = textAnalyzerService.analyze(text, type);

        assertNotNull(result);
        assertTrue(result.isEmpty(), "Result map should be empty for non-letter characters");
    }

    @Test
    @DisplayName("Should handle text with spaces correctly")
    void analyze_textWithSpaces() {
        String text = "A B C D E";
        AnalysisType type = AnalysisType.VOWELS;
        Map<Character, Integer> result = textAnalyzerService.analyze(text, type);

        assertEquals(2, result.size()); // A, E
        assertEquals(1, result.get('A'));
        assertEquals(1, result.get('E'));
    }

    @Test
    @DisplayName("Should handle text with numbers and symbols, ignoring them")
    void analyze_textWithNumbersAndSymbols() {
        String text = "A1b2c3!@#E";
        AnalysisType type = AnalysisType.CONSONANTS;
        Map<Character, Integer> result = textAnalyzerService.analyze(text, type);

        assertEquals(2, result.size()); // B, C
        assertEquals(1, result.get('B'));
        assertEquals(1, result.get('C'));
    }

    @Test
    @DisplayName("Should correctly count vowels in repeated string")
    void analyze_vowels_repeated() {
        String text = "aaaaa";
        AnalysisType type = AnalysisType.VOWELS;
        Map<Character, Integer> result = textAnalyzerService.analyze(text, type);

        assertEquals(1, result.size());
        assertEquals(5, result.get('A'));
    }
}
