package org.example;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class AppTest {

    @TempDir Path tmp;

    @Test
    void main_createsOutput() throws Exception {
        Path csv = tmp.resolve("cheese_data.csv");
        try (BufferedWriter w = Files.newBufferedWriter(csv)) {
            w.write("MilkTreatmentTypeEn,Organic,MoisturePercent,MilkTypeEn\n");
            w.write("Pasteurized,1,55.0,cow\n");
            w.write("Raw,0,40.0,cow\n");
        }
        Path out = tmp.resolve("output.txt");

        App.main(new String[]{csv.toString(), out.toString()});

        assertTrue(Files.exists(out));
        String txt = Files.readString(out);
        assertTrue(txt.contains("Pasteurized milk cheeses: 1"));
        assertTrue(txt.contains("Raw milk cheeses: 1"));
        assertTrue(txt.contains("Most common milk type: Cow"));
    }
}
