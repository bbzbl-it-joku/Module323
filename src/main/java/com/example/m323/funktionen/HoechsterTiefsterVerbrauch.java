package com.example.m323.funktionen;

import java.util.ArrayList;
import java.util.List;

import com.example.m323.Main;
import com.example.m323.utils.data.DataSet;
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
