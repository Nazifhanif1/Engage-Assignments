package com.superhero.dao;

import com.superhero.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SightingDaoDBImpl implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting getSightingById(int id) {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE sightingId = ?";
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID,
                    new SightingMapper(), id);
            sighting.setHero(getHeroForSighting(sighting));
            sighting.setLoc(getLocationForSighting(sighting));
            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Superhero getHeroForSighting(Sighting sighting) {
        final String SELECT_HERO_FOR_SIGHTING = "SELECT s.* FROM superhero s "
                + "JOIN sighting si ON s.superheroId = si.superId WHERE si.sightingId = ?";

        Superhero superhero = jdbc.queryForObject(SELECT_HERO_FOR_SIGHTING, new SuperheroDaoDBImpl.HeroMapper(),
                sighting.getSightingId());
        return superhero;
    }

    private Location getLocationForSighting(Sighting sighting) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM location l "
                + "JOIN sighting si ON l.locationId = si.locId WHERE si.sightingId = ?";
        Location loc = jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationDaoDBImpl.LocationMapper(),
                sighting.getSightingId());
        return loc;
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO sighting(sightingDate, superId, locId) VALUES(?,?,?)";
        jdbc.update(INSERT_SIGHTING,
                Date.valueOf(sighting.getSightingDate()),
                sighting.getHero().getId(),
                sighting.getLoc().getLocationId());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setSightingId(newId);

        return sighting;
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());

        addHeroAndLocationToSightings(sightings);

        return sightings;
    }

    private void addHeroAndLocationToSightings(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setHero(getHeroForSighting(sighting));
            sighting.setLoc(getLocationForSighting(sighting));
        }
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE sighting "
                + "SET sightingDate = ?, superId = ?, locId = ? WHERE sightingId = ?";
        jdbc.update(UPDATE_SIGHTING,
                Date.valueOf(sighting.getSightingDate()),
                sighting.getHero().getId(),
                sighting.getLoc().getLocationId(),
                sighting.getSightingId());
    }

    @Override
    public void deleteSightingById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE sightingId = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setSightingId(rs.getInt("sightingId"));
            sighting.setSightingDate(rs.getDate("sightingDate").toLocalDate());

            return sighting;
        }
    }
}