package org.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {
        // Default file names
        String input = args.length > 0 ? args[0] : "cheese_data.csv";
        String output = args.length > 1 ? args[1] : "output.txt";

        try {
            //If input file doesn't exist, we can create a small sample one
            Path inputPath = Paths.get(input);
            if (!Files.exists(inputPath)) {
                System.out.println("Input file not found. Creating a sample cheese_data.csv...");
                String sampleData =
                        "MilkTreatmentTypeEn,Organic,MoisturePercent,MilkTypeEn\n" +
                        "Pasteurized,1,55.0,cow\n" +
                        "Raw,0,40.0,goat\n" +
                        "Pasteurized,1,60.0,buffalo\n";
                Files.writeString(inputPath, sampleData);
            }

            // using try-with-resources to close reader automatically
            try (CheeseReader reader = new CheeseReader(input)) {
                CheeseAnalyzer.Results results = CheeseAnalyzer.analyze(reader);
                CheeseWriter.writeResults(output, results);
            }

            System.out.println("Analysis complete. Results written to: " + output);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
