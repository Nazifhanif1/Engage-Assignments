package com.gn.guessthenumber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.gn.guessthenumber.service.GameServiceImpl;
import com.gn.guessthenumber.dto.Game;

@RestController
public class GameRestController {

    @Autowired
    private GameServiceImpl gameServiceImpl;

    // Start a new game
    @PostMapping("/begin")
    public String beginGame(@RequestBody Game game) {
        return gameServiceImpl.newGame(game);
    }

    // Get a game by id
    @GetMapping("/game/{id}")
    public Game findGame(@PathVariable Long id) {
        return gameServiceImpl.findGame(id);
    }

    // Get a list of all games
    @GetMapping("/game")
    public Iterable<Game> listGames() {
        return gameServiceImpl.listGames();
    }
}