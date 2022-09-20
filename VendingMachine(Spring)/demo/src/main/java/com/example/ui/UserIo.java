package com.example.ui;

import java.math.BigDecimal;

public interface UserIo {


    void print(String input);
	int readInteger(String msg);
	String readString(String msg);
	BigDecimal readBigDecimal(String msg);
}