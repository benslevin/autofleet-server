package com.autofleet.house;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public interface CarLocationService {

    Map<String,Location> getAll();

    Location getPosition(String carId);

    Map<String, Location> getPositionsByCarIds(Collection<String> carIds);

    JSONObject getAllCarsInPolygon(ArrayList<Location> polygon);
}
