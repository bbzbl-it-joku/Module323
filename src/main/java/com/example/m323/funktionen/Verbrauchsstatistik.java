package com.example.m323.funktionen;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.ArrayList;
import java.text.DecimalFormat;

import com.example.m323.Main;
import com.example.m323.utils.data.DataSet;

/**
 * This class calculates consumption statistics for a given year.
 * 
 * @author Joshua Kunz
 * @author Seth Schmutz
 * @version 1.0
 * @since 10.10.2024
 */
public class Verbrauchsstatistik {
    /**
     * Calculates consumption statistics using an iterative approach.
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

        // Format and print results
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("Statistics for year " + year + ":");
        System.out.println("Number of municipalities: " + count);
        System.out.println("Minimum consumption: " + df.format(min));
        System.out.println("Maximum consumption: " + df.format(max));
        System.out.println("Average consumption: " + df.format(mean));
        System.out.println("Standard deviation: " + df.format(stdDev));
    }

    /**
     * Calculates consumption statistics using a functional approach.
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

        // Print results
        System.out.println("Statistics for year " + year + ":");
        System.out.println("Number of municipalities: " + stats.getCount());
        System.out.println("Minimum consumption: " + df.format(stats.getMin()));
        System.out.println("Maximum consumption: " + df.format(stats.getMax()));
        System.out.println("Average consumption: " + df.format(stats.getAverage()));
        System.out.println("Standard deviation: " + df.format(stdDev));
    }
}