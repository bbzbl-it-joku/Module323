package com.example.m323.funktionen;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

import com.example.m323.Main;
import com.example.m323.utils.TableFormatterUtils;
import com.example.m323.utils.data.DataSet;

/**
 * The FilterNachVerbrauch class provides methods to filter and display data based on
 * consumption thresholds.
 * 
 * @author Joshua Kunz
 * @author Seth Schmutz
 * @version 1.0
 * @since 10.10.2024
 */
public class FilterNachVerbrauch {
    private static final int BAR_WIDTH = 35;
    private static final int[] COLUMN_WIDTHS = {20, 7, 20, 36}; // Gemeinde, Jahr, Verbrauch, Visualisierung
    private static final int TOTAL_WIDTH = 88; // Total width of the table
    
    /**
     * Prints the header for the filtered data display.
     * 
     * @param verbrauch The consumption threshold used for filtering
     */
    private static void printHeader(double verbrauch) {
        System.out.println("\n╔" + "═".repeat(TOTAL_WIDTH - 2) + "╗");
        System.out.printf("║  Gemeinden mit Verbrauch über %s MWh%s║%n",
            TableFormatterUtils.formatNumber(verbrauch),
            " ".repeat(TOTAL_WIDTH - 37 - TableFormatterUtils.formatNumber(verbrauch).length()));
        System.out.println(TableFormatterUtils.createColumnSeparator(COLUMN_WIDTHS, '═', '╦'));
        System.out.println("║  Gemeinde          ║  Jahr ║  Verbrauch (MWh)   ║  Visualisierung                    ║");
        System.out.println(TableFormatterUtils.createColumnSeparator(COLUMN_WIDTHS, '═', '╬'));
    }

    /**
     * Prints the footer with summary statistics.
     * 
     * @param count Number of municipalities found
     * @param avgConsumption Average consumption of filtered municipalities
     */
    private static void printFooter(int count, double avgConsumption) {
        System.out.println(TableFormatterUtils.createColumnSeparator(COLUMN_WIDTHS, '═', '╩'));
        String stats = String.format("  Gefundene Gemeinden: %-6d          Durchschnittsverbrauch: %-15s MWh",
            count, TableFormatterUtils.formatNumber(avgConsumption));
        System.out.println("║" + TableFormatterUtils.centerText(stats, TOTAL_WIDTH - 2) + "║");
        System.out.println("╚" + "═".repeat(TOTAL_WIDTH - 2) + "╝");
    }

    /**
     * Prints a single data row with formatting.
     */
    private static void printRow(DataSet dataSet, double maxValue) {
        System.out.printf("║  %-18s║  %4d ║ %15s    ║ %s ║%n",
            dataSet.getGemeinde(),
            dataSet.getJahr(),
            TableFormatterUtils.formatNumber(dataSet.getWert()),
            TableFormatterUtils.createBar(dataSet.getWert(), maxValue, BAR_WIDTH - 1));
    }

    /**
     * Filters the data using an iterative approach.
     *
     * @param verbrauch The consumption threshold to filter the data.
     * @author Seth Schmutz
     */
    public static void iterativeFunction(double verbrauch) {
        List<DataSet> filteredData = new ArrayList<>();
        for (DataSet dataSet : Main.getDataLoader().getData()) {
            if (dataSet.getWert() > verbrauch) {
                filteredData.add(dataSet);
            }
        }

        // Calculate statistics
        double maxVerbrauch = 0;
        double sumVerbrauch = 0;
        for (DataSet dataSet : filteredData) {
            maxVerbrauch = Math.max(maxVerbrauch, dataSet.getWert());
            sumVerbrauch += dataSet.getWert();
        }
        double avgVerbrauch = filteredData.isEmpty() ? 0 : sumVerbrauch / filteredData.size();

        // Sort data by consumption (descending)
        filteredData.sort((a, b) -> Double.compare(b.getWert(), a.getWert()));

        // Print results
        printHeader(verbrauch);
        for (DataSet dataSet : filteredData) {
            printRow(dataSet, maxVerbrauch);
        }
        printFooter(filteredData.size(), avgVerbrauch);
    }

    /**
     * Filters the data using a functional approach.
     * Uses Java streams to filter, process, and display the data.
     *
     * @param verbrauch The consumption threshold to filter the data.
     * @author Joshua Kunz
     */
    public static void functionalFunction(double verbrauch) {
        List<DataSet> filteredData = Main.getDataLoader().getData().stream()
            .filter(dataSet -> dataSet.getWert() > verbrauch)
            .collect(Collectors.toList());

        double maxVerbrauch = filteredData.stream()
            .mapToDouble(DataSet::getWert)
            .max()
            .orElse(0.0);

        double avgVerbrauch = filteredData.stream()
            .mapToDouble(DataSet::getWert)
            .average()
            .orElse(0.0);

        printHeader(verbrauch);
        filteredData.stream()
            .sorted(Comparator.comparingDouble(DataSet::getWert).reversed())
            .forEach(dataSet -> printRow(dataSet, maxVerbrauch));
        printFooter(filteredData.size(), avgVerbrauch);
    }
}