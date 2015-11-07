package com.nobullet.taoofmarkov;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Punctuation interface for all interested parties.
 */
public interface Punctuation {

    /**
     * Start of the sentence.
     */
    static final String SENTENCE_BEGINNING = "\n";

    /**
     * Enders as strings.
     */
    static final Set<String> ENDERS
            = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(".", "?", "!")));
    /**
     * Enders as characters.
     */
    static final Set<Character> ENDERS_AS_CHARACTERS
            = Collections.unmodifiableSet(ENDERS.stream().map(s -> s.charAt(0)).collect(Collectors.toSet()));

    /**
     * Characters allowed in words (other than a-zA-Z).
     */
    static final Set<Character> ALLOWED_IN_WORDS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList('-', '\'')));

    /**
     * Characters allowed in words (other than a-zA-Z).
     */
    static final Set<String> TREAT_AS_WORDS_STRINGS
            = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(":", ";", ",")));

    /**
     * Characters allowed in words (other than a-zA-Z).
     */
    static final Set<Character> TREAT_AS_WORDS_CHARACTERS
            = Collections.unmodifiableSet(TREAT_AS_WORDS_STRINGS.stream()
                    .map(s -> s.charAt(0)).collect(Collectors.toSet()));

    /**
     * Set of characters that must be treated as words.
     *
     * @return Set of characters that must be treated as words.
     */
    default Set<Character> treatAsWords() {
        return TREAT_AS_WORDS_CHARACTERS;
    }
    
    /**
     * Set of characters that must be treated as words.
     *
     * @return Set of characters that must be treated as words.
     */
    default Set<String> treatAsWordsAsStrings() {
        return TREAT_AS_WORDS_STRINGS;
    }

    /**
     * Set of characters allowed in words.
     *
     * @return Set of characters allowed in words.
     */
    default Set<Character> allowedInWords() {
        return ALLOWED_IN_WORDS;
    }

    /**
     * Returns a set of sentence enders as a set of strings.
     *
     * @return Set of sentence enders as set of strings.
     */
    default Set<String> endersAsStrings() {
        return ENDERS;
    }

    /**
     * Returns a set of sentence enders as a set of characters.
     *
     * @return Set of sentence enders as characters.
     */
    default Set<Character> endersAsCharacters() {
        return ENDERS_AS_CHARACTERS;
    }

    /**
     * Returns sentence beginning.
     *
     * @return Sentence beginning.
     */
    default String sentenceBeginning() {
        return SENTENCE_BEGINNING;
    }

    /**
     * Checks if the given character is English alphabet character or special character inside the word.
     *
     * @param c Character to check.
     * @return Whether the given character is English alphabet character or special character inside the word.
     */
    default boolean isWordCharacter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || allowedInWords().contains(c);
    }
}
