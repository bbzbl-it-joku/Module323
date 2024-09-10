package com.example.m322;

import com.example.m322.data.DataLoader;
import com.example.m322.utils.ArgsHandler;

/**
 * The main class of the application.
 * 
 * @author Joshua Kunz
 * @author Seth Schmutz
 * @version 1.0
 * @since 10.10.2024
 */
public class Main {
    private static DataLoader dataLoader = new DataLoader();

    public static void main(String[] args) {
        ArgsHandler.handleArgs(args);
    }

    /**
     * Returns the data loader instance.
     *
     * @return The data loader instance.
     */
    public static DataLoader getDataLoader() {
        return dataLoader;
    }
}