package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

class CheeseListTest {

    @Test
    void addAndIterate_simple() {
        CheeseList list = new CheeseList();

        CheeseList.Cheese c1 = new CheeseList.Cheese();
        c1.MilkTypeEn = "cow";
        list.add(c1);

        CheeseList.Cheese c2 = new CheeseList.Cheese();
        c2.MilkTypeEn = "goat";
        list.add(c2);

        int count = 0;
        for (CheeseList.Cheese c : list) {
            assertNotNull(c);
            count++;
        }
        assertEquals(2, count);
        assertEquals(2, list.size());
    }
}
