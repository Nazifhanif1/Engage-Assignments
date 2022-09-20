package com.example.dao;

import java.math.BigDecimal;
import java.util.List;

import com.example.dto.*;

public interface VendingMachine_DAO {

    List<Item> getItems() throws VendingMachinePersistenceException;

    Item getItem(String name) throws VendingMachinePersistenceException;

    Change buyItem(String userSelection, BigDecimal userFunds) throws VendingMachinePersistenceException;

    void addItem(Item item) throws VendingMachinePersistenceException;

    Change calculateChange(BigDecimal newFunds) throws VendingMachinePersistenceException;

}
