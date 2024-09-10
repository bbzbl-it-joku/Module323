package com.example.m322;

import com.example.m322.data.DataLoader;
import com.example.m322.funktionen.FilterNachVerbrauch;
import com.example.m322.funktionen.DurchschnitUeberAlleJahre;

public class Main {
    private static DataLoader dataLoader;

    public static void main(String[] args) {
        dataLoader = new DataLoader();

        FilterNachVerbrauch.filterIterative(100000);
        DurchschnitUeberAlleJahre.durchschnitIterative();
    }

    public static DataLoader getDataLoader() {
        return dataLoader;
    }
}