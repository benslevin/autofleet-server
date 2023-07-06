package com.autofleet.house;

import lombok.Data;

@Data
public class Location {
    private final double lat;
    private final double lng;
    private final long bearing;
}
