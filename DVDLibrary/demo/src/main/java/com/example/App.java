package com.example;

import com.example.controller.DVDController;
import com.example.dao.DVD_dao;
import com.example.dao.DVD_daoFileImpl;
import com.example.ui.DVDview;
import com.example.ui.UserIO;
import com.example.ui.UserIOImpl;

public class App {

    public static void main(String[] args) {

        // Use dependency injection and wire the entire application.

        UserIO myIo = new UserIOImpl();
        DVD_dao myDao = new DVD_daoFileImpl();
        DVDview myView = new DVDview(myIo);
        DVDController Controller = new DVDController(myView, myDao);
        Controller.run();

    }

}