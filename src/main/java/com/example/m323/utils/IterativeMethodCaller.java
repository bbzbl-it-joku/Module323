package com.example.m323.utils;

import java.util.Arrays;

import com.example.m323.funktionen.DurchschnitUeberAlleJahre;
import com.example.m323.funktionen.FilterNachVerbrauch;
import com.example.m323.funktionen.Gesamtverbrauch;
import com.example.m323.funktionen.HoechsterTiefsterVerbrauch;
import com.example.m323.funktionen.SortierenNachVerbrauch;
import com.example.m323.funktionen.Verbrauchskategorien;
import com.example.m323.funktionen.Verbrauchsstatistik;

/**
 * The IterativeMethodCaller class provides static methods to call the
 * corresponding functional methods.
 * 
 * @author Joshua Kunz
 * @version 1.0
 * @since 10.10.2024
 */
public class IterativeMethodCaller {
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

        FilterNachVerbrauch.iterativeFunction(verbrauch);
    }

    /**
     * Handles the durchschnittlicher verbrauch operation.
     *
     * @param args the command line arguments
     */
    public static void handleDurchschnittlicherVerbrauch(String[] args) {
        DurchschnitUeberAlleJahre.iterativeFunction();
    }

    /**
     * Handles the gemeinde min/max operation.
     *
     * @param args the command line arguments
     */
    public static void handleGemeindeMinMax(String[] args) {
        // Default value
        boolean hoechster = true;

        if (Arrays.asList(args).contains("-m")) {
            hoechster = true;
        } else if (Arrays.asList(args).contains("--highest")) {
            hoechster = true;
        } else if (Arrays.asList(args).contains("-l")) {
            hoechster = false;
        } else if (Arrays.asList(args).contains("--lowest")) {
            hoechster = false;
        }
        HoechsterTiefsterVerbrauch.iterativeFunction(hoechster);
    }

    /**
     * Handles the verbrauchskategorien operation.
     *
     * @param args the command line arguments
     */
    public static void handleVerbrauchskategorien(String[] args) {

        Verbrauchskategorien.iterativeFunction();
    }

    /**
     * Handles the gesamtverbrauch operation.
     *
     * @param args the command line arguments
     */
    public static void handleGesamtverbrauch(String[] args) {
        Gesamtverbrauch.iterativeFunction();
    }

    /**
     * Handles the sortieren nach verbrauch operation.
     *
     * @param args the command line arguments
     */
    public static void handleSortierenNachVerbrauch(String[] args) {
        SortierenNachVerbrauch.iterativeFunction();
    }

    /**
     * Handles the verbrauchsstatistik operation.
     *
     * @param args the command line arguments
     */
    public static void handleVerbrauchsstatistik(String[] args) {
        Verbrauchsstatistik.iterativeFunction();
    }
}
