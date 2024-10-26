package com.example.m323.funktionen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.example.m323.Main;
import com.example.m323.utils.data.DataSet;

/**
 * Class to find the highest and lowest consumption values per year.
 * 
 * @author Seth Schmutz
 * @version 1.0
 * @since 10.10.2024
 */
public class HoechsterTiefsterVerbrauch {

    /**
     * Finds the highest or lowest consumption for each year using an iterative approach.
     * 
     * @param highest If true, finds highest consumption; if false, finds lowest
     * @author Seth Schmutz
     */
    public static void iterativeFunction(boolean highest) {
        List<Integer> uniqueYears = new ArrayList<>();

        for (DataSet data : Main.getDataLoader().getData()) {
            if (!uniqueYears.contains(data.getJahr())) {
                uniqueYears.add(data.getJahr());
            }
        }

        for (Integer jahr : uniqueYears) {
            String gemeinde = "";
            double verbrauch = 0;
            if(!highest){
                verbrauch = 1000000000;
            }
            for (DataSet data : Main.getDataLoader().getData()) {
                if(jahr == data.getJahr()){
                    if(verbrauch < data.getWert() && highest){
                        verbrauch = data.getWert();
                        gemeinde = data.getGemeinde();
                    } else if( verbrauch > data.getWert() && !highest){
                        verbrauch = data.getWert();
                        gemeinde = data.getGemeinde();
                    }
                }
            }

            if(highest){
                System.out.println("Höchster Verbrauch " + jahr + " In " + gemeinde + ": " + verbrauch);
            }else{
                System.out.println("Tiefster Verbrauch " + jahr + " In " + gemeinde + ": " + verbrauch);
            }
        }
    }

    /**
     * Finds both highest and lowest consumption for each year using a functional approach.
     *  
     * @param highest If true, finds highest consumption; if false, finds lowest
     * @author Joshua Kunz
     */
    public static void functionalFunction(boolean highest) {
        Main.getDataLoader().getData().stream()
            .collect(Collectors.groupingBy(
                DataSet::getJahr,
                Collectors.maxBy(highest ? 
                    Comparator.comparingDouble(DataSet::getWert) :
                    Comparator.comparingDouble(DataSet::getWert).reversed())))
            .forEach((jahr, extremeDataSet) -> extremeDataSet.ifPresent(
                data -> System.out.println(
                    (highest ? "Höchster" : "Tiefster") + 
                    " Verbrauch " + jahr + 
                    " In " + data.getGemeinde() + 
                    ": " + data.getWert())));
    }
}