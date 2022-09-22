package com.fm.dao;

import com.fm.model.Order;
import java.time.LocalDate;
import java.util.List;

public interface OrdersDao {

    List<Order> getOrders(LocalDate dateChoice) throws DataPersistenceException;

    Order addOrder(Order o) throws DataPersistenceException;

    Order removeOrder(Order o) throws DataPersistenceException;

    Order editOrder(Order editedOrder) throws DataPersistenceException;

    void getAllOrders() throws DataPersistenceException;

}