package org.example;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CheeseList implements Iterable<CheeseList.Cheese> {

    public static class Cheese {
        public String MilkTreatmentTypeEn;
        public String Organic;
        public String MoisturePercent;
        public String MilkTypeEn;
    }

    private final List<Cheese> items = new ArrayList<>();

    public void add(Cheese c) { items.add(c); }
    public int size() { return items.size(); }
    @Override
    public Iterator<Cheese> iterator() { return items.iterator(); }
}

