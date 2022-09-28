package com.gn.guessthenumber.service;

import com.gn.guessthenumber.dto.Game;

public interface GameService {

    String newGame(Game game);

    Game findGame(Long id);

    Iterable<Game> listGames();

    Game endGame(Long id);
}