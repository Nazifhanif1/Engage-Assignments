package com.example.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.example.dto.Change;
import com.example.dto.Item;
import com.example.dto.Change.Coin;

public class VendingMachineView {

    private UserIo io;

    public VendingMachineView(UserIo io) {
        this.io = io;
    }

    // Display items in the vending machine
    public void displayItemList(List<Item> ItemList) {

        for (Item item : ItemList) {
            String ItemInfo = "Item Name: " + item.getItemName()
                    + "\nPrice: " + item.getPrice()
                    + "\nQuantity: " + item.getQuantity();

            io.print(ItemInfo);
            io.print(" ");
        }
        io.print("Please hit enter to continue");
    }

    // Insert money into vending machine
    public BigDecimal insertFunds() {

        String userInput = io.readString("Please insert some money into the vending machine");
        io.print("");
        BigDecimal userFunds = new BigDecimal(userInput);
        userFunds.setScale(2, RoundingMode.HALF_UP);
        io.print("You have entered: $" + userFunds);
        return userFunds;
    }

    // Select an item from a vending machine
    public String selectItem() {

        String userInput = io.readString("Please select the item that you want to buy");
        io.print("");
        io.print("You have selected: " + userInput);
        return userInput;
    }

    public void displayChange(List<Coin> coins) {

        int penny_counter = 0;
        int nickel_counter = 0;
        int dime_counter = 0;
        int quarter_counter = 0;

        for (Change.Coin coin : coins) {
            if (coin == Coin.PENNY) {
                penny_counter += 1;
            } else if (coin == Coin.NICKEL) {
                nickel_counter += 1;
            } else if (coin == Coin.DIME) {
                dime_counter += 1;
            } else if (coin == Coin.QUARTER) {
                quarter_counter += 1;
            }
        }
        io.print("Your change contains the following:");
        io.print(penny_counter + " Pennies");
        io.print(nickel_counter + " Nickels");
        io.print(dime_counter + " Dimes");
        io.print(quarter_counter + " Quarters");
    }

    public int PrintMenuAndGetSelection() {
        io.print("Menu");
        io.print("1. Buy Item");
        io.print("2. View Items");
        io.print("3. Exit");

        return io.readInteger("Please select from above choices : ");
    }

    public void DisplayErrorMessage(String message) {
        io.print(message);
    }

    public void displayExitMessage() {
        io.print("Program quit");
        io.print("Goodbye!");
    }

}
