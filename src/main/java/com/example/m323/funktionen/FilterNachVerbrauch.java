package com.example.m323.funktionen;

import java.util.ArrayList;
import java.util.List;

import com.example.m323.Main;
import com.example.m323.utils.data.DataSet;

/**
 * The FilterNachVerbrauch class provides methods to filter data based on
 * consumption.
 * 
 * @author Joshua Kunz
 * @author Seth Schmutz
 * @version 1.0
 * @since 10.10.2024
 */
public class FilterNachVerbrauch {

    /**
     * Filters the data using an iterative approach.
     *
     * @param verbrauch The consumption threshold to filter the data.
     * 
     * @author Seth Schmutz
     */
    public static void iterativeFunction(double verbrauch) {
        List<DataSet> filteredData = new ArrayList<>();
        for (DataSet dataSet : Main.getDataLoader().getData()) {
            if (dataSet.getWert() > verbrauch) {
                filteredData.add(dataSet);
            }
        }

        // Print the filtered data
        for (DataSet dataSet : filteredData) {
            System.out.println(dataSet.getGemeinde() + " " + dataSet.getJahr() + ":    \t" + dataSet.getWert());
        }
    }

    /**
     * Filters the data using a functional approach.
     * Uses Java streams to filter and process the data.
     *
     * @param verbrauch The consumption threshold to filter the data.
     * @author Joshua Kunz
     */
    public static void functionalFunction(double verbrauch) {
        Main.getDataLoader().getData().stream()
            .filter(dataSet -> dataSet.getWert() > verbrauch)
            .forEach(dataSet -> System.out.println(
                dataSet.getGemeinde() + " " + 
                dataSet.getJahr() + ":    \t" + 
                dataSet.getWert()));
    }
}