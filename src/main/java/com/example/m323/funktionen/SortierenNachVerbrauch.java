package com.example.m323.funktionen;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.m323.Main;
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
    /**
     * Sorts and displays consumption data using an iterative approach.
     * 
     * @author Seth Schmutz
     */
    public static void iterativeFunction() {
        List<Integer> uniqueYears = new ArrayList<>();
        Map<Integer, List<DataSet>> dataByYear = new HashMap<>();

        for (DataSet data : Main.getDataLoader().getData()) {
            int year = data.getJahr();
            if (!uniqueYears.contains(year)) {
                uniqueYears.add(year);
            }
            dataByYear.computeIfAbsent(year, k -> new ArrayList<>()).add(data);
        }

        for (Integer year : uniqueYears) {
            System.out.println("Year " + year + ":");

            List<DataSet> dataList = dataByYear.get(year);
            dataList.sort((a, b) -> Double.compare(b.getWert(), a.getWert()));

            for (DataSet data : dataList) {
                System.out.println("Gemeinde " + data.getGemeinde() + ": " + data.getWert());
            }
            System.out.println();
        }
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
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    list -> list.stream()
                        .sorted((a, b) -> Double.compare(b.getWert(), a.getWert()))
                        .collect(Collectors.toList()))))
            .entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> {
                System.out.println("Year " + entry.getKey() + ":");
                entry.getValue().forEach(data -> 
                    System.out.println("Gemeinde " + data.getGemeinde() + ": " + data.getWert()));
                System.out.println();
            });
    }
}