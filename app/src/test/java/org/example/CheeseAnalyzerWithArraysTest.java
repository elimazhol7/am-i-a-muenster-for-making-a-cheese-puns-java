package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class CheeseAnalyzerWithArraysTest {

    @Test
    void analyzeWithArrays_works() {
        String[] header = {"Id","MilkTreatmentTypeEn","Organic","MoisturePercent","MilkTypeEn"};
        Map<String,Integer> idx = CheeseParser.headerToIndex(header);

        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"1","Pasteurized","1","42.0","Cow"});
        rows.add(new String[]{"2","Raw","0","39.0","Goat"});
        rows.add(new String[]{"3","Pasteurized","1","41.5","Cow"});
        rows.add(new String[]{"4","","1","45.0","Ewe"}); // missing treatment

        CheeseAnalyzer.Results r = CheeseAnalyzerWithArrays.analyzeWithArrays(rows, idx);

        assertEquals(2, r.pasteurizedCount);
        assertEquals(1, r.rawCount);
        assertEquals(2, r.organicHighMoistureCount); // rows 1 & 3
        assertEquals(2, r.milkTypeTallies.get("cow"));
        assertEquals(1, r.milkTypeTallies.get("goat"));
        assertEquals(1, r.milkTypeTallies.get("ewe"));

        assertEquals("cow", r.mostCommonMilkType);
    }
}
