package com.example.m323.funktionen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.m323.Main;
import com.example.m323.utils.TableFormatterUtils;
import com.example.m323.utils.data.DataSet;

/**
 * Class to find the highest and lowest consumption values per year.
 * 
 * @author Seth Schmutz
 * @version 1.0
 * @since 10.10.2024
 */
public class HoechsterTiefsterVerbrauch {
    private static final int BAR_WIDTH = 45;  // Slightly reduced for better fit
    private static final int[] COLUMN_WIDTHS = {8, 22, 18, 32}; // Jahr, Gemeinde, Verbrauch, Visualisierung
    private static final int TOTAL_WIDTH = 84; // Total width including borders

    /**
     * Prints the header for the results.
     * 
     * @param highest If true, shows header for highest values; if false, for lowest
     */
    private static void printHeader(boolean highest) {
        String title = String.format("%s Energieverbrauch nach Jahr", highest ? "Höchster" : "Tiefster");
        
        System.out.println("\n╔" + "═".repeat(TOTAL_WIDTH - 2) + "╗");
        System.out.println("║" + TableFormatterUtils.centerText(title, TOTAL_WIDTH - 2) + "║");
        System.out.println("╠" + "═".repeat(TOTAL_WIDTH - 2) + "╣");
        System.out.println("║  Jahr  │  Gemeinde                │  Verbrauch (MWh)  │  Visualisierung        ║");
        System.out.println("╟" + "─".repeat(COLUMN_WIDTHS[0]) + "┼" + 
                         "─".repeat(COLUMN_WIDTHS[1]) + "┼" +
                         "─".repeat(COLUMN_WIDTHS[2]) + "┼" +
                         "─".repeat(COLUMN_WIDTHS[3]) + "╢");
    }

    /**
     * Prints the footer for the results.
     */
    private static void printFooter() {
        System.out.println("╚" + "═".repeat(TOTAL_WIDTH - 2) + "╝");
    }

    /**
     * Prints a single data row with formatting.
     */
    private static void printRow(DataSet data, double maxValue) {
        System.out.printf("║  %4d  │  %-20s  │  %14s  │  %s║%n",
            data.getJahr(),
            data.getGemeinde(),
            TableFormatterUtils.formatNumber(data.getWert()),
            TableFormatterUtils.createBar(data.getWert(), maxValue, BAR_WIDTH));
    }

    /**
     * Finds the highest or lowest consumption for each year using an iterative approach.
     * 
     * @param highest If true, finds highest consumption; if false, finds lowest
     * @author Seth Schmutz
     */
    public static void iterativeFunction(boolean highest) {
        List<Integer> uniqueYears = new ArrayList<>();
        List<DataSet> extremeValues = new ArrayList<>();

        // Collect unique years and find extreme values
        for (DataSet data : Main.getDataLoader().getData()) {
            if (!uniqueYears.contains(data.getJahr())) {
                uniqueYears.add(data.getJahr());
            }
        }

        for (Integer jahr : uniqueYears) {
            DataSet extremeDataSet = Main.getDataLoader().getData().stream()
                .filter(data -> data.getJahr() == jahr)
                .min(highest ? 
                    Comparator.comparingDouble(DataSet::getWert).reversed() :
                    Comparator.comparingDouble(DataSet::getWert))
                .orElse(null);
            
            if (extremeDataSet != null) {
                extremeValues.add(extremeDataSet);
            }
        }

        // Find maximum value for scaling the bars
        double maxValue = extremeValues.stream()
            .mapToDouble(DataSet::getWert)
            .max()
            .orElse(0.0);

        // Print results
        printHeader(highest);
        extremeValues.stream()
            .sorted(Comparator.comparingInt(DataSet::getJahr))
            .forEach(data -> printRow(data, maxValue));
        printFooter();
    }

    /**
     * Finds the highest or lowest consumption for each year using a functional approach.
     * 
     * @param highest If true, finds highest consumption; if false, finds lowest
     * @author Joshua Kunz
     */
    public static void functionalFunction(boolean highest) {
        // Collect all extreme values first
        List<DataSet> extremeValues = Main.getDataLoader().getData().stream()
            .collect(Collectors.groupingBy(
                DataSet::getJahr,
                Collectors.maxBy(highest ? 
                    Comparator.comparingDouble(DataSet::getWert) :
                    Comparator.comparingDouble(DataSet::getWert).reversed())))
            .values()
            .stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());

        // Find maximum value for scaling the bars
        double maxValue = extremeValues.stream()
            .mapToDouble(DataSet::getWert)
            .max()
            .orElse(0.0);

        // Print results
        printHeader(highest);
        extremeValues.stream()
            .sorted(Comparator.comparingInt(DataSet::getJahr))
            .forEach(data -> printRow(data, maxValue));
        printFooter();
    }
}