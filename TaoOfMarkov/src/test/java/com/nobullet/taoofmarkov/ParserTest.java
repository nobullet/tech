package com.nobullet.taoofmarkov;

import com.nobullet.taoofmarkov.ChainPhrase;
import com.nobullet.taoofmarkov.Parser;
import com.nobullet.taoofmarkov.Punctuation;
import static org.junit.Assert.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import org.junit.Test;

/**
 * Tests for parser.
 */
public class ParserTest implements Punctuation {

    static final String TEST = "The happy dog 1 2 3  followed \n\n\n\r   the white and red car.";
    static final String TEST_2 = "The colors: red, green, blue.";

    @Test
    public void testCommonCase() throws IOException {
        Parser parser = new Parser(new ByteArrayInputStream(TEST.getBytes(Charset.forName("UTF-8"))));
        Map<ChainPhrase, List<String>> chains = parser.parse();
        
        assertEquals(9, chains.size());
        
        assertEquals(".", chains.get(new ChainPhrase("red", "car")).get(0));
        assertEquals("car", chains.get(new ChainPhrase("and", "red")).get(0));
        assertEquals("dog", chains.get(new ChainPhrase("The", "happy")).get(0));
        assertEquals("happy", chains.get(new ChainPhrase(sentenceBeginning(), "The")).get(0));
    }
    
    @Test
    public void testColon() throws IOException {
        Parser parser = new Parser(new ByteArrayInputStream(TEST_2.getBytes(Charset.forName("UTF-8"))));
        Map<ChainPhrase, List<String>> chains = parser.parse();
        
        assertEquals(8, chains.size());
        
        assertEquals(".", chains.get(new ChainPhrase(",", "blue")).get(0));
        assertEquals(",", chains.get(new ChainPhrase(":", "red")).get(0));
        assertEquals("colors", chains.get(new ChainPhrase(sentenceBeginning(), "The")).get(0));
    }
}
