package com.example.m323.funktionen;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.example.m323.Main;
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
    private static final int GRAPH_WIDTH = 50; // Width of the console graph
    private static final int NUM_BINS = 10;    // Number of histogram bins

    /**
     * Calculates consumption statistics and displays a graph using an iterative approach.
     * 
     * @param year The year to calculate statistics for
     * @author Seth Schmutz
     */
    public static void iterativeFunction(int year) {
        List<Double> values = new ArrayList<>();
        double sum = 0;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        int count = 0;
        
        // Collect values for the specified year
        for (DataSet data : Main.getDataLoader().getData()) {
            if (data.getJahr() == year) {
                double value = data.getWert();
                values.add(value);
                sum += value;
                min = Math.min(min, value);
                max = Math.max(max, value);
                count++;
            }
        }
        
        if (count == 0) {
            System.out.println("No data found for year " + year);
            return;
        }

        // Calculate mean
        double mean = sum / count;

        // Calculate variance and standard deviation
        double sumSquaredDiff = 0;
        for (double value : values) {
            double diff = value - mean;
            sumSquaredDiff += diff * diff;
        }
        double variance = sumSquaredDiff / count;
        double stdDev = Math.sqrt(variance);

        // Create histogram data
        int[] histogram = new int[NUM_BINS];
        double binWidth = (max - min) / NUM_BINS;
        
        for (double value : values) {
            int bin = (int) ((value - min) / binWidth);
            if (bin == NUM_BINS) bin--; // Handle edge case for maximum value
            histogram[bin]++;
        }

        // Format and print results
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("Statistics for year " + year + ":");
        System.out.println("Number of municipalities: " + count);
        System.out.println("Minimum consumption: " + df.format(min));
        System.out.println("Maximum consumption: " + df.format(max));
        System.out.println("Average consumption: " + df.format(mean));
        System.out.println("Standard deviation: " + df.format(stdDev));
        
        // Print histogram
        System.out.println("\nDistribution of consumption values:");
        int maxCount = 0;
        for (int binCount : histogram) {
            maxCount = Math.max(maxCount, binCount);
        }

        for (int i = 0; i < NUM_BINS; i++) {
            double binStart = min + (i * binWidth);
            double binEnd = binStart + binWidth;
            int barLength = (int) ((histogram[i] * GRAPH_WIDTH) / maxCount);
            System.out.printf("%8.0f - %-8.0f |%-" + GRAPH_WIDTH + "s| %d%n", 
                binStart, binEnd, 
                "#".repeat(barLength), 
                histogram[i]);
        }
    }

    /**
     * Calculates consumption statistics and displays a graph using a functional approach.
     * 
     * @param year The year to calculate statistics for
     * @author Joshua Kunz
     */
    public static void functionalFunction(int year) {
        DecimalFormat df = new DecimalFormat("#.##");
        
        DoubleSummaryStatistics stats = Main.getDataLoader().getData().stream()
            .filter(data -> data.getJahr() == year)
            .mapToDouble(DataSet::getWert)
            .summaryStatistics();
            
        if (stats.getCount() == 0) {
            System.out.println("No data found for year " + year);
            return;
        }

        // Calculate standard deviation
        double stdDev = Math.sqrt(
            Main.getDataLoader().getData().stream()
                .filter(data -> data.getJahr() == year)
                .mapToDouble(data -> {
                    double diff = data.getWert() - stats.getAverage();
                    return diff * diff;
                })
                .average()
                .orElse(0.0)
        );

        // Create histogram data using streams
        double binWidth = (stats.getMax() - stats.getMin()) / NUM_BINS;
        Map<Integer, Long> histogram = Main.getDataLoader().getData().stream()
            .filter(data -> data.getJahr() == year)
            .collect(Collectors.groupingBy(
                data -> {
                    int bin = (int) ((data.getWert() - stats.getMin()) / binWidth);
                    return bin >= NUM_BINS ? NUM_BINS - 1 : bin;
                },
                Collectors.counting()
            ));

        // Print results
        System.out.println("Statistics for year " + year + ":");
        System.out.println("Number of municipalities: " + stats.getCount());
        System.out.println("Minimum consumption: " + df.format(stats.getMin()));
        System.out.println("Maximum consumption: " + df.format(stats.getMax()));
        System.out.println("Average consumption: " + df.format(stats.getAverage()));
        System.out.println("Standard deviation: " + df.format(stdDev));
        
        // Print histogram using streams
        System.out.println("\nDistribution of consumption values:");
        long maxCount = histogram.values().stream().mapToLong(Long::valueOf).max().orElse(0);
        
        IntStream.range(0, NUM_BINS).forEach(i -> {
            double binStart = stats.getMin() + (i * binWidth);
            double binEnd = binStart + binWidth;
            long binCount = histogram.getOrDefault(i, 0L);
            int barLength = (int) ((binCount * GRAPH_WIDTH) / maxCount);
            
            System.out.printf("%8.0f - %-8.0f |%-" + GRAPH_WIDTH + "s| %d%n",
                binStart, binEnd,
                "#".repeat(barLength),
                binCount);
        });
    }
}