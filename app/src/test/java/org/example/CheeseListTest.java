package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

class CheeseListTest {

    @Test
    void addAndIterate() {
        CheeseList list = new CheeseList();

        CheeseList.Cheese c1 = new CheeseList.Cheese();
        c1.MilkTreatmentTypeEn = "Pasteurized";
        c1.Organic = "1";
        c1.MoisturePercent = "42.0";
        c1.MilkTypeEn = "Cow";

        CheeseList.Cheese c2 = new CheeseList.Cheese();
        c2.MilkTreatmentTypeEn = "Raw";
        c2.Organic = "0";
        c2.MoisturePercent = "39.0";
        c2.MilkTypeEn = "Goat";

        list.add(c1);
        list.add(c2);

        int count = 0;
        for (CheeseList.Cheese c : list) {
            assertNotNull(c.MilkTypeEn);
            count++;
        }
        assertEquals(2, count);
        assertEquals(2, list.size());
    }
}

