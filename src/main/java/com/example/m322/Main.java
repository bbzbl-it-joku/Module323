package com.example.m322;

import com.example.m322.data.DataLoader;
import com.example.m322.funktionen.FilterNachVerbrauch;

public class Main {
    private static DataLoader dataLoader;

    public static void main(String[] args) {
        dataLoader = new DataLoader();

        FilterNachVerbrauch.filterIterative(100000);
    }

    public static DataLoader getDataLoader() {
        return dataLoader;
    }
}