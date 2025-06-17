package homework.textanalyzer.util;

import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum CharacterSets {
    VOWELS("AEIOU"),
    CONSONANTS("BCDFGHJKLMNPQRSTVWXYZ");
    // DIGITS("0123456789");

    private final String characters;

    private final Set<Character> characterSet;

    CharacterSets(String characters) {
        this.characters = characters;
        // Initialize the HashSet once per enum constant
        // Creates an IntStream from the characters in the strings & converts them to a Character object.
        this.characterSet = characters.chars()
                .mapToObj(c -> (char) c).collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Checks if the given character is present in this character set.
     * @param ch The character to check.
     * @return true if the character is in the set, false otherwise.
     */
    public boolean contains(char ch) {
        return characterSet.contains(ch);
    }

}