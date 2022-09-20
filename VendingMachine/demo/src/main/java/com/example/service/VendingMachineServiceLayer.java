package com.example.service;

import java.math.BigDecimal;
import java.util.List;

import com.example.dao.VendingMachinePersistenceException;
import com.example.dao.DataValidationException;
import com.example.dao.InsufficientFundsException;
import com.example.dao.InvalidItemException;
import com.example.dao.NoItemInventoryException;
import com.example.dto.*;

public interface VendingMachineServiceLayer {

    Change buyItems(String name, BigDecimal cash) throws InsufficientFundsException,
            NoItemInventoryException,
            InvalidItemException, VendingMachinePersistenceException, DataValidationException;

    List<Item> getAllItems() throws VendingMachinePersistenceException;

    void addItem(Item item) throws VendingMachinePersistenceException, InvalidItemException, DataValidationException;
}