package org.example;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class CheeseParserTest {

    @Test
    void parseCsvLine_handlesQuotes() {
        String line = "A,\"B, inside\",C";
        String[] parts = CheeseParser.parseCsvLine(line);
        assertArrayEquals(new String[]{"A","B, inside","C"}, parts);
    }

    @Test
    void headerToIndex_lowercasesAndTrims() {
        String[] header = {" MilkTypeEn ", "Organic"};
        Map<String,Integer> map = CheeseParser.headerToIndex(header);
        assertTrue(map.containsKey("milktypeen"));
        assertTrue(map.containsKey("organic"));
        assertEquals(0, map.get("milktypeen"));
        assertEquals(1, map.get("organic"));
    }
}
