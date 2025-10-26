package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class CheeseRowCleanerTest {

    @Test
    void cleanString_trimsAndNullsBlank() {
        assertEquals("Gouda", CheeseRowCleaner.cleanString("  Gouda  "));
        assertNull(CheeseRowCleaner.cleanString("   "));
        assertNull(CheeseRowCleaner.cleanString(null));
    }

    @Test
    void parseIntOrNull_basic() {
        assertEquals(10, CheeseRowCleaner.parseIntOrNull("10"));
        assertNull(CheeseRowCleaner.parseIntOrNull("abc"));
        assertNull(CheeseRowCleaner.parseIntOrNull(" "));
        assertNull(CheeseRowCleaner.parseIntOrNull(null));
    }

    @Test
    void parseDoubleOrNull_basic() {
        assertEquals(3.14, CheeseRowCleaner.parseDoubleOrNull("3.14"));
        assertNull(CheeseRowCleaner.parseDoubleOrNull("abc"));
        assertNull(CheeseRowCleaner.parseDoubleOrNull(""));
        assertNull(CheeseRowCleaner.parseDoubleOrNull(null));
    }
}
