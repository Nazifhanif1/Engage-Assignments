package com.sg.superhero.dao;

import java.util.List;

import com.sg.superhero.dto.Location;

public interface LocationDao {

    Location createLocation(Location location);

    void updateLocation(Location location);

    void deleteLocationById(int id);

    Location getLocationById(int id);

    List<Location> getAllLocations();

}