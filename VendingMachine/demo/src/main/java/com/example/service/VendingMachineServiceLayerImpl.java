package com.example.service;

import java.math.BigDecimal;
import java.util.List;

import com.example.dao.DataValidationException;
import com.example.dao.InsufficientFundsException;
import com.example.dao.InvalidItemException;
import com.example.dao.NoItemInventoryException;
import com.example.dao.VendingMachineAuditDao;
import com.example.dao.VendingMachine_DAO;
import com.example.dao.VendingMachinePersistenceException;
import com.example.dto.Change;
import com.example.dto.Item;

public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

	private VendingMachine_DAO dao;
	private VendingMachineAuditDao auditDao;

	// Initializes a constructor to inject dao and auditDao objects
	public VendingMachineServiceLayerImpl(VendingMachine_DAO dao, VendingMachineAuditDao auditDao) {
		this.dao = dao;
		this.auditDao = auditDao;
	}

	// Buy an item from the vending machine and return the change
	@Override
	public Change buyItems(String name, BigDecimal userFunds)
			throws VendingMachinePersistenceException, InsufficientFundsException,
			NoItemInventoryException, InvalidItemException, DataValidationException {

		validateData(name, userFunds);
		Change change = null;

		if (dao.getItem(name) == null) {
			throw new InvalidItemException("Sorry, but this item does not exist in the vending machine");
		}

		BigDecimal itemPrice = dao.getItem(name).getPrice();
		int inventoryLevel = dao.getItem(name).getQuantity();

		if (itemPrice.compareTo(userFunds) > 0) {
			throw new InsufficientFundsException("Sorry, but you don't have enough funds to buy this item\n");
		} else if (inventoryLevel == 0) {
			throw new NoItemInventoryException("Sorry, but this item is currently out of stock");
		} else {
			auditDao.writeAuditEntry("Items were purchased");
			change = dao.buyItem(name, userFunds);
		}
		return change;

	}

	// Add item to the vending machine
	@Override
	public void addItem(Item item) throws DataValidationException, VendingMachinePersistenceException {

		if (item.getItemName() == null || item.getPrice() == null || item.getQuantity() < 0) {
			throw new DataValidationException("ERROR: The item must have a name, price and valid quantity");
		} else {
			dao.addItem(item);
		}
	}

	// Returns all items from the vending machine and writes the time that this
	// occured.
	@Override
	public List<Item> getAllItems() throws VendingMachinePersistenceException {
		auditDao.writeAuditEntry("All items have been retrieved");
		return dao.getItems();

	}

	// Checks whether the data that the user entered is valid
	private void validateData(String name, BigDecimal userFunds) throws DataValidationException {

		if (name == null || name.trim().length() == 0 || userFunds == null
				|| userFunds.compareTo(BigDecimal.valueOf(0.00)) < 0) {
			throw new DataValidationException("ERROR: You must insert some cash and insert an existing item name\n");
		}
		return;
	}

}