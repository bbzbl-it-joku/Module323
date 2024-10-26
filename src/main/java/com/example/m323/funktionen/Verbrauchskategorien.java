package com.example.m323.funktionen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.example.m323.Main;
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

    /**
     * Categorizes consumption data using an iterative approach.
     * 
     * @author Seth Schmutz
     */
    public static void iterativeFunction() {
        List<Integer> uniqueYears = new ArrayList<>();
        Map<Integer, List<DataSet>> categorizedData = new HashMap<>();

        for (DataSet data : Main.getDataLoader().getData()) {
            int year = data.getJahr();
            if (!uniqueYears.contains(year)) {
                uniqueYears.add(year);
                categorizedData.put(year, new ArrayList<>());
            }
        }

        for (Integer year : uniqueYears) {
            int niedrigCount = 0;
            int mittelCount = 0;
            int hochCount = 0;

            for (DataSet data : Main.getDataLoader().getData()) {
                if (year == data.getJahr()) {
                    double usage = data.getWert();
                    if (usage <= 10000) {
                        niedrigCount++;
                    } else if (usage <= 50000) {
                        mittelCount++;
                    } else {
                        hochCount++;
                    }
                }
            }

            System.out.println("Year " + year + ":");
            System.out.println("Niedrig (0-10,000 MWh): " + niedrigCount + " Gemeinden");
            System.out.println("Mittel (10,001-50,000 MWh): " + mittelCount + " Gemeinden");
            System.out.println("Hoch (50,001+ MWh): " + hochCount + " Gemeinden\n");
        }
    }

    /**
     * Categorizes consumption data using a functional approach.
     * Uses streams to group and count municipalities by consumption category.
     * 
     * @author Joshua Kunz
     */
    public static void functionalFunction() {
        Main.getDataLoader().getData().stream()
            .collect(Collectors.groupingBy(
                DataSet::getJahr,
                Collectors.collectingAndThen(
                    Collectors.groupingBy(
                        data -> {
                            double usage = data.getWert();
                            if (usage <= 10000) return "Niedrig";
                            else if (usage <= 50000) return "Mittel";
                            else return "Hoch";
                        },
                        Collectors.counting()
                    ),
                    categoryMap -> {
                        return Map.of(
                            "Niedrig", categoryMap.getOrDefault("Niedrig", 0L),
                            "Mittel", categoryMap.getOrDefault("Mittel", 0L),
                            "Hoch", categoryMap.getOrDefault("Hoch", 0L)
                        );
                    }
                )))
            .forEach((year, categories) -> {
                System.out.println("Year " + year + ":");
                System.out.println("Niedrig (0-10,000 MWh): " + categories.get("Niedrig") + " Gemeinden");
                System.out.println("Mittel (10,001-50,000 MWh): " + categories.get("Mittel") + " Gemeinden");
                System.out.println("Hoch (50,001+ MWh): " + categories.get("Hoch") + " Gemeinden\n");
            });
    }
}