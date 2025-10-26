package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class CheeseWriter {

    public static void writeResults(String filePath, CheeseAnalyzer.Results results) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("Pasteurized milk cheeses: " + results.pasteurizedCount);
            writer.newLine();

            writer.write("Raw milk cheeses: " + results.rawCount);
            writer.newLine();

            writer.write("Organic cheeses with MoisturePercent > 41.0: " + results.organicHighMoistureCount);
            writer.newLine();
            writer.newLine();

            writer.write("Milk type tallies:");
            writer.newLine();

            for (Map.Entry<String, Integer> entry : results.milkTypeTallies.entrySet()) {
                writer.write("  " + capitalize(entry.getKey()) + ": " + entry.getValue());
                writer.newLine();
            }

            writer.newLine();
            writer.write("Most common milk type: " + capitalize(results.mostCommonMilkType));

        } catch (IOException e) {
            System.err.println("Error writing output file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
