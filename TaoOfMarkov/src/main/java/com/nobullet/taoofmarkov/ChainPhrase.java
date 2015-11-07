package com.nobullet.taoofmarkov;

import java.util.Objects;

/**
 * Chain phrase or pair of words. Could be a sentence beginning or a sentence end.
 */
public class ChainPhrase implements Punctuation {

    /**
     * First word.
     */
    private final String first;
    /**
     * Second word.
     */
    private final String second;

    /**
     * Constructs pair of words.
     *
     * @param first First word.
     * @param second Second word.
     */
    public ChainPhrase(String first, String second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Returns if the chain is the sentence beginning.
     *
     * @return Whether the chain is the sentence beginning.
     */
    public boolean isSentenceBeginning() {
        return sentenceBeginning().equals(first);
    }

    /**
     * Returns if the chain is the sentence ending.
     *
     * @return Whether the chain is the sentence beginning.
     */
    public boolean isSentenceEnding() {
        return endersAsStrings().contains(second);
    }

    /**
     * Return the first word of the chain phrase or sentence beginning.
     *
     * @return The first word or sentence beginning.
     */
    public String getFirst() {
        return first;
    }

    /**
     * Return the second word of the chain phrase or sentence beginning.
     *
     * @return The second word or sentence beginning.
     */
    public String getSecond() {
        return second;
    }

    @Override
    public int hashCode() {
        return (23 * 7 + this.first.hashCode()) * 23 + this.second.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ChainPhrase other = (ChainPhrase) obj;
        return Objects.equals(this.first, other.first) && Objects.equals(this.second, other.second);
    }

    @Override
    public String toString() {
        if (isSentenceEnding() || treatAsWordsAsStrings().contains(second)) {
            return first + second;
        }
        return first + " " + second;
    }
}
