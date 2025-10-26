package org.example;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CheeseReaderTest {

    @TempDir Path tmp;

    @Test
    void readsTwoRows_thenNull() throws Exception {
        Path csv = tmp.resolve("cheese.csv");
        try (BufferedWriter w = Files.newBufferedWriter(csv)) {
            w.write("MilkTreatmentTypeEn,Organic,MoisturePercent,MilkTypeEn\n");
            w.write("Pasteurized,1,55.0,cow\n");
            w.write("Raw,0,40.0,goat\n");
        }

        try (CheeseReader r = new CheeseReader(csv.toString())) {
            CheeseReader.Row a = r.next();
            assertEquals("Pasteurized", a.MilkTreatmentTypeEn);
            assertEquals("1", a.Organic);
            assertEquals("55.0", a.MoisturePercent);
            assertEquals("cow", a.MilkTypeEn);

            CheeseReader.Row b = r.next();
            assertEquals("Raw", b.MilkTreatmentTypeEn);
            assertEquals("0", b.Organic);
            assertEquals("40.0", b.MoisturePercent);
            assertEquals("goat", b.MilkTypeEn);

            assertNull(r.next());
        }
    }
}
