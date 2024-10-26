package com.example.m323.funktionen;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.example.m323.Main;
import com.example.m323.utils.TableFormatterUtils;
import com.example.m323.utils.data.DataSet;

/**
 * This class calculates and visualizes consumption statistics for a given year.
 * 
 * @author Joshua Kunz
 * @author Seth Schmutz
 * @version 1.0
 * @since 10.10.2024
 */
public class Verbrauchsstatistik {
    private static final int GRAPH_WIDTH = 40;    // Width of the console graph
    private static final int NUM_BINS = 10;       // Number of histogram bins
    private static final int TOTAL_WIDTH = 79;    // Total width of the table
    private static final int[] COLUMN_WIDTHS = {36, 8, 31}; // Verbrauchsbereich, Anzahl, Verteilung

    private static void printHeader(int year) {
        System.out.println("\n╔" + "═".repeat(TOTAL_WIDTH) + "╗");
        String title = String.format("Verbrauchsstatistik für das Jahr %d", year);
        System.out.println("║" + TableFormatterUtils.centerText(title, TOTAL_WIDTH) + "║");
        System.out.println("╠" + "═".repeat(TOTAL_WIDTH) + "╣");
    }

    private static void printNoDataHeader(int year) {
        System.out.println("\n╔" + "═".repeat(TOTAL_WIDTH) + "╗");
        System.out.printf("║  Keine Daten für das Jahr %-4d gefunden%s║%n", 
            year, " ".repeat(TOTAL_WIDTH - 37));
        System.out.println("╚" + "═".repeat(TOTAL_WIDTH) + "╝");
    }

    private static void printStatistics(DoubleSummaryStatistics stats, double stdDev) {
        System.out.println("║  Statistische Kennzahlen:                                                 ║");
        System.out.printf("║    • Anzahl Gemeinden:       %-10d                                     ║%n", 
            stats.getCount());
        System.out.printf("║    • Minimaler Verbrauch:    %-10s MWh                                ║%n", 
            TableFormatterUtils.formatNumber(stats.getMin()));
        System.out.printf("║    • Maximaler Verbrauch:    %-10s MWh                                ║%n", 
            TableFormatterUtils.formatNumber(stats.getMax()));
        System.out.printf("║    • Durchschnittsverbrauch: %-10s MWh                                ║%n", 
            TableFormatterUtils.formatNumber(stats.getAverage()));
        System.out.printf("║    • Standardabweichung:     %-10s MWh                                ║%n", 
            TableFormatterUtils.formatNumber(stdDev));
    }

    private static void printHistogramHeader() {
        System.out.println("║                                                                           ║");
        System.out.println("║  Verteilung der Verbrauchswerte:                                         ║");
        System.out.println("║    Verbrauchsbereich (MWh)   │ Anzahl │ Verteilung                      ║");
        System.out.println("║  ──────────────────────────────┼────────┼───────────────────────────────  ║");
    }

    private static void printHistogramBar(double binStart, double binEnd, long count, int barLength) {
        System.out.printf("║    %8s - %-8s      │   %-4d │ %-28s ║%n",
            TableFormatterUtils.formatNumber(binStart), 
            TableFormatterUtils.formatNumber(binEnd),
            count,
            TableFormatterUtils.createBar(count, count, barLength));
    }

    private static void printFooter() {
        System.out.println("╚" + "═".repeat(TOTAL_WIDTH) + "╝");
    }

    private static double calculateStdDev(List<DataSet> data, double mean) {
        return Math.sqrt(
            data.stream()
                .mapToDouble(d -> {
                    double diff = d.getWert() - mean;
                    return diff * diff;
                })
                .average()
                .orElse(0.0)
        );
    }

    /**
     * Calculates consumption statistics and displays a graph using an iterative approach.
     * 
     * @param year The year to calculate statistics for
     * @author Seth Schmutz
     */
    public static void iterativeFunction(int year) {
        List<DataSet> yearData = new ArrayList<>();
        
        // Collect values for the specified year
        for (DataSet data : Main.getDataLoader().getData()) {
            if (data.getJahr() == year) {
                yearData.add(data);
            }
        }
        
        if (yearData.isEmpty()) {
            printNoDataHeader(year);
            return;
        }

        // Calculate statistics
        DoubleSummaryStatistics stats = yearData.stream()
            .mapToDouble(DataSet::getWert)
            .summaryStatistics();
        double stdDev = calculateStdDev(yearData, stats.getAverage());

        // Create histogram
        double binWidth = (stats.getMax() - stats.getMin()) / NUM_BINS;
        int[] histogram = new int[NUM_BINS];
        for (DataSet data : yearData) {
            int bin = (int) ((data.getWert() - stats.getMin()) / binWidth);
            if (bin == NUM_BINS) bin--;
            histogram[bin]++;
        }

        // Print results
        printHeader(year);
        printStatistics(stats, stdDev);
        printHistogramHeader();

        int maxCount = 0;
        for (int binCount : histogram) {
            maxCount = Math.max(maxCount, binCount);
        }

        for (int i = 0; i < NUM_BINS; i++) {
            double binStart = stats.getMin() + (i * binWidth);
            double binEnd = binStart + binWidth;
            printHistogramBar(binStart, binEnd, histogram[i], GRAPH_WIDTH);
        }

        printFooter();
    }

    /**
     * Calculates consumption statistics and displays a graph using a functional approach.
     * 
     * @param year The year to calculate statistics for
     * @author Joshua Kunz
     */
    public static void functionalFunction(int year) {
        List<DataSet> yearData = Main.getDataLoader().getData().stream()
            .filter(data -> data.getJahr() == year)
            .collect(Collectors.toList());
            
        if (yearData.isEmpty()) {
            printNoDataHeader(year);
            return;
        }

        // Get basic statistics
        DoubleSummaryStatistics stats = yearData.stream()
            .mapToDouble(DataSet::getWert)
            .summaryStatistics();

        // Calculate standard deviation
        double stdDev = calculateStdDev(yearData, stats.getAverage());

        // Create histogram data
        double binWidth = (stats.getMax() - stats.getMin()) / NUM_BINS;
        Map<Integer, Long> histogram = yearData.stream()
            .collect(Collectors.groupingBy(
                data -> {
                    int bin = (int) ((data.getWert() - stats.getMin()) / binWidth);
                    return bin >= NUM_BINS ? NUM_BINS - 1 : bin;
                },
                Collectors.counting()
            ));

        // Print results
        printHeader(year);
        printStatistics(stats, stdDev);
        printHistogramHeader();

        IntStream.range(0, NUM_BINS).forEach(i -> {
            double binStart = stats.getMin() + (i * binWidth);
            double binEnd = binStart + binWidth;
            long binCount = histogram.getOrDefault(i, 0L);
            printHistogramBar(binStart, binEnd, binCount, GRAPH_WIDTH);
        });

        printFooter();
    }
}