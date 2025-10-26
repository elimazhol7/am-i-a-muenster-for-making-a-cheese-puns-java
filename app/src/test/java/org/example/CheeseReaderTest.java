package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CheeseReaderTest {

    @Test
    void next_readsRows(@TempDir File temp) throws Exception {
        File csv = new File(temp, "cheese.csv");
        try (BufferedWriter w = new BufferedWriter(new FileWriter(csv))) {
            w.write("Id,MilkTreatmentTypeEn,Organic,MoisturePercent,MilkTypeEn\n");
            w.write("1,Pasteurized,1,42.0,Cow\n");
            w.write("2,Raw,0,39.0,Goat\n");
        }

        try (CheeseReader reader = new CheeseReader(csv.getAbsolutePath())) {
            CheeseReader.Row r1 = reader.next();
            assertNotNull(r1);
            assertEquals("Pasteurized", r1.MilkTreatmentTypeEn);
            assertEquals("1", r1.Organic);
            assertEquals("42.0", r1.MoisturePercent);
            assertEquals("Cow", r1.MilkTypeEn);

            CheeseReader.Row r2 = reader.next();
            assertNotNull(r2);
            assertEquals("Raw", r2.MilkTreatmentTypeEn);
            assertEquals("0", r2.Organic);
            assertEquals("39.0", r2.MoisturePercent);
            assertEquals("Goat", r2.MilkTypeEn);

            assertNull(reader.next()); // EOF
        }
    }
}
