package com.nobullet;

import com.nobullet.taoofmarkov.Generator;
import com.nobullet.taoofmarkov.Parser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Starting point for 'The Tao of Markov' application.
 */
public class App {

    private static final int NUMBER_OF_SENTENCES = 100;
    private static final PrintStream stdout = System.out;
    
    public static void main(String... args) throws FileNotFoundException, IOException {
        if (args == null || args.length == 0) {
            throw new IllegalStateException("Please, provide a file.");
        }
        stdout.println();
        List<String> sentences = new ArrayList<>();
        FileInputStream sourceContents = new FileInputStream(new File(args[0]));
        new Generator(new Parser(sourceContents).parse())
                .generateRandomSentences(NUMBER_OF_SENTENCES, sentences::add);
        Collections.sort(sentences);
        int index = 1;
        for (String sentence : sentences) {
            stdout.print(index++);
            stdout.print(". ");
            stdout.println(sentence);
        }
    }
}
