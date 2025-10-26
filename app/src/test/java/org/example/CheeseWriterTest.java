package org.example;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CheeseWriterTest {

    @TempDir Path tmp;

    @Test
    void writesOutputFile() throws Exception {
        CheeseAnalyzer.Results r = new CheeseAnalyzer.Results();
        r.pasteurizedCount = 2;
        r.rawCount = 1;
        r.organicHighMoistureCount = 2;
        r.milkTypeTallies.put("cow", 2);
        r.milkTypeTallies.put("goat", 1);
        r.mostCommonMilkType = "cow";

        Path out = tmp.resolve("out.txt");
        CheeseWriter.writeResults(out.toString(), r);

        String s = Files.readString(out);
        assertTrue(s.contains("Pasteurized milk cheeses: 2"));
        assertTrue(s.contains("Raw milk cheeses: 1"));
        assertTrue(s.contains("Organic cheeses with MoisturePercent > 41.0: 2"));
        assertTrue(s.contains("Cow: 2"));
        assertTrue(s.contains("Most common milk type: Cow"));
    }
}
