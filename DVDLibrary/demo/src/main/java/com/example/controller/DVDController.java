package com.example.controller;

import com.example.dao.*;
import com.example.dto.*;
import com.example.ui.*;

public class DVDController {

    private DVDview view;
    private DVD_dao dao;

    public DVDController(DVDview view, DVD_dao dao) {
        this.view = view;
        this.dao = dao;
    }

    // This is where the controller begins executing
    /**
     * 
     */
    public void run() {
        boolean keepGoing = true;
        int userSelection = 0;

        try {
            while (keepGoing) {

                // Get menu selection from user
                userSelection = getMenuSelection();

                switch (userSelection) {

                    case 1:
                        createDVD();
                        break;
                    case 2:
                        removeDVD();
                        break;
                    case 3:
                        editDVD();
                        break;
                    case 4:
                        displayDVDs();
                        break;
                    case 5:
                        displayDVDByTitle();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        error();
                }
            }
            exitMessage();
        } catch (DVD_daoException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    // Receive menu selection from user
    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    // Create a new DVD
    private void createDVD() throws DVD_daoException {
        view.createDVDBanner();
        DVD newDVD = view.getNewDVDInfo();
        dao.addDVD(newDVD.getTitle(), newDVD);
        view.createDVDSuccessBanner();
    }

    // Display all DVDs in the collection
    private void displayDVDs() throws DVD_daoException {
        view.displayDVDList(dao.getAllDVDs());
        view.displayAllSuccessBanner();
    }

    // Display DVD by title
    private void displayDVDByTitle() throws DVD_daoException {
        view.displayDVDBanner();
        String DVDtitle = view.DVDTitleRequest();
        DVD tempDVD = view.displayDVD(dao.getDVD(DVDtitle));
        view.displayDVDSuccessBanner(tempDVD);
    }

    // Remove a particular DVD
    private void removeDVD() throws DVD_daoException {
        view.removeDVDBanner();
        String title = view.DVDTitleRequest();
        DVD dvd = dao.removeDVD(title);
        view.removeDVDSuccessBanner(dvd);
    }

    private void editDVD() throws DVD_daoException {
        view.editDVDBanner();
        String title = view.DVDTitleRequest();
        DVD dvdToEdit = dao.getDVD(title);
        if (dvdToEdit == null) {
            view.displayNullDvd();
        } else {
            view.displayDVD(dvdToEdit);
            int editMenuSelection = 0;
            boolean keepGoing = true;
            while (keepGoing) {
                editMenuSelection = view.printEditMenuAndGetSelection();

                switch (editMenuSelection) {
                    case 1:
                        editTitle(title);
                        break;
                    case 2:
                        editMpaaRating(title);
                        break;
                    case 3:
                        editDirectorName(title);
                        break;
                    case 4:
                        editUserRating(title);
                        break;
                    case 5:
                        editStudioName(title);
                        break;
                    case 6:
                        editReleaseDate(title);
                        break;
                    case 7:
                        keepGoing = false;
                        break;

                    default:
                        error();
                }
                if (keepGoing == false) {
                    break;
                }
            }
        }
    }

    private void editTitle(String title) throws DVD_daoException {
        String newReleaseDate = view.getTitle();
        dao.editDVDInfo(title, "Title", newReleaseDate);
        view.displayEditResult();
    }

    private void editReleaseDate(String title) throws DVD_daoException {
        String newReleaseDate = view.getReleaseDate();
        dao.editDVDInfo(title, "Release date", newReleaseDate);
        view.displayEditResult();
    }

    private void editMpaaRating(String title) throws DVD_daoException {
        String newMpaaRating = view.getMpaaRating();
        dao.editDVDInfo(title, "MPAA Rating", newMpaaRating);
        view.displayEditResult();
    }

    private void editDirectorName(String title) throws DVD_daoException {
        String newDirectorName = view.getDirectorName();
        dao.editDVDInfo(title, "Director name", newDirectorName);
        view.displayEditResult();
    }

    private void editUserRating(String title) throws DVD_daoException {
        String newUserRating = view.getUserRating();
        dao.editDVDInfo(title, "User rating", newUserRating);
        view.displayEditResult();
    }

    private void editStudioName(String title) throws DVD_daoException {
        String newStudioName = view.getStudioName();
        dao.editDVDInfo(title, "Studio", newStudioName);
        view.displayEditResult();
    }

    // Tell the user that the message is unknown
    private void error() {
        view.unknownCommand();
    }

    // Tell the user that the program has ended
    private void exitMessage() {
        view.displayExitMessage();
    }

}