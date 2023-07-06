package com.autofleet.house;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HouseConfiguration {

    @Bean
    public CarLocationService carLocationService(){
        return new CarLocationServiceImpl();
    }
}
