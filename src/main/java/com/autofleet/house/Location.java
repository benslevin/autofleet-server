package com.autofleet.house;

import lombok.Data;

import java.io.Serializable;

@Data
public class Location implements Serializable {
    private final double lat;
    private final double lng;
}
