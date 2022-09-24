package com.superhero.dao;

import com.superhero.dto.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SuperheroDaoDBImpl implements SuperheroDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Superhero getHeroById(int id) {
        try {
            final String SELECT_SUPERHEROES_BY_ID = "SELECT * FROM superhero WHERE superheroId = ?";
            Superhero hero = jdbc.queryForObject(SELECT_SUPERHEROES_BY_ID, new HeroMapper(), id);
            return hero;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Superhero> getAllHeroes() {
        final String SELECT_ALL_SUPERHEROES = "SELECT * FROM superhero";
        List<Superhero> heroes = jdbc.query(SELECT_ALL_SUPERHEROES, new HeroMapper());
        return heroes;
    }

    @Override
    @Transactional
    public Superhero createHero(Superhero superhero) {
        final String INSERT_SUPERHERO = "INSERT INTO superhero(superheroName, description, superpower) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_SUPERHERO,
                superhero.getName(),
                superhero.getDescription(),
                superhero.getSuperpower());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superhero.setId(newId);
        return superhero;
    }

    @Override
    public void updateHero(Superhero superhero) {
        final String UPDATE_SUPERHERO = "UPDATE superhero SET superheroName = ?, description = ?, superpower = ? "
                + "WHERE superheroId = ?";
        jdbc.update(UPDATE_SUPERHERO,
                superhero.getName(),
                superhero.getDescription(),
                superhero.getSuperpower(),
                superhero.getId());
    }

    @Override
    @Transactional
    public void deleteHeroById(int id) {
        final String DELETE_HERO_ORG = "DELETE FROM super_organisation WHERE superId = ?";
        jdbc.update(DELETE_HERO_ORG, id);

        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE superId = ?";
        jdbc.update(DELETE_SIGHTING, id);

        final String DELETE_SUPERHERO = "DELETE FROM superhero WHERE superheroId = ?";
        jdbc.update(DELETE_SUPERHERO, id);
    }

    public static final class HeroMapper implements RowMapper<Superhero> {

        @Override
        public Superhero mapRow(ResultSet rs, int index) throws SQLException {
            Superhero hero = new Superhero();
            hero.setId(rs.getInt("superheroId"));
            hero.setName(rs.getString("superheroName"));
            hero.setDescription(rs.getString("description"));
            hero.setSuperpower(rs.getString("superpower"));

            return hero;
        }
    }

}