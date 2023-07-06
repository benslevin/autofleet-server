package com.autofleet.house;


import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/car")
public class CarController {

    @Resource
    private CarLocationService carLocationService;

    @GetMapping("/")
    public Map<String, Location> getAllCarLocations() {
        return carLocationService.getAll();
    }

}
