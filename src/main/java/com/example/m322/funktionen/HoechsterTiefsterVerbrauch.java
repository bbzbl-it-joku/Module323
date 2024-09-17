package com.example.m322.funktionen;

import com.example.m322.Main;
import com.example.m322.utils.data.DataSet;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Seth Schmutz
 * @version 1.0
 * @since 10.10.2024
 */
public class HoechsterTiefsterVerbrauch {

    /**
     * This method represents an iterative function.
     * It is currently unimplemented and throws an UnsupportedOperationException.
     * 
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
            double verbrauch = 0;
            String gemeinde = "";
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
            System.out.println("Höchster Verbrauch " + jahr + " In " + gemeinde + ": " + verbrauch);
 
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
