package homework.textanalyzer.controller;

import homework.textanalyzer.service.TextAnalyzerService;
import homework.textanalyzer.util.AnalysisType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for TextAnalyzerController.
 * Uses @WebMvcTest to test the controller layer in isolation from the full Spring context,
 * but still verifying web-specific concerns like request mapping, parameter binding,
 * and JSON serialization. The TextAnalyzerService dependency is mocked.
 */
@WebMvcTest(TextAnalyzerController.class) // Focuses on Spring MVC components
@TestPropertySource(properties = "text.analyzer.max-length=100")
class TextAnalyzerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc; // Autowired to simulate HTTP requests in tests

    @MockitoBean
    private TextAnalyzerService textAnalyzerService;

    @Test
    @DisplayName("Should return vowel counts for valid input")
    void analyzeText_vowels_success() throws Exception {
        // Define the expected result from the mocked service
        Map<Character, Integer> serviceResult = new HashMap<>();
        serviceResult.put('A', 2);
        serviceResult.put('E', 1);
        // Configure the mock service to return 'serviceResult' when its analyze method is called
        when(textAnalyzerService.analyze(anyString(), any(AnalysisType.class)))
                .thenReturn(serviceResult);

        // Perform a GET request to /analyze with specific parameters
        mockMvc.perform(get("/analyze")
                        .param("type", "VOWELS")
                        .param("text", "Hello World"))
                .andExpect(status().isOk()) // Verify HTTP 200 OK status
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Verify response content type is JSON
                // Assert specific values within the JSON response body using JSONPath
                .andExpect(jsonPath("$.characterCounts.A").value(2))
                .andExpect(jsonPath("$.characterCounts.E").value(1))
                .andExpect(jsonPath("$.originalText").value("Hello World"))
                .andExpect(jsonPath("$.analysisType").value("VOWELS"));
    }

    @Test
    @DisplayName("Should return consonant counts for valid input")
    void analyzeText_consonants_success() throws Exception {
        Map<Character, Integer> serviceResult = new HashMap<>();
        serviceResult.put('H', 1);
        serviceResult.put('L', 3);
        when(textAnalyzerService.analyze(anyString(), any(AnalysisType.class)))
                .thenReturn(serviceResult);

        mockMvc.perform(get("/analyze")
                        .param("type", "CONSONANTS")
                        .param("text", "Hello World"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.characterCounts.H").value(1))
                .andExpect(jsonPath("$.characterCounts.L").value(3))
                .andExpect(jsonPath("$.originalText").value("Hello World"))
                .andExpect(jsonPath("$.analysisType").value("CONSONANTS"));
    }

    @Test
    @DisplayName("Should return empty result for empty text")
    void analyzeText_emptyText() throws Exception {
        // Even though the controller has an explicit check for empty text,
        // we mock the service behavior for consistency in isolation testing.
        when(textAnalyzerService.analyze(anyString(), any(AnalysisType.class)))
                .thenReturn(Collections.emptyMap());

        mockMvc.perform(get("/analyze")
                        .param("type", "VOWELS")
                        .param("text", ""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.characterCounts").isEmpty())
                .andExpect(jsonPath("$.originalText").value(""))
                .andExpect(jsonPath("$.analysisType").value("VOWELS"));
    }

    @Test
    @DisplayName("Should return 400 Bad Request if 'type' parameter is missing")
    void analyzeText_missingTypeParam() throws Exception {
        mockMvc.perform(get("/analyze")
                        .param("text", "some text"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("type parameter is missing."));
    }

    @Test
    @DisplayName("Should return 400 Bad Request if 'text' parameter is missing")
    void analyzeText_missingTextParam() throws Exception {
        mockMvc.perform(get("/analyze")
                        .param("type", "VOWELS"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("text parameter is missing."));
    }

    @Test
    @DisplayName("Should return 400 Bad Request for invalid 'type' parameter value")
    void analyzeText_invalidTypeParam() throws Exception {
        mockMvc.perform(get("/analyze")
                        .param("type", "INVALID_TYPE")
                        .param("text", "some text"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Parameter 'type' has an invalid value: 'INVALID_TYPE'")));
    }

    @Test
    @DisplayName("Should return 413 Payload Too Large for excessive text length")
    void analyzeText_excessiveTextLength() throws Exception {
        String longText = "a".repeat(200); // 200 characters > max length

        // This test verifies the controller's validation, which throws an exception
        // before the service is even called. Thus, no mock service behavior is needed here.
        mockMvc.perform(get("/analyze")
                        .param("type", "VOWELS")
                        .param("text", longText))
                .andExpect(status().isPayloadTooLarge()) // Verify HTTP 413 status
                // Verify that the response body contains part of the expected error message.
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Input text exceeds maximum allowed length of")));
    }
}
