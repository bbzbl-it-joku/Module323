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
public class Verbrauchskategorien {

    /**
     * This method represents an iterative function.
     * 
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
