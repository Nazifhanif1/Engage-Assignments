package com.gn.guessthenumber.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roundId;
    private Long gameId;
    private int guess;
    private String result;
    private String createDate;
}