package com.gn.guessthenumber.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gn.guessthenumber.dto.Game;
import com.gn.guessthenumber.dto.Round;

@Service
public class RoundServiceImpl implements RoundService {

    @Autowired
    private RoundRepository roundRepository;
    @Autowired
    private GameServiceImpl gameServiceImpl;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    // New round for a game
    @Override
    public Round newRound(Round round) {
        Game game = gameServiceImpl.findGame(round.getGameId());
        int answer = game.getAnswer();

        String result = calcResult(round.getGuess(), answer);

        round.setResult(result);
        round.setCreateDate(formatter.format(LocalDateTime.now()));

        String wincondition = "e:4:p:0";
        if (result.equals(wincondition)) {
            System.out.println("Game won");
            gameServiceImpl.endGame(game.getId());
        }

        return roundRepository.save(round);
    }

    // Get all rounds for a specific game id
    @Override
    public List<Round> getAllRounds(Long id) {
        List<Round> result = new ArrayList<>();
        for (Round round : roundRepository.findAll()) {
            if (round.getGameId() == id) {
                result.add(round);
            }
        }
        return result;
    }

    public String calcResult(int guess, int answer) {
        String strguess = String.valueOf(guess);
        String stranswer = String.valueOf(answer);

        int exact = 0;
        int partial = 0;
        for (int i = 0; i < strguess.length(); i++) {
            if (stranswer.charAt(i) == strguess.charAt(i)) {
                exact++;
            } else if (stranswer.contains("" + strguess.charAt(i))) {
                partial++;
            }
        }
        String result = "e:" + exact + ":p:" + partial;

        return result;
    }

}