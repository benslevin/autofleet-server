package com.autofleet.house;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public interface CarLocationService {

    Map<String,Location> getAll();

    Location getPosition(String carId);

    Map<String, Location> getPositionsByCarIds(Collection<String> carIds);

    Map<String, Location> getAllCarsInPolygon(ArrayList<Location> polygon);
}
