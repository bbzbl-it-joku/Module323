package com.example.m323.funktionen;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.example.m323.Main;
import com.example.m323.utils.data.DataSet;

/**
 * This class represents an EXAMPLE and contains two methods: iterativeFunction
 * and functionalFunction.
 * 
 * @author Joshua Kunz
 * @author Seth Schmutz
 * @version 1.0
 * @since 10.10.2024
 */
public class SortierenNachVerbrauch {
    /**
     * This method represents an iterative function.
     * It is currently unimplemented and throws an UnsupportedOperationException.
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
     * This method represents a functional function.
     * It is currently unimplemented and throws an UnsupportedOperationException.
     * 
     * @author Joshua Kunz
     */
    public void functionalFunction() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'functionalFunction'");
    }
}