package com.example.m322.funktionen;

import java.util.ArrayList;
import java.util.List;


import com.example.m322.Main;
import com.example.m322.data.DataSet;

public class DurchschnitUeberAlleJahre {

    public static void durchschnitIterative() {


        List<Integer> uniqueYears = new ArrayList();

        for(DataSet data : Main.getDataLoader().getData()){
            if(!uniqueYears.contains(data.getJahr())){
                uniqueYears.add(data.getJahr());
            }
        }

        for(Integer jahr : uniqueYears){
            double total = 0;
            Integer count = 0;   
            for(DataSet data : Main.getDataLoader().getData()){
                if(jahr == data.getJahr()){
                    total += data.getWert();
                    count++;
                }
            }

            double avg = total / count;

            System.out.println("Durchschnitlicher Verbrauch " + jahr + ": "  + avg);
        }
        
    }
}
