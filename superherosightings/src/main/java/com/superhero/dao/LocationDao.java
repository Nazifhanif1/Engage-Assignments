package com.superhero.dao;

import java.util.List;

import com.superhero.dto.Location;

public interface LocationDao {

    Location createLocation(Location location);

    void updateLocation(Location location);

    void deleteLocationById(int id);

    Location getLocationById(int id);

    List<Location> getAllLocations();

}