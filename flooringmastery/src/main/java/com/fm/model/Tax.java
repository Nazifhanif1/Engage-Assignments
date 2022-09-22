package com.fm.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tax {

    private String stateAbbr;
    private String stateName;
    private BigDecimal taxRate;

}
