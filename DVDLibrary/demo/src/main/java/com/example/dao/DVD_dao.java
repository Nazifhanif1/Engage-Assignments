package com.example.dao;

import java.util.List;

import com.example.dto.DVD;

public interface DVD_dao {

    DVD addDVD(String title, DVD dvd) throws DVD_daoException;

    DVD removeDVD(String title) throws DVD_daoException;

    List<DVD> getAllDVDs() throws DVD_daoException;

    DVD getDVD(String title) throws DVD_daoException;

    DVD editDVDInfo(String title, String pieceOfInformation, String change) throws DVD_daoException;

}