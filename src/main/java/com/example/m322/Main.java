package com.example.m322;

import com.example.m322.data.DataLoader;

public class Main {
    private static DataLoader dataLoader;

    public static void main(String[] args) {
        dataLoader = new DataLoader();

        printGemeinden();
    }

    public static DataLoader getDataLoader() {
        return dataLoader;
    }

    private static void printGemeinden() {
        dataLoader.getData().stream().map(dataSet -> dataSet.getGemeinde()).distinct().forEach(System.out::println);
    }
}