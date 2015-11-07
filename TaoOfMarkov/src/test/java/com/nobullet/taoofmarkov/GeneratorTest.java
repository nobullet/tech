package com.nobullet.taoofmarkov;

import com.nobullet.taoofmarkov.Generator;
import com.nobullet.taoofmarkov.Parser;
import static org.junit.Assert.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

/**
 * Tests for generator.
 */
public class GeneratorTest {

    static final String TEST = "The happy dog followed the white and red car.";
    static final String TEST2 = "The happy dog followed the white, red car.";

    @Test
    public void testGenerator() throws IOException {
        Parser parser = new Parser(new ByteArrayInputStream(TEST.getBytes(Charset.forName("UTF-8"))));
        Generator generator = new Generator(parser.parse());

        Set<String> result = new HashSet<>();
        generator.generateRandomSentences(10, result::add);

        assertEquals("Same sentence is expected to be generated 10 times.", 1, result.size());
        assertEquals(TEST, result.iterator().next().trim());
    }
    
    @Test
    public void testGeneratorWithCommas() throws IOException {
        Parser parser = new Parser(new ByteArrayInputStream(TEST2.getBytes(Charset.forName("UTF-8"))));
        Generator generator = new Generator(parser.parse());

        assertEquals(TEST2, generator.generateRandomSentence());
    }
}
