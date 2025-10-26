package org.example;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class CheeseParserTest {

    @Test
    void parseCsvLine_handlesQuotedCommas() {
        String line = "Id,\"Milk,Treat\",Organic,MoisturePercent,MilkTypeEn";
        String[] cols = CheeseParser.parseCsvLine(line);
        assertArrayEquals(new String[] {
                "Id", "Milk,Treat", "Organic", "MoisturePercent", "MilkTypeEn"
        }, cols);
    }

    @Test
    void headerToIndex_lowercasesAndMaps() {
        String[] header = {"Id", "MilkTreatmentTypeEn", "Organic", "MoisturePercent", "MilkTypeEn"};
        Map<String,Integer> idx = CheeseParser.headerToIndex(header);
        assertEquals(0, idx.get("id"));
        assertEquals(1, idx.get("milktreatmenttypeen"));
        assertEquals(2, idx.get("organic"));
        assertEquals(3, idx.get("moisturepercent"));
        assertEquals(4, idx.get("milktypeen"));
    }
}
