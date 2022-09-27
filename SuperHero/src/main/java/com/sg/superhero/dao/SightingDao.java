package com.sg.superhero.dao;

import java.util.List;

import com.sg.superhero.dto.Sighting;

public interface SightingDao {

    Sighting createSighting(Sighting sighting);

    void updateSighting(Sighting sighting);

    void deleteSightingById(int id);

    Sighting getSightingById(int id);

    List<Sighting> getAllSightings();

}