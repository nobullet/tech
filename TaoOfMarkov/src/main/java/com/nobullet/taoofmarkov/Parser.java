package com.nobullet.taoofmarkov;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Parser for chain phrases.
 */
public class Parser implements Punctuation {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    /**
     * Input stream with text.
     */
    private final InputStream textInputStream;
    /**
     * Parsed result.
     */
    private final Map<ChainPhrase, List<String>> chains;
    /**
     * Currently parsed queue of words.
     */
    private final Deque<String> currentWords;
    /**
     * Current word builder.
     */
    private final StringBuilder currentWord;

    /**
     * Constructor.
     *
     * @param textInputStream InputStream with text.
     */
    public Parser(InputStream textInputStream) {
        this.textInputStream = textInputStream;
        this.chains = new HashMap<>();
        this.currentWords = new LinkedList<>();
        this.currentWord = new StringBuilder();
    }

    /**
     * Parses current text input stream. Closes input stream. Subsequent call will throw {@link IOException}.
     *
     * @return Parsed results.
     * @throws IOException Exception in case of read error or if the input stream has been already closed.
     */
    public Map<ChainPhrase, List<String>> parse() throws IOException {
        currentWords.addLast(sentenceBeginning());
        int characterAsInt;
        char character;
        try (InputStreamReader reader = new InputStreamReader(textInputStream, UTF8)) {
            while ((characterAsInt = reader.read()) != -1) {
                character = (char) characterAsInt;
                if (isWordCharacter(character)) {
                    currentWord.append(character);
                } else if (treatAsWords().contains(character)) {
                    appendCurrentWords();
                    currentWord.append(character);
                    appendCurrentWords();
                } else if (endersAsCharacters().contains(character)) {
                    appendCurrentWords();
                    currentWord.append(character);
                    appendCurrentWords();
                    currentWords.clear();
                    currentWords.addLast(sentenceBeginning());
                } else {
                    appendCurrentWords();
                }
            }
        }
        return chains;
    }

    /**
     * Appends current word to common result and shifts to next state.
     */
    private void appendCurrentWords() {
        if (currentWord.length() > 0) {
            currentWords.addLast(currentWord.toString());
            currentWord.setLength(0);
        }
        while (currentWords.size() >= 3) {
            String firstWord = currentWords.removeFirst();
            Iterator<String> it = currentWords.iterator();
            String secondWord = it.next();
            String third = it.next();

            ChainPhrase phrase = new ChainPhrase(firstWord, secondWord);
            List<String> existingWordList = chains.get(phrase);
            if (existingWordList == null) {
                existingWordList = new ArrayList<>();
                chains.put(phrase, existingWordList);
            }
            existingWordList.add(third);
        }
    }
}
