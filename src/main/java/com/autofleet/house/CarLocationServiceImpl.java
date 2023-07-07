package com.autofleet.house;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CarLocationServiceImpl implements CarLocationService {

    //for the build to send to the server
    public static final String VEHICLES_LOCATION_JSON_PATH = "vehicles-location.json";

    //for local use for testing
    //public static final String VEHICLES_LOCATION_JSON_PATH = "src/main/resources/vehicles-location.json";
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

    @Override
    public JSONObject getAllCarsInPolygon(ArrayList<Location> polygon) {
        //check which cars are inside the polygon
        Map<String,Location> carInsidePolygon = new HashMap<>();

        Map<String, Location> carIdToLocation = getAll();

        for (Map.Entry<String,Location> car : carIdToLocation.entrySet()) {
            Location location = new Location(car.getValue().getLat(), car.getValue().getLng());
            if (isCarInsidePolygon(location, polygon)) {
                carInsidePolygon.put(car.getKey(), car.getValue());
            }
        }

        System.out.println(carInsidePolygon);

        return convertToJSONFile(carInsidePolygon);
    }

    private JSONObject convertToJSONFile(Map<String,Location> carInsidePolygon) {

        JSONObject jsonObject = new JSONObject();
        try {
            for (Map.Entry<String, Location> entry : carInsidePolygon.entrySet()) {
                String key = entry.getKey();
                Location location = entry.getValue();

                JSONObject locationJson = new JSONObject();
                locationJson.put("lat", location.getLat());
                locationJson.put("lng", location.getLng());

                jsonObject.put(key, locationJson);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return jsonObject;
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

                Location location = new Location(latitude, longitude);
                carIdToPosition.put(id, location);
            }
            System.out.println("Finished loading all car locations");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isCarInsidePolygon(Location location, ArrayList<Location> polygon) {

        double minX = polygon.get(0).getLat();
        double maxX = polygon.get(0).getLat();
        double minY = polygon.get(0).getLng();
        double maxY = polygon.get(0).getLng();

        for (Location polygonPoint: polygon) {
            minX = Math.min(polygonPoint.getLat(), minX);
            maxX = Math.max(polygonPoint.getLat(), maxX);
            minY = Math.min(polygonPoint.getLng(), minY);
            maxY = Math.max(polygonPoint.getLng(), maxY);
        }

        //check x & y
        if (location.getLat() < minX || location.getLat() > maxX || location.getLng() < minY || location.getLng() > maxY)
        {
            return false;
        }

        //check crossing
        boolean inside = false;
        for (int i = 0, j = polygon.size() - 1; i < polygon.size(); j = i++)
        {
            if ((polygon.get(i).getLng() > location.getLng()) != (polygon.get(j).getLng() > location.getLng()) &&
                    location.getLat() < ((polygon.get(j).getLat() - polygon.get(i).getLat()) *
                            (location.getLng() - polygon.get(i).getLng()) /
                            (polygon.get(j).getLng() - polygon.get(i).getLng()) + polygon.get(i).getLat()))//end if
            {
                inside = !inside;
            }
        }

        return inside;
    }
}
