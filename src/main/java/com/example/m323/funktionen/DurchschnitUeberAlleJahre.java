package com.example.m323.funktionen;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.m323.Main;
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

    /**
     * Calculates the average consumption using an iterative approach.
     * 
     * @author Seth Schmutz
     */
    public static void iterativeFunction() {
        List<Integer> uniqueYears = new ArrayList<>();

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

            double avg = total / count;

            System.out.println("Durchschnitlicher Verbrauch " + jahr + ": " + avg);
        }
    }

    /**
     * Calculates the average consumption using a functional approach.
     * Uses Java streams to group data by year and calculate averages.
     * 
     * @author Joshua Kunz
     */
    public static void functionalFunction() {
        Main.getDataLoader().getData().stream()
            .collect(Collectors.groupingBy(
                DataSet::getJahr,
                Collectors.averagingDouble(DataSet::getWert)))
            .forEach((jahr, durchschnitt) -> 
                System.out.println("Durchschnitlicher Verbrauch " + jahr + ": " + durchschnitt));
    }
}