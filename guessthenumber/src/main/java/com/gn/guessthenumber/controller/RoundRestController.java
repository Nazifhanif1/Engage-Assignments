package com.gn.guessthenumber.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gn.guessthenumber.dto.Round;
import com.gn.guessthenumber.service.RoundServiceImpl;

@RestController
public class RoundRestController {

    @Autowired
    private RoundServiceImpl roundServiceImpl;

    // Get guess for a game
    @PostMapping("/guess")
    public Round guess(@RequestBody Round round) {
        return roundServiceImpl.newRound(round);
    }

    // Get all rounds for a specific game id
    @GetMapping("/rounds/{id}")
    public List<Round> findRounds(@PathVariable Long id) {
        return roundServiceImpl.getAllRounds(id);
    }
}