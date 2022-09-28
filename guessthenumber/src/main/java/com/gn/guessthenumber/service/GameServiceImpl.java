package com.gn.guessthenumber.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gn.guessthenumber.dto.Game;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    Random rng = new Random();

    // create new game
    @Override
    public String newGame(Game game) {

        game.setFinished(false);
        game.setAnswer(generateAnswer());
        gameRepository.save(game);

        String response = "201 GAME SUCCESSFULLY CREATED! Game ID: " + game.getId();
        return response;
    }

    @Override
    public Game findGame(Long id) {
        if (gameRepository.findById(id).isPresent()) {
            Game game = gameRepository.findById(id).get();
            return game;
        }
        return null;
    }

    @Override
    public Iterable<Game> listGames() {
        Iterable<Game> games = gameRepository.findAll();
        for (Game game : games) {
            System.out.println(game);

        }
        return games;
    }

    public int generateAnswer() {
        ArrayList<Integer> nums = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        String answer = "";
        for (int i = 0; i < 4; i++) {
            int index;
            if (i == 0) {
                index = rng.nextInt(1, nums.size());
            } else {
                index = rng.nextInt(nums.size());
            }
            int num = nums.get(index);
            nums.remove(index);
            answer += num;
        }
        return Integer.parseInt(answer);
    }

    public Game endGame(Long id) {
        Game game = findGame(id);
        game.setFinished(true);
        return gameRepository.save(game);
    }
}