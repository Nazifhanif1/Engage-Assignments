package com.gn.guessthenumber.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.gn.guessthenumber.dto.Round;

@Repository
public interface RoundRepository extends CrudRepository<Round, Long> {

}