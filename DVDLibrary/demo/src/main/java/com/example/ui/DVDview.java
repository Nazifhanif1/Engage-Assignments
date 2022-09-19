package com.example.ui;

import java.util.List;

import com.example.dto.DVD;

public class DVDview {

    private UserIO io;

    public DVDview(UserIO io) {
        this.io = io;
    }

    // Display the menu to the user.
    public int printMenuAndGetSelection() {

        io.print("Please select from the following choices:");
        io.print("1. Add a DVD to the collection");
        io.print("2. Remove a DVD from the collection");
        io.print("3. Edit information for existing DVD");
        io.print("4. List all DVDs in the collection");
        io.print("5. Display information for a particular DVD");
        io.print("6. Exit");

        io.print("Please make your selection and enter a number from 1 to 6");
        return io.readInteger();
    }

    // Allow the user to enter information about the DVD
    public DVD getNewDVDInfo() {

        io.print("Please enter the DVD's title");
        String title = io.readString();

        io.print("Please enter the release date of the DVD");
        String releaseDate = io.readString();

        io.print("Please enter the MPAARating of the DVD");
        String MPAARating = io.readString();

        io.print("Please enter the director's name");
        String directorName = io.readString();

        io.print("Please enter the studio's name");
        String studio = io.readString();

        io.print("Please enter any additional information about the DVD");
        String userRating = io.readString();

        DVD currentDVD = new DVD();
        currentDVD.setTitle(title);
        currentDVD.setReleaseDate(releaseDate);
        currentDVD.setMPAArating(MPAARating);
        currentDVD.setDirectorName(directorName);
        currentDVD.setStudio(studio);
        currentDVD.setUserRating(userRating);

        return currentDVD;
    }

    // Display a new message for creating a DVD
    public void createDVDBanner() {
        io.print(" ");
        io.print("**** Create DVD ****");
    }

    // Show that the creation of the DVD has been successful.
    public void createDVDSuccessBanner() {
        io.print(" ");
        io.print("**** DVD has been successfully created, press enter to continue ****");
        io.readString();
    }

    // Display all DVDs and their information from a DVD collection
    public void displayDVDList(List<DVD> DVDList) {

        for (DVD dvd : DVDList) {
            io.print(" ");
            String dvdInfo = "Title: " + dvd.getTitle()
                    + "\nRelease Date: " + dvd.getReleaseDate()
                    + "\nMPAA Rating: " + dvd.getMPAArating()
                    + "\nDirector Name: " + dvd.getDirectorName()
                    + "\nStudio: " + dvd.getStudio()
                    + "\nUser Rating: " + dvd.getUserRating();

            io.print(dvdInfo);
        }
        io.print("Please hit enter to continue");
        io.readString();
    }

    // Show that the DVDs are being displayed.
    public void displayAllDVDBanner() {
        io.print("**** Display all DVDs ****");
    }

    // Show that the display for all DVDs has been successful.
    public void displayAllSuccessBanner() {
        io.print("All DVDs were displayed successfully");
    }

    // Request title name from the user.
    public String DVDTitleRequest() {
        io.print("Please write the DVD's title");
        String title = io.readString();
        return title;
    }

    // Display information about a particular DVD
    public DVD displayDVD(DVD dvd) {

        if (dvd != null) {
            String dvdInfo = "Title: " + dvd.getTitle()
                    + "\nRelease Date: " + dvd.getReleaseDate()
                    + "\nMPAA Rating: " + dvd.getMPAArating()
                    + "\nDirector Name: " + dvd.getDirectorName()
                    + "\nStudio: " + dvd.getStudio()
                    + "\nUser Rating: " + dvd.getUserRating();

            io.print(dvdInfo);
        }
        io.print("Please hit enter to continue");
        io.readString();

        return dvd;
    }

    // Show that the DVD is being displayed.
    public void displayDVDBanner() {
        io.print("**** Display DVD ****");
    }

    // Determine whether the display was successful or not.
    public void displayDVDSuccessBanner(DVD dvd) {

        if (dvd != null) {
            io.print("DVD was successfully displayed");
        } else {
            io.print("No such DVD found");
        }
    }

    public void removeDVDBanner() {
        io.print(" ");
        io.print("**** Remove DVD ****");
    }

    // Determine whether the removal was successful or not
    public void removeDVDSuccessBanner(DVD dvd) {

        if (dvd != null) {
            io.print(" ");
            io.print("**** DVD WAS REMOVED SUCCESSFULLY! ****");
            io.print(" ");
        } else {
            io.print("No such DVD found");
        }
    }

    public void editDVDBanner() {
        io.print(" ");
        io.print("**** Edit DVD ****");
    }

    // Display a message for unknown command.
    public void unknownCommand() {
        io.print("command not recognised, please try again");
    }

    // Display exit message
    public void displayExitMessage() {
        io.print("Good bye!");
    }

    // Display error message
    public void displayErrorMessage(String errorMsg) {
        io.print("== Error == ");
        io.print(errorMsg);
    }

    public int printEditMenuAndGetSelection() {
        io.print("Which field do you want to change?");
        io.print("Edit DVD menu");
        io.print("1. Title");
        io.print("2. MPAA rating");
        io.print("3. Director's name");
        io.print("4. User rating");
        io.print("5. Studio name");
        io.print("6. Release Date");
        io.print("7. Exit edit menu");
        return io.readInteger();
    }

    public void displayNullDvd() {
        io.print("No such DVD");
    }

    public String getTitle() {
        io.print("Please enter the new DVD Title");
        return io.readString();
    }

    public String getMpaaRating() {
        io.print("Please enter the new DVD MPAA rating");
        return io.readString();
    }

    public String getDirectorName() {
        io.print("Please enter the new director's name");
        return io.readString();
    }

    public String getUserRating() {
        io.print("Please enter the new user rating");
        return io.readString();
    }

    public String getStudioName() {
        io.print("Please enter the new studio name");
        return io.readString();
    }

    public String getReleaseDate() {
        io.print("Please enter the new DVD release date");
        return io.readString();
    }

    public void displayEditResult() {
        io.print(" ");
        io.print("**** DVD Successfully edited ****");
        io.print(" ");
    }

}