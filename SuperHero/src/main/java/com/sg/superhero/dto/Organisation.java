package com.sg.superhero.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Organisation {

    private int organisationId;
    private String organisationName;
    private String description;
    private String contactInfo;

    private List<Superhero> members;

}
