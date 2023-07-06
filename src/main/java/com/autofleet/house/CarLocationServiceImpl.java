package com.autofleet.house;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CarLocationServiceImpl implements CarLocationService {

    public static final String VEHICLES_LOCATION_JSON_PATH = "src/main/resources/vehicles-location.json";
    private final Map<String, Location> carIdToPosition = new HashMap<>();

    public CarLocationServiceImpl() {
        readJson();
    }

    @Override
    public Map<String, Location> getAll() {
        return carIdToPosition;
    }

    @Override
    public Location getPosition(String carId) {
        return carIdToPosition.get(carId);
    }

    @Override
    public Map<String,Location> getPositionsByCarIds(Collection<String> carIds){
        return carIdToPosition.entrySet().stream()
                .filter(e->carIds.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    private void readJson() {
        JSONParser parser = new JSONParser();
        try {

            FileReader fileReader = new FileReader(VEHICLES_LOCATION_JSON_PATH);
            Object obj = parser.parse(fileReader);
            JSONArray jsonArr = (JSONArray) obj;

            for (Object item : jsonArr) {

                JSONObject jo = (JSONObject) item;
                String id = (String) jo.get("id");

                JSONObject locationJson = (JSONObject) jo.get("location");
                double latitude = (double) locationJson.get("lat");
                double longitude = (double) locationJson.get("lng");
                long bearing = (long) locationJson.get("bearing");

                Location location = new Location(latitude, longitude, bearing);
                carIdToPosition.put(id, location);
            }
            System.out.println("Finished loading all car locations");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
