package com.nobullet.taoofmarkov;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * The Tao of Markov generator.
 */
public class Generator implements Supplier<String>, Punctuation {

    /**
     * List of beginners: phrases that start the application.
     */
    private final List<ChainPhrase> sentenceBeginners;
    /**
     * Chains.
     */
    private final Map<ChainPhrase, List<String>> chains;
    /**
     * Random.
     */
    private final Random random = new Random();

    /**
     * Builds generator with the given chains data.
     *
     * @param chains Chains data.
     */
    public Generator(Map<ChainPhrase, List<String>> chains) {
        this.chains = chains;
        this.sentenceBeginners = chains.keySet()
                .stream()
                .filter(phrase -> phrase.isSentenceBeginning())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Generates next random sentence.
     *
     * @return Next random sentence.
     */
    public String generateRandomSentence() {
        ChainPhrase current = sentenceBeginners.get(random.nextInt(sentenceBeginners.size()));
        StringBuilder result = new StringBuilder();
        result.append(current.getSecond());
        while (true) {
            List<String> nextWords = chains.get(current);
            String nextWord = nextWords.get(random.nextInt(nextWords.size()));
            current = new ChainPhrase(current.getSecond(), nextWord);
            if (!treatAsWordsAsStrings().contains(current.getSecond())) {
                result.append(' ');
            }
            result.append(current.getSecond());
            if (current.isSentenceEnding()) {
                result.setLength(result.length() - 1 - current.getSecond().length());
                result.append(current.getSecond());
                break;
            }
        }
        return result.toString();
    }

    /**
     * Generates several random sentences.
     *
     * @param number Number of sentences to generate.
     * @param sentenceConsumer Sentence consumer.
     */
    public void generateRandomSentences(int number, Consumer<String> sentenceConsumer) {
        while (number-- > 0) {
            sentenceConsumer.accept(generateRandomSentence());
        }
    }

    /**
     * Generates new sentence.
     *
     * @return New random sentence.
     */
    @Override
    public String get() {
        return generateRandomSentence();
    }
}
