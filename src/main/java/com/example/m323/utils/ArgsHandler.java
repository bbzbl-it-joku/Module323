package com.example.m323.utils;

import java.util.Arrays;

/**
 * The ArgsHandler class is responsible for handling command line arguments and
 * invoking the appropriate methods based on the provided arguments.
 * It provides a static method, handleArgs, which takes an array of strings as
 * input and performs the necessary actions based on the arguments.
 * The class also contains private helper methods for printing help messages and
 * delegating the handling of arguments to specific method callers.
 * 
 * @author Joshua Kunz
 * @version 1.0
 * @since 10.10.2024
 */
public class ArgsHandler {

    /**
     * Handles the command line arguments passed to the program.
     *
     * @param args The command line arguments.
     */
    public static void handleArgs(String[] args) {
        if (args.length < 2) {
            printHelp(0);
        }

        if (Arrays.asList(args).contains("-h") || Arrays.asList(args).contains("--help")) {
            printHelp(Integer.parseInt(args[1]));
        }

        switch (args[0]) {
            case "i":
            case "imperative":
                handleImperative(args);
                break;
            case "f":
            case "functional":
                handleFunctional(args);
                break;
            default:
                printHelp(0);
        }
    }

    /**
     * Prints the help message for the command-line arguments.
     *
     * @param method the method number representing the desired operation
     */
    private static void printHelp(int method) {
        System.out.println("\nUsage: java -jar <jarfile> <process> <method> [options] \n");
        System.out.println("Process:");
        System.out.println("  i, imperative\t\tProcess data imperatively");
        System.out.println("  f, functional\t\tProcess data functionally");
        System.out.println("Method:");
        System.out.println("  1\t\t\tFilter nach Verbrauch");
        System.out.println("  2\t\t\tBerechnen des durchschnittlichen Elektrizitätsverbrauchs aller Gemeinden");
        System.out.println("  3\t\t\tFinden der Gemeinde mit dem höchsten und niedrigsten Verbrauch");
        System.out.println("  4\t\t\tGruppieren der Gemeinden nach Verbrauchskategorien (z.B. niedrig, mittel, hoch)");
        System.out.println("  5\t\t\tBerechnen des Gesamtverbrauchs des Kantons über alle Jahre");
        System.out.println("  6\t\t\tSortieren der Gemeinden nach Verbrauch (absteigend)");
        System.out.println(
                "  7\t\t\tErstellen einer Verbrauchsstatistik (z.B. Anzahl Gemeinden in verschiedenen Verbrauchsbereichen)");
        System.out.println("Options:");
        System.out.println("  -h, --help\t\tPrint this help message");

        System.out.println("\nOptions for method nr. 1:");
        System.out.println("  -v, --verbrauch\tFilter data by consumption");

        System.out.println("\nOptions for method nr. 3:");
        System.out.println("  -m, --highest\t\tFind the community with the highest consumption");

        System.out.println("\nOptions for method nr. 6:");
        System.out.println("  -a, --ascending\tSort communities by consumption (ascending)");

        System.out.println("\nOptions for method nr. 7:");
        System.out.println("  -y, --year\t\tCreate consumption statistics for a specific year");

        System.exit(0);
    }

    /**
     * Handles the imperative mode based on the command line arguments.
     *
     * @param args The command line arguments.
     */
    private static void handleImperative(String[] args) {
        switch (args[1]) {
            case "1":
                IterativeMethodCaller.handleFilterNachVerbrauch(args);
                break;
            case "2":
                IterativeMethodCaller.handleDurchschnittlicherVerbrauch(args);
                break;
            case "3":
                IterativeMethodCaller.handleGemeindeMinMax(args);
                break;
            case "4":
                IterativeMethodCaller.handleVerbrauchskategorien(args);
                break;
            case "5":
                IterativeMethodCaller.handleGesamtverbrauch(args);
                break;
            case "6":
                IterativeMethodCaller.handleSortierenNachVerbrauch(args);
                break;
            case "7":
                IterativeMethodCaller.handleVerbrauchsstatistik(args);
                break;
            default:
                printHelp(0);
        }
    }

    /**
     * Handles the functional operations based on the command line arguments.
     *
     * @param args The command line arguments.
     */
    private static void handleFunctional(String[] args) {
        switch (args[1]) {
            case "1":
                FunctionalMethodCaller.handleFilterNachVerbrauch(args);
                break;
            case "2":
                FunctionalMethodCaller.handleDurchschnittlicherVerbrauch(args);
                break;
            case "3":
                FunctionalMethodCaller.handleGemeindeMinMax(args);
                break;
            case "4":
                FunctionalMethodCaller.handleVerbrauchskategorien(args);
                break;
            case "5":
                FunctionalMethodCaller.handleGesamtverbrauch(args);
                break;
            case "6":
                FunctionalMethodCaller.handleSortierenNachVerbrauch(args);
                break;
            case "7":
                FunctionalMethodCaller.handleVerbrauchsstatistik(args);
                break;
            default:
                printHelp(0);
        }
    }
}