package com.example.m323.funktionen;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.m323.Main;
import com.example.m323.utils.data.DataSet;

/**
 * This class represents the total consumption of the cantons.
 * 
 * @author Joshua Kunz
 * @author Seth Schmutz
 * @version 1.0
 * @since 10.10.2024
 */
public class Gesamtverbrauch {

    /**
     * This method represents an iterative function.
     * 
     * @author Joshua Kunz
     */
    public static void iterativeFunction() {
        Map<String, BigDecimal> verbrauch = new HashMap<>();
        DecimalFormat df = new DecimalFormat("#.##");

        for (DataSet data : Main.getDataLoader().getData()) {
            BigDecimal wert = BigDecimal.valueOf(data.getWert());
            if (verbrauch.containsKey(data.getGemeinde())) {
                verbrauch.put(data.getGemeinde(), verbrauch.get(data.getGemeinde()).add(wert));
            } else {
                verbrauch.put(data.getGemeinde(), wert);
            }
        }

        Map<String, BigDecimal> sortedVerbrauch = verbrauch.entrySet().stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        for (String gemeinde : sortedVerbrauch.keySet()) {
            System.out.printf("Gesamtverbrauch %-30s: %s%n", gemeinde, df.format(sortedVerbrauch.get(gemeinde)));
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
