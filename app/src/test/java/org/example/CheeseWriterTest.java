package org.example;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CheeseWriterTest {

    @Test
    void writeResults_writesExpectedFormat(@TempDir File temp) throws Exception {
        File out = new File(temp, "output.txt");

        // fabricate results
        CheeseAnalyzer.Results r = new CheeseAnalyzer.Results();
        r.pasteurizedCount = 10;
        r.rawCount = 4;
        r.organicHighMoistureCount = 3;
        r.milkTypeTallies = new LinkedHashMap<>();
        r.milkTypeTallies.put("cow", 7);
        r.milkTypeTallies.put("goat", 4);
        r.milkTypeTallies.put("ewe", 2);
        r.milkTypeTallies.put("buffalo", 1);
        r.mostCommonMilkType = "cow";

        CheeseWriter.writeResults(out.getAbsolutePath(), r);

        String text = Files.readString(out.toPath());
        assertTrue(text.contains("Pasteurized milk cheeses: 10"));
        assertTrue(text.contains("Raw milk cheeses: 4"));
        assertTrue(text.contains("Organic cheeses with MoisturePercent > 41.0: 3"));
        assertTrue(text.contains("Cow: 7"));
        assertTrue(text.contains("Goat: 4"));
        assertTrue(text.contains("Ewe: 2"));
        assertTrue(text.contains("Buffalo: 1"));
        assertTrue(text.contains("Most common milk type: Cow"));
    }
}
