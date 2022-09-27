package com.sg.superhero.dao;

import com.sg.superhero.dto.Superhero;

import java.util.List;

public interface SuperheroDao {

    Superhero createHero(Superhero superhero);

    void updateHero(Superhero superhero);

    void deleteHeroById(int id);

    Superhero getHeroById(int id);

    List<Superhero> getAllHeroes();

}