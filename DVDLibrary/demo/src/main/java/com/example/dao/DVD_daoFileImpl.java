package com.example.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.example.dto.DVD;

public class DVD_daoFileImpl implements DVD_dao {

    private Map<String, DVD> DVDs = new HashMap<>();

    public static final String DVD_File = "demo/DVD.txt";
    public static final String DELIMITER = "::";

    // Add a particular DVD by title
    @Override
    public DVD addDVD(String title, DVD dvd) throws DVD_daoException {
        loadDVD();
        DVD newDVD = DVDs.put(title, dvd);
        writeDVD();
        return newDVD;
    }

    // Remove a particular DVD by title
    @Override
    public DVD removeDVD(String title) throws DVD_daoException {
        loadDVD();
        DVD removedDVD = DVDs.remove(title);
        writeDVD();
        return removedDVD;
    }

    // Get all DVDs from the DVD collection
    @Override
    public List<DVD> getAllDVDs() throws DVD_daoException {
        loadDVD();
        return new ArrayList<DVD>(DVDs.values());
    }

    // Get a particular DVD from the DVD collection
    @Override
    public DVD getDVD(String title) throws DVD_daoException {
        loadDVD();
        return DVDs.get(title);
    }

    // Edit DVD attributes
    @Override
    public DVD editDVDInfo(String title, String selection, String change) throws DVD_daoException {
        loadDVD();
        DVD dvd = DVDs.get(title);

        if (dvd != null) {
            if (selection.equalsIgnoreCase("Title")) {
                dvd.setTitle(change);
            } else if (selection.equalsIgnoreCase("Release date")) {
                dvd.setReleaseDate(change);
            } else if (selection.equalsIgnoreCase("MPAA rating")) {
                dvd.setMPAArating(change);
            } else if (selection.equalsIgnoreCase("Director name")) {
                dvd.setDirectorName(change);
            } else if (selection.equalsIgnoreCase("Studio")) {
                dvd.setStudio(change);
            } else if (selection.equalsIgnoreCase("User rating")) {
                dvd.setUserRating(change);
            } else {
                dvd = null;
            }
        }
        if (dvd != null)
            DVDs.replace(title, dvd);
        writeDVD();
        return dvd;

    }

    // Translate data from file to an object in memory
    private DVD unmarshallDVD(String DVDAsText) {

        String[] DVDAsElements = DVDAsText.split(DELIMITER);
        String title = DVDAsElements[0];
        DVD dvdFromFile = new DVD();
        dvdFromFile.setTitle(title);
        dvdFromFile.setReleaseDate(DVDAsElements[1]);
        dvdFromFile.setMPAArating(DVDAsElements[2]);
        dvdFromFile.setDirectorName(DVDAsElements[3]);
        dvdFromFile.setStudio(DVDAsElements[4]);
        dvdFromFile.setUserRating(DVDAsElements[5]);

        return dvdFromFile;
    }

    // Put all unmarshalled data into the DVD collection
    private void loadDVD() throws DVD_daoException {

        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(DVD_File)));
        } catch (FileNotFoundException e) {
            throw new DVD_daoException("Could not locate the file", e);
        }

        String currentLine;
        DVD currentDVD;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentDVD = unmarshallDVD(currentLine);
            DVDs.put(currentDVD.getTitle(), currentDVD);
        }
        scanner.close();
    }

    // Translate data from object in memory into a text file.
    private String marshallDVD(DVD dvd) {

        String DVDAsText = dvd.getTitle() + DELIMITER;
        DVDAsText += dvd.getReleaseDate() + DELIMITER;
        DVDAsText += dvd.getMPAArating() + DELIMITER;
        DVDAsText += dvd.getDirectorName() + DELIMITER;
        DVDAsText += dvd.getStudio() + DELIMITER;
        DVDAsText += dvd.getUserRating() + DELIMITER;

        return DVDAsText;
    }

    // Write marshalled data into the text file.
    private void writeDVD() throws DVD_daoException {

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(DVD_File));
        } catch (Exception e) {
            throw new DVD_daoException("Could not save DVD data", e);
        }

        String DVDAsText;
        List<DVD> DVDList = this.getAllDVDs();
        for (DVD currentDVD : DVDList) {
            DVDAsText = marshallDVD(currentDVD);
            out.println(DVDAsText);
            out.flush();
        }
        out.close();
    }
}