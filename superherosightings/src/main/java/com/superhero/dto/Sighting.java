package com.superhero.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sighting {

    private int sightingId;
    private int heroId;
    private int locId;
    private LocalDate sightingDate;
    private Superhero hero;
    private Location loc;
}
