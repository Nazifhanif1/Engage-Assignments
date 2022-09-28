package com.gn.guessthenumber.service;

import com.gn.guessthenumber.dto.Round;
import java.util.List;

public interface RoundService {

    Round newRound(Round round);

    List<Round> getAllRounds(Long id);
}