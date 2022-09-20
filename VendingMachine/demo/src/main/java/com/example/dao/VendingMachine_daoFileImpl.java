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
import java.math.BigDecimal;

import com.example.dto.Change;
import com.example.dto.Item;
import com.example.dto.Change.Coin;

public class VendingMachine_daoFileImpl implements VendingMachine_DAO {

    public static final String VendingMachine_File = "VendingMachine.txt";
    public static final String DELIMITER = "::";

    private Map<String, Item> Items = new HashMap<>();

    @Override
    public List<Item> getItems() throws VendingMachinePersistenceException {
        loadItem();
        return new ArrayList<Item>(Items.values());
    }

    @Override
    public Item getItem(String name) throws VendingMachinePersistenceException {
        loadItem();
        return Items.get(name);
    }

    private void loadItem() throws VendingMachinePersistenceException {

        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(VendingMachine_File)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException("Could not locate the file", e);
        }

        String currentLine;
        Item currentItem;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentItem = unmarshallItem(currentLine);
            Items.put(currentItem.getItemName(), currentItem);
        }
        scanner.close();
    }

    private Item unmarshallItem(String ItemAsText) {

        String[] ItemAsElements = ItemAsText.split(DELIMITER);
        BigDecimal price = BigDecimal.valueOf(Double.valueOf(ItemAsElements[1]));
        int Quant = Integer.parseInt(ItemAsElements[2]);
        Item itemFromFile = new Item();
        itemFromFile.setItemName(ItemAsElements[0]);
        itemFromFile.setPrice(price);
        itemFromFile.setQuantity(Quant);

        return itemFromFile;
    }

    @Override
    public Change buyItem(String userSelection, BigDecimal userFunds) throws VendingMachinePersistenceException {
        loadItem();
        Item item = Items.get(userSelection);
        item.setQuantity(item.getQuantity() - 1);
        Items.replace(userSelection, item);
        BigDecimal moneyDiff = userFunds.subtract(item.getPrice());
        Change change = calculateChange(moneyDiff);
        writeItem();
        return change;

    }

    @Override
    public void addItem(Item item) throws VendingMachinePersistenceException {
        loadItem();
        Items.put(item.getItemName(), item);
        writeItem();
        return;
    }

    @Override
    public Change calculateChange(BigDecimal newFunds) throws VendingMachinePersistenceException {

        Change change = new Change();
        List<Coin> coins = new ArrayList<Coin>();
        change.setTotalChange(newFunds);

        while (newFunds.compareTo(BigDecimal.ZERO) != 0) {

            if (newFunds.compareTo(BigDecimal.valueOf(0.25)) >= 0) {
                newFunds = newFunds.subtract(BigDecimal.valueOf(0.25));
                coins.add(change.getQuarter());
            } else if (newFunds.compareTo(BigDecimal.valueOf(0.10)) >= 0) {
                newFunds = newFunds.subtract(BigDecimal.valueOf(0.10));
                coins.add(change.getDime());
            } else if (newFunds.compareTo(BigDecimal.valueOf(0.05)) >= 0) {
                newFunds = newFunds.subtract(BigDecimal.valueOf(0.05));
                coins.add(change.getNickel());
            } else if (newFunds.compareTo(BigDecimal.valueOf(0.01)) >= 0) {
                newFunds = newFunds.subtract(BigDecimal.valueOf(0.01));
                coins.add(change.getPenny());
            }
        }
        change.setCoins(coins);
        return change;
    }

    private String marshallItem(Item item) {

        String ItemAsText = item.getItemName() + DELIMITER;
        ItemAsText += item.getPrice() + DELIMITER;
        ItemAsText += item.getQuantity();

        return ItemAsText;
    }

    // Write marshalled data into the text file.
    private void writeItem() throws VendingMachinePersistenceException {

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(VendingMachine_File));
        } catch (Exception e) {
            throw new VendingMachinePersistenceException("Could not save DVD data", e);
        }

        String ItemAsText;
        List<Item> ItemList = this.getItems();
        for (Item currentItem : ItemList) {
            ItemAsText = marshallItem(currentItem);
            out.println(ItemAsText);
            out.flush();
        }
        out.close();
    }
}
