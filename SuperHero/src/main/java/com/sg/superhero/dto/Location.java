package com.sg.superhero.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {

    private int LocationId;
    private String locationName;
    private String description;
    private String address;
    private double longitude;
    private double latitude;

}
