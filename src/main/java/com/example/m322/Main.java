package com.example.m322;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        // Get the InputStream of the JSON file
        InputStream jsonInputStream = Main.class.getResourceAsStream("/10190.json");

        // Initialize Jackson's ObjectMapper
        ObjectMapper mapper = new ObjectMapper();

        // Parse the JSON to a List of Maps
        List<Map<String, Object>> data = mapper.readValue(jsonInputStream,
                new TypeReference<List<Map<String, Object>>>() {
                });

        // Now you can access the data
        for (Map<String, Object> item : data) {
            System.out.println(item.get("gemeinde"));
        }
    }
}