package homework.textanalyzer.util;

import lombok.Getter;

@Getter
public enum CharacterSets {
    VOWELS("AEIOU"),
    CONSONANTS("BCDFGHJKLMNPQRSTVWXYZ");
    // DIGITS("0123456789");

    private final String characters;

    CharacterSets(String characters) {
        this.characters = characters;
    }

}