package com.autofleet.house;


import jakarta.annotation.Resource;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/car")
@CrossOrigin(origins = "*")
public class CarController {

    @Resource
    private CarLocationService carLocationService;

    @GetMapping("/")
    @CrossOrigin(origins = "*")
    public Map<String, Location> getAllCarLocations() {
        return carLocationService.getAll();
    }

    @PostMapping("/polygon")
    @CrossOrigin(origins = "*")
    public JSONObject getLocationOfCarInPolygon(@RequestBody List<Location> polygon){
        ArrayList<Location> arrPolygon = new ArrayList<>(polygon);
        return carLocationService.getAllCarsInPolygon(arrPolygon);
    }
}
