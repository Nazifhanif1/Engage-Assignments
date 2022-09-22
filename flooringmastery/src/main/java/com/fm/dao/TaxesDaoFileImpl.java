package com.fm.dao;

import com.fm.model.Tax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaxesDaoFileImpl implements TaxesDao {

    private static final String TAXES_FILE = "SampleFileData/Data/Taxes.txt";
    private static final String DELIMITER = ",";

    @Override
    public Tax getState(String state) throws DataPersistenceException {
        List<Tax> states = loadStates();
        if (states == null) {
            return null;
        } else {
            Tax chosenState = states.stream()
                    .filter(s -> s.getStateName().equalsIgnoreCase(state))
                    .findFirst().orElse(null);
            return chosenState;
        }
    }

    private List<Tax> loadStates() throws DataPersistenceException {
        Scanner scanner;
        List<Tax> states = new ArrayList<>();

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(TAXES_FILE)));
        } catch (FileNotFoundException e) {
            throw new DataPersistenceException(
                    "-_- Could not load states data into memory.", e);
        }

        String currentLine;
        String[] currentTokens;
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            if (currentTokens.length == 3) {
                Tax currentState = new Tax();
                currentState.setStateAbbr(currentTokens[0]);
                currentState.setStateName(currentTokens[1]);
                currentState.setTaxRate(new BigDecimal(currentTokens[2]));
                // Put currentState into the map using stateAbbr as the key
                states.add(currentState);
            } else {
                // Ignores line if delimited wrong or empty.
            }
        }
        scanner.close();

        if (!states.isEmpty()) {
            return states;
        } else {
            return null;
        }
    }
}