package org.example;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Streaming reader for cheese CSV.
 */
public class CheeseReader implements Closeable {

    public static class Row {
        public String MilkTreatmentTypeEn;
        public String Organic;
        public String MoisturePercent;
        public String MilkTypeEn;
    }

    private final BufferedReader br;
    private final Map<String, Integer> headerIndex;
    private boolean closed = false;

    public CheeseReader(String csvPath) throws Exception {
        this.br = new BufferedReader(new InputStreamReader(new FileInputStream(csvPath), "UTF-8"));
        String headerLine = br.readLine();
        if (headerLine == null) throw new RuntimeException("Empty CSV: " + csvPath);
        String[] header = CheeseParser.parseCsvLine(headerLine);
        this.headerIndex = CheeseParser.headerToIndex(header);
    }

    public Row next() throws IOException {
        if (closed) return null;
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] cols = CheeseParser.parseCsvLine(line);

            Row r = new Row();
            r.MilkTreatmentTypeEn = get(cols, "milktreatmenttypeen");
            r.Organic             = get(cols, "organic");
            r.MoisturePercent     = get(cols, "moisturepercent");
            r.MilkTypeEn          = get(cols, "milktypeen");
            return r;
        }
        close();
        return null;
    }

    private String get(String[] cols, String key) {
        Integer idx = headerIndex.get(key);
        if (idx == null || idx < 0 || idx >= cols.length) return null;
        return cols[idx];
    }

    @Override
    public void close() throws IOException {
        if (!closed) {
            closed = true;
            br.close();
        }
    }
}
