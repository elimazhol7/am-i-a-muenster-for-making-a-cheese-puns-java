package org.example;
import java.util.*;
import java.util.Locale;

public class CheeseAnalyzer {

    public static class Results {
        public int pasteurizedCount;
        public int rawCount;
        public int organicHighMoistureCount;
        public String mostCommonMilkType;
        public Map<String, Integer> milkTypeTallies = new LinkedHashMap<>();
    }

    public static Results analyze(CheeseReader reader) throws Exception {
        Results results = new Results();

        results.milkTypeTallies.put("cow", 0);
        results.milkTypeTallies.put("goat", 0);
        results.milkTypeTallies.put("ewe", 0);
        results.milkTypeTallies.put("buffalo", 0);

        CheeseReader.Row row;
        while ((row = reader.next()) != null) {

            // 1) Pasteurized vs Raw
            String treat = CheeseRowCleaner.cleanString(row.MilkTreatmentTypeEn);
            if (treat != null) {
                String lt = treat.toLowerCase(Locale.ROOT);
                if (lt.contains("pasteurized")) results.pasteurizedCount++;
                if (lt.contains("raw")) results.rawCount++;
            }

            // 2) Organic with Moisture > 41
            Integer org = CheeseRowCleaner.parseIntOrNull(row.Organic);
            Double moist = CheeseRowCleaner.parseDoubleOrNull(row.MoisturePercent);
            if (org != null && moist != null && org == 1 && moist > 41.0) {
                results.organicHighMoistureCount++;
            }

            // 3) Milk type
            String milk = CheeseRowCleaner.cleanString(row.MilkTypeEn);
            if (milk != null) {
                String lt = milk.toLowerCase(Locale.ROOT).trim();
                if (results.milkTypeTallies.containsKey(lt)) {
                    results.milkTypeTallies.put(lt, results.milkTypeTallies.get(lt) + 1);
                }
            }
        }

        // find most common milk type
        String bestType = null;
        int bestCount = -1;
        for (Map.Entry<String, Integer> e : results.milkTypeTallies.entrySet()) {
            if (e.getValue() > bestCount) {
                bestType = e.getKey();
                bestCount = e.getValue();
            }
        }
        results.mostCommonMilkType = bestType;

        return results;
    }
}
