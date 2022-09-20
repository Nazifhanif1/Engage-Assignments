package com.example.controller;

import java.math.BigDecimal;

import com.example.dao.InsufficientFundsException;
import com.example.dao.InvalidItemException;
import com.example.dao.VendingMachinePersistenceException;
import com.example.dto.Change;
import com.example.service.VendingMachineServiceLayer;
import com.example.ui.VendingMachineView;

public class VendingMachine_Controller {

    private VendingMachineView view;
    private VendingMachineServiceLayer serviceLayer;

    public VendingMachine_Controller(VendingMachineView view, VendingMachineServiceLayer serviceLayer) {
        this.view = view;
        this.serviceLayer = serviceLayer;
    }

    public void run() throws Exception {
        displayItems();
        boolean running = true;
        while (running) {
            try {
                int menuSelection = getMenuSelection();
                switch (menuSelection) {
                    case 1:
                        BigDecimal userFunds = view.insertFunds();
                        String userSelection = view.selectItem();
                        Change change = serviceLayer.buyItems(userSelection, userFunds);
                        view.displayChange(change.getCoins());
                        break;

                    case 2:
                        displayItems();
                        break;

                    case 3:
                        running = false;
                        break;

                }
            } catch (InvalidItemException | VendingMachinePersistenceException | InsufficientFundsException e) {
                view.DisplayErrorMessage(e.getMessage());
            }
        }
        view.displayExitMessage();

    }

    private void displayItems() throws VendingMachinePersistenceException {
        view.displayItemList(serviceLayer.getAllItems());
    }

    private int getMenuSelection() {
        return view.PrintMenuAndGetSelection();
    }

}
