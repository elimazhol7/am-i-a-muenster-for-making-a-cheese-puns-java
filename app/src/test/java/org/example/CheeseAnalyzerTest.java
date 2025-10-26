package org.example;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CheeseAnalyzerTest {

    @TempDir Path tmp;

    @Test
    void analyze_countsAreCorrect() throws Exception {
        Path csv = tmp.resolve("data.csv");
        try (BufferedWriter w = Files.newBufferedWriter(csv)) {
            w.write("MilkTreatmentTypeEn,Organic,MoisturePercent,MilkTypeEn\n");
            w.write("Pasteurized,1,55.0,cow\n");   // counts: pasteurized + organic moist>41 + cow
            w.write("Raw,0,40.0,cow\n");          // counts: raw + cow
            w.write("Pasteurized,1,60.0,goat\n"); // counts: pasteurized + organic moist>41 + goat
        }

        try (CheeseReader reader = new CheeseReader(csv.toString())) {
            CheeseAnalyzer.Results r = CheeseAnalyzer.analyze(reader);

            assertEquals(2, r.pasteurizedCount);
            assertEquals(1, r.rawCount);
            assertEquals(2, r.organicHighMoistureCount);

            Map<String,Integer> t = r.milkTypeTallies;
            assertEquals(2, t.get("cow"));
            assertEquals(1, t.get("goat"));

            assertEquals("cow", r.mostCommonMilkType);
        }
    }
}
