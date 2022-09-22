package com.fm.controller;

import com.fm.dao.DataPersistenceException;
import com.fm.model.Order;
import com.fm.service.FlooringService;
import com.fm.service.InvalidOrderNumberException;
import com.fm.service.OrderValidationException;
import com.fm.service.ProductValidationException;
import com.fm.service.StateValidationException;
import com.fm.view.View;
import java.time.LocalDate;

public class Controller {

    FlooringService service;
    View view;

    public Controller(FlooringService service, View view) {
        this.service = service;
        this.view = view;
    }

    public void run() {

        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            while (keepGoing) {

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        getOrdersByDate();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        exportData();

                    case 6:
                        keepGoing = false;
                        break;

                    default:
                        error();
                }

            }
            exitMessage();
        } catch (DataPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void addOrder() throws DataPersistenceException {
        try {
            Order o = service.getOrderDetails(view.getOrder());
            view.displayOrder(o);
            String reply = view.askSave();
            if (reply.equalsIgnoreCase("Y")) {
                service.addOrder(o);
                view.displayAddOrderSuccess(true, o);
            } else if (reply.equalsIgnoreCase("N")) {
                view.displayAddOrderSuccess(false, o);
            } else {
                error();
            }
        } catch (OrderValidationException
                | StateValidationException | ProductValidationException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void getOrdersByDate() throws DataPersistenceException {
        LocalDate dateChoice = view.inputDate();
        view.displayDateBanner(dateChoice);
        try {
            view.displayDateOrders(service.getOrders(dateChoice));
            view.displayContinue();
        } catch (InvalidOrderNumberException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void editOrder() throws DataPersistenceException {
        view.displayEditOrderBanner();
        try {
            LocalDate dateChoice = view.inputDate();
            int orderNumber = view.inputOrderNumber();
            Order savedOrder = service.getOrder(dateChoice, orderNumber);
            Order editedOrder = view.editOrderInfo(savedOrder);
            Order updatedOrder = service.compareOrders(savedOrder, editedOrder);
            view.displayEditOrderBanner();
            view.displayOrder(updatedOrder);
            String response = view.askSave();
            if (response.equalsIgnoreCase("Y")) {
                service.editOrder(updatedOrder);
                view.displayEditOrderSuccess(true, updatedOrder);
            } else if (response.equalsIgnoreCase("N")) {
                view.displayEditOrderSuccess(false, updatedOrder);
            } else {
                error();
            }
        } catch (InvalidOrderNumberException
                | ProductValidationException | StateValidationException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void removeOrder() throws DataPersistenceException {
        view.displayRemoveOrderBanner();
        LocalDate dateChoice = view.inputDate();
        view.displayDateBanner(dateChoice);
        try {
            view.displayDateOrders(service.getOrders(dateChoice));
            int orderNumber = view.inputOrderNumber();
            Order o = service.getOrder(dateChoice, orderNumber);
            view.displayRemoveOrderBanner();
            view.displayOrder(o);
            String response = view.askRemove();
            if (response.equalsIgnoreCase("Y")) {
                service.removeOrder(o);
                view.displayRemoveOrderSuccess(true, o);
            } else if (response.equalsIgnoreCase("N")) {
                view.displayRemoveOrderSuccess(false, o);
            } else {
                error();
            }
        } catch (InvalidOrderNumberException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void exportData() throws DataPersistenceException {
        service.exportAllData();
        view.displayExportSuccessBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

    private void error() {
        view.displayErrorBanner();
    }

}