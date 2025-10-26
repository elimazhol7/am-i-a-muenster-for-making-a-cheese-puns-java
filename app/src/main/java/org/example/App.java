package org.example;

import java.nio.file.*;
import java.io.*;
import java.util.*;

public class App {
    public static void main(String[] args) {
        String input = args.length > 0 ? args[0] : "cheese_data.csv";
        String output = args.length > 1 ? args[1] : "output.txt";

        try {
            CheeseReader reader = new CheeseReader(input);
            CheeseAnalyzer.Results results = CheeseAnalyzer.analyze(reader);

            CheeseWriter.writeResults(output, results);

            System.out.println("Analysis complete. Results written to: " + output);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
