package com.example.ui;

import java.util.Scanner;

public class UserIOImpl implements UserIO {

    public void print(String msg) {
        System.out.println(msg);
    }

    public int readInteger() {

        Scanner myScanner = new Scanner(System.in);
        int integer = myScanner.nextInt();
        return integer;
    }

    public String readString() {

        Scanner myScanner = new Scanner(System.in);
        String string = myScanner.nextLine();
        return string;
    }
}