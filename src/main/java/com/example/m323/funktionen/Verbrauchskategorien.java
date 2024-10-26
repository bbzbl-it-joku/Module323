package com.example.m323.funktionen;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.example.m323.Main;
import com.example.m323.utils.TableFormatterUtils;
import com.example.m323.utils.data.DataSet;

/**
 * This class categorizes energy consumption data into different usage levels.
 * 
 * @author Joshua Kunz
 * @author Seth Schmutz
 * @version 1.0
 * @since 10.10.2024
 */
public class Verbrauchskategorien {
    private static final int BAR_WIDTH = 30;
    private static final int[] COLUMN_WIDTHS = {8, 28, 13, 27}; // Jahr, Kategorie, Gemeinden, Verteilung
    private static final int TOTAL_WIDTH = 80; // Total width including borders
    
    private static final String CATEGORY_LOW = "Niedrig";
    private static final String CATEGORY_MEDIUM = "Mittel";
    private static final String CATEGORY_HIGH = "Hoch";
    private static final List<String> CATEGORIES = List.of(CATEGORY_LOW, CATEGORY_MEDIUM, CATEGORY_HIGH);
    
    private static final double THRESHOLD_LOW = 10000.0;
    private static final double THRESHOLD_MEDIUM = 50000.0;

    private static void printHeader() {
        System.out.println("\n╔" + "═".repeat(TOTAL_WIDTH - 2) + "╗");
        System.out.println("║" + TableFormatterUtils.centerText("Verteilung der Energieverbrauchskategorien", TOTAL_WIDTH - 2) + "║");
        System.out.println("╠" + "═".repeat(TOTAL_WIDTH - 2) + "╣");
        System.out.println("║  Jahr  │  Kategorie                    │  Gemeinden  │  Verteilung            ║");
        System.out.println("╟" + "─".repeat(COLUMN_WIDTHS[0]) + "┼" + 
                         "─".repeat(COLUMN_WIDTHS[1]) + "┼" +
                         "─".repeat(COLUMN_WIDTHS[2]) + "┼" +
                         "─".repeat(COLUMN_WIDTHS[3]) + "╢");
    }

    private static void printYearSeparator() {
        System.out.println("╟" + "─".repeat(COLUMN_WIDTHS[0]) + "┼" + 
                         "─".repeat(COLUMN_WIDTHS[1]) + "┼" +
                         "─".repeat(COLUMN_WIDTHS[2]) + "┼" +
                         "─".repeat(COLUMN_WIDTHS[3]) + "╢");
    }

    private static void printFooter() {
        System.out.println("╚" + "═".repeat(TOTAL_WIDTH - 2) + "╝");
    }

    private static String getCategoryDescription(String category) {
        if (CATEGORY_LOW.equals(category)) {
            return String.format("%-6s (0-%-,6.0f MWh)", category, THRESHOLD_LOW);
        } else if (CATEGORY_MEDIUM.equals(category)) {
            return String.format("%-6s (%-,6.0f-%-,6.0f MWh)", 
                                category, THRESHOLD_LOW, THRESHOLD_MEDIUM);
        } else if (CATEGORY_HIGH.equals(category)) {
            return String.format("%-6s (>%-,6.0f MWh)", category, THRESHOLD_MEDIUM);
        }
        return category;
    }

    private static void printCategoryRow(Integer year, String category, long count, long maxCount, boolean showYear) {
        System.out.printf("║  %s │  %-24s  │  %9d  │  %s║%n",
            showYear ? String.format("%4d", year) : "    ",
            getCategoryDescription(category),
            count,
            TableFormatterUtils.createBar(count, maxCount, BAR_WIDTH));
    }

    private static String categorizeConsumption(double value) {
        if (value <= THRESHOLD_LOW) {
            return CATEGORY_LOW;
        } else if (value <= THRESHOLD_MEDIUM) {
            return CATEGORY_MEDIUM;
        }
        return CATEGORY_HIGH;
    }

    /**
     * Categorizes consumption data using an iterative approach.
     * 
     * @author Seth Schmutz
     */
    public static void iterativeFunction() {
        Map<Integer, Map<String, Long>> yearData = new TreeMap<>();
        
        // Collect data for each year
        for (DataSet data : Main.getDataLoader().getData()) {
            int year = data.getJahr();
            yearData.putIfAbsent(year, new HashMap<>());
            
            String category = categorizeConsumption(data.getWert());
            yearData.get(year).merge(category, 1L, Long::sum);
        }

        printFormattedResults(yearData);
    }

    /**
     * Categorizes consumption data using a functional approach.
     * Uses streams to group and count municipalities by consumption category.
     * 
     * @author Joshua Kunz
     */
    public static void functionalFunction() {
        Map<Integer, Map<String, Long>> yearData = Main.getDataLoader().getData().stream()
            .collect(Collectors.groupingBy(
                DataSet::getJahr,
                TreeMap::new,
                Collectors.groupingBy(
                    data -> categorizeConsumption(data.getWert()),
                    Collectors.counting()
                )
            ));

        printFormattedResults(yearData);
    }

    private static void printFormattedResults(Map<Integer, Map<String, Long>> yearData) {
        printHeader();

        Integer lastYear = ((TreeMap<Integer, Map<String, Long>>) yearData).lastKey();
        yearData.forEach((year, categories) -> {
            long maxCount = categories.values().stream().mapToLong(Long::valueOf).max().orElse(0L);
            
            for (int i = 0; i < CATEGORIES.size(); i++) {
                String category = CATEGORIES.get(i);
                long count = categories.getOrDefault(category, 0L);
                printCategoryRow(year, category, count, maxCount, i == 0); // Show year only for first category
            }

            if (!year.equals(lastYear)) {
                printYearSeparator();
            }
        });

        printFooter();
    }
}