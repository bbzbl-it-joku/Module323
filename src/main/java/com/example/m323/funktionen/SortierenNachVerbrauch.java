package com.example.m323.funktionen;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.m323.Main;
import com.example.m323.utils.TableFormatterUtils;
import com.example.m323.utils.data.DataSet;

/**
 * This class sorts and displays consumption data by year.
 * 
 * @author Joshua Kunz
 * @author Seth Schmutz
 * @version 1.0
 * @since 10.10.2024
 */
public class SortierenNachVerbrauch {
    private static final int BAR_WIDTH = 35;
    private static final int[] COLUMN_WIDTHS = {22, 19, 37}; // Gemeinde, Verbrauch, Visualisierung
    private static final int TOTAL_WIDTH = 82; // Total width including borders

    /**
     * Prints the header for a specific year's data.
     */
    private static void printYearHeader(int year) {
        System.out.println("\n╔" + "═".repeat(TOTAL_WIDTH - 2) + "╗");
        String title = String.format("Energieverbrauch im Jahr %d", year);
        System.out.println("║" + TableFormatterUtils.centerText(title, TOTAL_WIDTH - 2) + "║");
        System.out.println("║" + "═".repeat(COLUMN_WIDTHS[0]) + "╦" + 
        "═".repeat(COLUMN_WIDTHS[1]) + "╦" +
        "═".repeat(COLUMN_WIDTHS[2]) + "║");
        System.out.println("║  Gemeinde            ║  Verbrauch (MWh)  ║  Visualisierung                     ║");
        System.out.println("║" + "═".repeat(COLUMN_WIDTHS[0]) + "╬" + 
                         "═".repeat(COLUMN_WIDTHS[1]) + "╬" +
                         "═".repeat(COLUMN_WIDTHS[2]) + "║");
    }

    /**
     * Prints the footer with summary statistics for a year.
     */
    private static void printYearFooter(List<DataSet> yearData) {
        double total = yearData.stream().mapToDouble(DataSet::getWert).sum();
        double average = yearData.stream().mapToDouble(DataSet::getWert).average().orElse(0.0);
        
        System.out.println("║" + "═".repeat(COLUMN_WIDTHS[0]) + "╩" + 
                         "═".repeat(COLUMN_WIDTHS[1]) + "╩" +
                         "═".repeat(COLUMN_WIDTHS[2]) + "║");
        String stats = String.format("Total: %s MWh        Durchschnitt: %s MWh",
            TableFormatterUtils.formatNumber(total),
            TableFormatterUtils.formatNumber(average));
        System.out.println("║" + TableFormatterUtils.centerText(stats, TOTAL_WIDTH - 2) + "║");
        System.out.println("╚" + "═".repeat(TOTAL_WIDTH - 2) + "╝");
    }

    /**
     * Prints a single data row with formatting.
     */
    private static void printRow(DataSet data, double maxValue) {
        System.out.printf("║  %-18s  ║  %15s  ║ %s ║%n",
            data.getGemeinde(),
            TableFormatterUtils.formatNumber(data.getWert()),
            TableFormatterUtils.createBar(data.getWert(), maxValue, BAR_WIDTH));
    }

    /**
     * Processes and displays data for a single year.
     */
    private static void processYearData(int year, List<DataSet> yearData) {
        yearData.sort((a, b) -> Double.compare(b.getWert(), a.getWert()));
        double maxValue = yearData.stream()
            .mapToDouble(DataSet::getWert)
            .max()
            .orElse(0.0);

        printYearHeader(year);
        yearData.forEach(data -> printRow(data, maxValue));
        printYearFooter(yearData);
    }

    /**
     * Sorts and displays consumption data using an iterative approach.
     * 
     * @author Seth Schmutz
     */
    public static void iterativeFunction() {
        List<Integer> uniqueYears = new ArrayList<>();
        Map<Integer, List<DataSet>> dataByYear = new HashMap<>();

        // Collect and organize data
        for (DataSet data : Main.getDataLoader().getData()) {
            int year = data.getJahr();
            if (!uniqueYears.contains(year)) {
                uniqueYears.add(year);
            }
            dataByYear.computeIfAbsent(year, k -> new ArrayList<>()).add(data);
        }

        // Process each year in order
        uniqueYears.stream()
            .sorted()
            .forEach(year -> processYearData(year, dataByYear.get(year)));
    }

    /**
     * Sorts and displays consumption data using a functional approach.
     * Uses streams to group, sort, and display the data.
     * 
     * @author Joshua Kunz
     */
    public static void functionalFunction() {
        Main.getDataLoader().getData().stream()
            .collect(Collectors.groupingBy(
                DataSet::getJahr,
                Collectors.toList()))
            .entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> processYearData(entry.getKey(), entry.getValue()));
    }
}