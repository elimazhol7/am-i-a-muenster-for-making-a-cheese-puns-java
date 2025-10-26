package org.example;
import java.util.*;
import java.util.Locale;

public class CheeseAnalyzerWithArrays {
    public static CheeseAnalyzer.Results analyzeWithArrays(List<String[]> rows, Map<String, Integer> headerIndex) {
        CheeseAnalyzer.Results results = new CheeseAnalyzer.Results();
        results.milkTypeTallies.put("cow", 0);
        results.milkTypeTallies.put("goat", 0);
        results.milkTypeTallies.put("ewe", 0);
        results.milkTypeTallies.put("buffalo", 0);

        int iMilkTreat = headerIndex.getOrDefault("milktreatmenttypeen", -1);
        int iOrganic   = headerIndex.getOrDefault("organic", -1);
        int iMoist     = headerIndex.getOrDefault("moisturepercent", -1);
        int iMilkType  = headerIndex.getOrDefault("milktypeen", -1);

        for (String[] r : rows) {
            if (iMilkTreat >= 0 && iMilkTreat < r.length) {
                String treat = CheeseRowCleaner.cleanString(r[iMilkTreat]);
                if (treat != null) {
                    String lt = treat.toLowerCase(Locale.ROOT);
                    if (lt.contains("pasteurized")) results.pasteurizedCount++;
                    if (lt.contains("raw")) results.rawCount++;
                }
            }

            Integer org = (iOrganic >= 0 && iOrganic < r.length) ? CheeseRowCleaner.parseIntOrNull(r[iOrganic]) : null;
            Double moist = (iMoist >= 0 && iMoist < r.length) ? CheeseRowCleaner.parseDoubleOrNull(r[iMoist]) : null;
            if (org != null && moist != null && org == 1 && moist > 41.0) {
                results.organicHighMoistureCount++;
            }

            if (iMilkType >= 0 && iMilkType < r.length) {
                String milk = CheeseRowCleaner.cleanString(r[iMilkType]);
                if (milk != null && results.milkTypeTallies.containsKey(milk.toLowerCase(Locale.ROOT))) {
                    String lt = milk.toLowerCase(Locale.ROOT);
                    results.milkTypeTallies.put(lt, results.milkTypeTallies.get(lt) + 1);
                }
            }
        }

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
