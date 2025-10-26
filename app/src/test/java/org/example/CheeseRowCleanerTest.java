package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class CheeseRowCleanerTest {

    @Test
    void cleanString_trimsAndNulls() {
        assertNull(CheeseRowCleaner.cleanString(null));
        assertNull(CheeseRowCleaner.cleanString(""));
        assertNull(CheeseRowCleaner.cleanString("   "));
        assertEquals("Cow", CheeseRowCleaner.cleanString("  Cow  "));
    }

    @Test
    void parseIntOrNull_handlesValidAndInvalid() {
        assertNull(CheeseRowCleaner.parseIntOrNull(null));
        assertNull(CheeseRowCleaner.parseIntOrNull(" "));
        assertNull(CheeseRowCleaner.parseIntOrNull("abc"));
        assertEquals(1, CheeseRowCleaner.parseIntOrNull("1"));
        assertEquals(42, CheeseRowCleaner.parseIntOrNull(" 42 "));
    }

    @Test
    void parseDoubleOrNull_handlesValidAndInvalid() {
        assertNull(CheeseRowCleaner.parseDoubleOrNull(null));
        assertNull(CheeseRowCleaner.parseDoubleOrNull(" "));
        assertNull(CheeseRowCleaner.parseDoubleOrNull("abc"));
        assertEquals(41.5, CheeseRowCleaner.parseDoubleOrNull("41.5"));
        assertEquals(2.0, CheeseRowCleaner.parseDoubleOrNull(" 2 "), 0.0001);
    }
}
