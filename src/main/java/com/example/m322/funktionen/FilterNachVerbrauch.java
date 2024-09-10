package com.example.m322.funktionen;

import java.util.ArrayList;
import java.util.List;

import com.example.m322.Main;
import com.example.m322.data.DataSet;

public class FilterNachVerbrauch {
    public static void filterIterative(double verbrauch) {
        List<DataSet> filteredData = new ArrayList<>();
        for (DataSet dataSet : Main.getDataLoader().getData()) {
            if (dataSet.getWert() > verbrauch) {
                filteredData.add(dataSet);
            }
        }

        // Print the filtered data
        for (DataSet dataSet : filteredData) {
            System.out.println(dataSet.getGemeinde() + ": \t" + dataSet.getWert());
        }
    }
}
