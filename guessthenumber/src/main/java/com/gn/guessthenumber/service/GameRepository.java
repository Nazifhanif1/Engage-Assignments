package com.gn.guessthenumber.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.gn.guessthenumber.dto.Game;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {

}