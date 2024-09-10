package com.example.m322.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.m322.Main;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataLoader {
    private List<DataSet> data;

    public DataLoader() {
        loadData();
    }

    private void loadData() {
        this.data = new ArrayList<>();

        // Get the InputStream of the JSON file
        InputStream jsonInputStream = Main.class.getResourceAsStream("/10190.json");

        // Initialize Jackson's ObjectMapper
        ObjectMapper mapper = new ObjectMapper();

        // Parse the JSON to a List of Maps
        List<Map<String, Object>> data = null;
        try {
            data = mapper.readValue(jsonInputStream,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Now you can access the data
        for (Map<String, Object> item : data) {
            this.data.add(new DataSet(item.get("gemeinde"), item.get("bfs_nummer"), item.get("jahr"),
                    item.get("indikator"), item.get("wert")));
        }
    }

    public List<DataSet> getData() {
        return this.data;
    }
}