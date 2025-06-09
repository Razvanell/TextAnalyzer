package homework.textanalyzer;

import java.util.HashMap;
import java.util.Map;

public class TextAnalyzer {

    private static final String VOWELS = "AEIOU";

    public enum Mode {
        VOWELS,
        CONSONANTS
    }

    public Map<Character, Integer> analyze(String input, Mode mode) {
        Map<Character, Integer> counts = new HashMap<>();
        if (input == null || input.isEmpty()) {
            return counts;
        }

        char[] chars = input.toUpperCase().toCharArray();

        for (char c : chars) {
            if (!Character.isLetter(c)) continue;

            boolean isVowel = VOWELS.indexOf(c) >= 0;
            if ((mode == Mode.VOWELS && isVowel) || (mode == Mode.CONSONANTS && !isVowel)) {
                counts.put(c, counts.getOrDefault(c, 0) + 1);
            }
        }

        return counts;
    }

    public String formatResult(Map<Character, Integer> counts) {
        StringBuilder sb = new StringBuilder();
        counts.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> sb.append("Letter '")
                        .append(entry.getKey())
                        .append("' appears ")
                        .append(entry.getValue())
                        .append(" times\n"));
        return sb.toString().trim();
    }
}
