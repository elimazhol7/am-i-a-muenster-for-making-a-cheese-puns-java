package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CheeseAnalyzerTest {

    @Test
    void analyze_computesAllMetrics(@TempDir File temp) throws Exception {
        File csv = new File(temp, "cheese.csv");
        try (BufferedWriter w = new BufferedWriter(new FileWriter(csv))) {
            w.write("Id,MilkTreatmentTypeEn,Organic,MoisturePercent,MilkTypeEn\n");
            w.write("1,Pasteurized,1,42.5,Cow\n");
            w.write("2,Raw,1,40.0,Goat\n");
            w.write("3,Pasteurized,0,50.0,Ewe\n");
            w.write("4,Pasteurized,1,41.1,Buffalo\n");
            w.write("5,,1,45.0,Cow\n");               // missing treatment -> skip for that calc
            w.write("6,Pasteurized,1,,Goat\n");       // missing moisture -> skip organic>41
            w.write("7,Raw,0,38.0,Cow\n");
            w.write("8,Raw,1,41.0,Cow\n");            // equals 41.0 -> not greater
            w.write("9,Pasteurized,1,41.01,Cow\n");   // > 41
        }

        try (CheeseReader reader = new CheeseReader(csv.getAbsolutePath())) {
            CheeseAnalyzer.Results r = CheeseAnalyzer.analyze(reader);

            // Pasteurized: rows 1,3,4,6,9 = 5
            assertEquals(5, r.pasteurizedCount);
            // Raw: rows 2,7,8 = 3
            assertEquals(3, r.rawCount);
            // Organic & moisture > 41.0: rows 1,4,9 = 3
            assertEquals(3, r.organicHighMoistureCount);

            // Milk type tallies (exact single types): Cow 4 (1,5,8,9), Goat 2 (2,6), Ewe 1 (3), Buffalo 1 (4)
            assertEquals(4, r.milkTypeTallies.get("cow"));
            assertEquals(2, r.milkTypeTallies.get("goat"));
            assertEquals(1, r.milkTypeTallies.get("ewe"));
            assertEquals(1, r.milkTypeTallies.get("buffalo"));
            assertEquals("cow", r.mostCommonMilkType);
        }
    }
}
