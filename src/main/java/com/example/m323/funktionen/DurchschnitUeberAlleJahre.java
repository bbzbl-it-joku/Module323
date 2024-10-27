package com.example.m323.funktionen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.example.m323.Main;
import com.example.m323.utils.TableFormatterUtils;
import com.example.m323.utils.data.DataSet;

/**
 * This class calculates the average consumption over all years.
 * 
 * @author Joshua Kunz
 * @author Seth Schmutz
 * @version 1.0
 * @since 10.10.2024
 */
public class DurchschnitUeberAlleJahre {
    private static final int BAR_WIDTH = 45;
    private static final int[] COLUMN_WIDTHS = {8, 22, 47}; // Width for each column
    private static final String TITLE = "Durchschnittlicher Energieverbrauch";
    
    /**
     * Prints a beautiful header for the consumption statistics.
     */
    private static void printHeader() {
        int totalWidth = COLUMN_WIDTHS[0] + COLUMN_WIDTHS[1] + COLUMN_WIDTHS[2] + 4; // +4 for borders
        System.out.println("\n╔" + "═".repeat(totalWidth - 2) + "╗");
        System.out.println("║" + TableFormatterUtils.centerText(TITLE, totalWidth - 2) + "║");
        System.out.println("╠" + "═".repeat(COLUMN_WIDTHS[0]) + "╦" + "═".repeat(COLUMN_WIDTHS[1]) + 
                         "╦" + "═".repeat(COLUMN_WIDTHS[2]) + "╣");
        System.out.println("║  Jahr  ║  Durchschnitt (MWh)  ║  Visualisierung                               ║");
        System.out.println("╠" + "═".repeat(COLUMN_WIDTHS[0]) + "╬" + "═".repeat(COLUMN_WIDTHS[1]) + 
                         "╬" + "═".repeat(COLUMN_WIDTHS[2]) + "╣");
    }

    /**
     * Prints the footer for the consumption statistics.
     */
    private static void printFooter() {
        System.out.println("╚" + "═".repeat(COLUMN_WIDTHS[0]) + "╩" + "═".repeat(COLUMN_WIDTHS[1]) + 
                         "╩" + "═".repeat(COLUMN_WIDTHS[2]) + "╝");
    }

    /**
     * Prints a formatted row of data.
     */
    private static void printRow(int year, double value, double maxValue) {
        System.out.printf("║  %4d  ║     %15s  ║ %s ║%n",
            year,
            TableFormatterUtils.formatNumber(value),
            TableFormatterUtils.createBar(value, maxValue, BAR_WIDTH));
    }

    /**
     * Calculates the average consumption using an iterative approach.
     * 
     * @author Seth Schmutz
     */
    public static void iterativeFunction() {
        List<Integer> uniqueYears = new ArrayList<>();
        Map<Integer, Double> averages = new TreeMap<>(); // Using TreeMap for sorted keys

        // Calculate averages
        for (DataSet data : Main.getDataLoader().getData()) {
            if (!uniqueYears.contains(data.getJahr())) {
                uniqueYears.add(data.getJahr());
            }
        }

        for (Integer jahr : uniqueYears) {
            double total = 0;
            Integer count = 0;
            for (DataSet data : Main.getDataLoader().getData()) {
                if (jahr == data.getJahr()) {
                    total += data.getWert();
                    count++;
                }
            }
            averages.put(jahr, total / count);
        }

        // Find maximum value for scaling
        double maxAverage = averages.values().stream()
            .mapToDouble(Double::doubleValue)
            .max()
            .orElse(0.0);

        // Print results
        printHeader();
        averages.forEach((jahr, avg) -> printRow(jahr, avg, maxAverage));
        printFooter();
    }

    /**
     * Calculates the average consumption using a functional approach.
     * Uses Java streams to group data by year and calculate averages.
     * 
     * @author Joshua Kunz
     */
    public static void functionalFunction() {
        // Collect all averages first
        Map<Integer, Double> averages = Main.getDataLoader().getData().stream()
            .collect(Collectors.groupingBy(
                DataSet::getJahr,
                TreeMap::new, // Use TreeMap for sorted keys
                Collectors.averagingDouble(DataSet::getWert)));

        // Find maximum value for scaling
        double maxAverage = averages.values().stream()
            .mapToDouble(Double::doubleValue)
            .max()
            .orElse(0.0);

        // Print results
        printHeader();
        averages.forEach((jahr, avg) -> printRow(jahr, avg, maxAverage));
        printFooter();
    }
}