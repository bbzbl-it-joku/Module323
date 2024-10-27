package com.example.m323.funktionen;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.m323.Main;
import com.example.m323.utils.TableFormatterUtils;
import com.example.m323.utils.data.DataSet;

/**
 * This class represents the total consumption of the cantons.
 * 
 * @author Joshua Kunz
 * @author Seth Schmutz
 * @version 1.0
 * @since 10.10.2024
 */
public class Gesamtverbrauch {
    private static final int BAR_WIDTH = 31;
    private static final int[] COLUMN_WIDTHS = {21, 25, 33}; // Gemeinde, Gesamtverbrauch, Visualisierung
    private static final int TOTAL_WIDTH = 83; // Total width of the table
    private static final String TITLE = "Gesamtenergieverbrauch";
    
    /**
     * Prints the header for the consumption display.
     */
    private static void printHeader() {
        System.out.println("\n╔" + "═".repeat(TOTAL_WIDTH - 2) + "╗");
        System.out.println("║" + TableFormatterUtils.centerText(TITLE, TOTAL_WIDTH - 2) + "║");
        System.out.println(TableFormatterUtils.createColumnSeparator(COLUMN_WIDTHS, '═', '╦'));
        System.out.println("║  Gemeinde           ║  Gesamtverbrauch (MWh)  ║  Visualisierung                 ║");
        System.out.println(TableFormatterUtils.createColumnSeparator(COLUMN_WIDTHS, '═', '╬'));
    }

    /**
     * Prints the footer with summary statistics.
     * 
     * @param totalConsumption Total consumption across all municipalities
     * @param avgConsumption Average consumption per municipality
     */
    private static void printFooter(BigDecimal totalConsumption, BigDecimal avgConsumption) {
        System.out.println(TableFormatterUtils.createColumnSeparator(COLUMN_WIDTHS, '═', '╩'));
        String stats = String.format("  Total: %s MWh    Durchschnitt: %s MWh",
            TableFormatterUtils.formatNumber(totalConsumption),
            TableFormatterUtils.formatNumber(avgConsumption));
        System.out.println("║" + TableFormatterUtils.centerText(stats, TOTAL_WIDTH - 2) + "║");
        System.out.println("╚" + "═".repeat(TOTAL_WIDTH - 2) + "╝");
    }

    /**
     * Prints a single data row with formatting.
     */
    private static void printRow(String gemeinde, BigDecimal value, BigDecimal maxValue) {
        System.out.printf("║  %-17s  ║  %21s  ║ %s ║%n",
            gemeinde,
            TableFormatterUtils.formatNumber(value),
            TableFormatterUtils.createBar(value, maxValue, BAR_WIDTH));
    }

    /**
     * This method represents an iterative function.
     * 
     * @author Joshua Kunz
     */
    public static void iterativeFunction() {
        Map<String, BigDecimal> verbrauch = new HashMap<>();

        // Calculate total consumption per municipality
        for (DataSet data : Main.getDataLoader().getData()) {
            BigDecimal wert = BigDecimal.valueOf(data.getWert());
            verbrauch.merge(data.getGemeinde(), wert, BigDecimal::add);
        }

        // Sort municipalities by consumption
        Map<String, BigDecimal> sortedVerbrauch = verbrauch.entrySet().stream()
            .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
            .collect(Collectors.toMap(
                Map.Entry::getKey, 
                Map.Entry::getValue, 
                (e1, e2) -> e1, 
                LinkedHashMap::new));

        // Calculate statistics
        BigDecimal maxVerbrauch = sortedVerbrauch.values().stream()
            .max(BigDecimal::compareTo)
            .orElse(BigDecimal.ZERO);
        
        BigDecimal totalVerbrauch = sortedVerbrauch.values().stream()
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal avgVerbrauch = totalVerbrauch.divide(
            BigDecimal.valueOf(sortedVerbrauch.size()), 
            2, 
            RoundingMode.HALF_UP);

        // Print results
        printHeader();
        sortedVerbrauch.forEach((gemeinde, wert) -> 
            printRow(gemeinde, wert, maxVerbrauch));
        printFooter(totalVerbrauch, avgVerbrauch);
    }

    /**
     * Calculates and displays total consumption using a functional approach.
     * Uses streams to group, sum, sort, and format the consumption data.
     * 
     * @author Joshua Kunz
     */
    public static void functionalFunction() {
        Map<String, BigDecimal> verbrauch = Main.getDataLoader().getData().stream()
            .collect(Collectors.groupingBy(
                DataSet::getGemeinde,
                Collectors.mapping(
                    data -> BigDecimal.valueOf(data.getWert()),
                    Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

        BigDecimal maxVerbrauch = verbrauch.values().stream()
            .max(BigDecimal::compareTo)
            .orElse(BigDecimal.ZERO);

        BigDecimal totalVerbrauch = verbrauch.values().stream()
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal avgVerbrauch = totalVerbrauch.divide(
            BigDecimal.valueOf(verbrauch.size()), 
            2, 
            RoundingMode.HALF_UP);

        printHeader();
        verbrauch.entrySet().stream()
            .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
            .forEach(entry -> printRow(entry.getKey(), entry.getValue(), maxVerbrauch));
        printFooter(totalVerbrauch, avgVerbrauch);
    }
}