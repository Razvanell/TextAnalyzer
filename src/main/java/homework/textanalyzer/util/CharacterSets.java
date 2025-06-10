package homework.textanalyzer.util; // Or 'model' or 'enums' if you prefer

public enum CharacterSets {
    VOWELS("AEIOU");
    // CONSONANTS("BCDFGHJKLMNPQRSTVWXYZ"),
    // DIGITS("0123456789");

    private final String characters;

    CharacterSets(String characters) {
        this.characters = characters;
    }

    public String getCharacters() {
        return characters;
    }
}