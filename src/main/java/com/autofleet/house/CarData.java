package com.autofleet.house;

import lombok.Data;

@Data
public class CarData {
    private final String id;
    private final String state;
    private final String routeCommitId;
    private final int seats;
    private final Long distance;
    private final Location location;
}
