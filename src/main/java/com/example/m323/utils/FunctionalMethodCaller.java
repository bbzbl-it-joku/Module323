package com.example.m323.utils;

import java.util.Arrays;

import com.example.m323.funktionen.FilterNachVerbrauch;

/**
 * The FunctionalMethodCaller class provides static methods to call the
 * corresponding functional methods.
 * 
 * @author Joshua Kunz
 * @version 1.0
 * @since 10.10.2024
 */
public class FunctionalMethodCaller {
    /**
     * Handles the filter nach verbrauch operation.
     *
     * @param args the command line arguments
     */
    public static void handleFilterNachVerbrauch(String[] args) {
        // Default value
        double verbrauch = 100000;

        if (Arrays.asList(args).contains("-v")) {
            verbrauch = Arrays.asList(args).indexOf("-v") + 1 < args.length
                    ? Double.parseDouble(args[Arrays.asList(args).indexOf("-v") + 1])
                    : 100000;
        } else if (Arrays.asList(args).contains("--verbrauch")) {
            verbrauch = Arrays.asList(args).indexOf("--verbrauch") + 1 < args.length
                    ? Double.parseDouble(args[Arrays.asList(args).indexOf("--verbrauch") + 1])
                    : 100000;
        }

        FilterNachVerbrauch.functionalFunction(verbrauch);
    }

    /**
     * Handles the durchschnittlicher verbrauch operation.
     *
     * @param args the command line arguments
     */
    public static void handleDurchschnittlicherVerbrauch(String[] args) {
        // add method body here
    }

    /**
     * Handles the gemeinde min/max operation.
     *
     * @param args the command line arguments
     */
    public static void handleGemeindeMinMax(String[] args) {
        // add method body here
    }

    /**
     * Handles the verbrauchskategorien operation.
     *
     * @param args the command line arguments
     */
    public static void handleVerbrauchskategorien(String[] args) {
        // add method body here
    }

    /**
     * Handles the gesamtverbrauch operation.
     *
     * @param args the command line arguments
     */
    public static void handleGesamtverbrauch(String[] args) {
        // add method body here
    }

    /**
     * Handles the sortieren nach verbrauch operation.
     *
     * @param args the command line arguments
     */
    public static void handleSortierenNachVerbrauch(String[] args) {
        // add method body here
    }

    /**
     * Handles the verbrauchsstatistik operation.
     *
     * @param args the command line arguments
     */
    public static void handleVerbrauchsstatistik(String[] args) {
        // add method body here
    }
}
