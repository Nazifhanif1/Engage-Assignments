package com.example.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {

    private String ItemName;
    private BigDecimal Price;
    private int Quantity;

}
