package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class CheeseAnalyzerWithArraysTest {

    @Test
    void simpleArrayInput() {
        String[] header = {"MilkTreatmentTypeEn","Organic","MoisturePercent","MilkTypeEn"};
        Map<String,Integer> idx = CheeseParser.headerToIndex(header);

        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Pasteurized","1","55.0","cow"});
        rows.add(new String[]{"Raw","0","40.0","cow"});
        rows.add(new String[]{"Pasteurized","1","60.0","goat"});

        CheeseAnalyzer.Results r = CheeseAnalyzerWithArrays.analyzeWithArrays(rows, idx);

        assertEquals(2, r.pasteurizedCount);
        assertEquals(1, r.rawCount);
        assertEquals(2, r.organicHighMoistureCount);
        assertEquals(2, r.milkTypeTallies.get("cow"));
        assertEquals("cow", r.mostCommonMilkType);
    }
}
