package com.fm.service;

import com.fm.dao.DataPersistenceException;
import com.fm.dao.OrdersDao;
import com.fm.dao.ProductsDao;
import com.fm.dao.TaxesDao;
import com.fm.model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class FlooringServiceImpl implements FlooringService {

    private OrdersDao ordersDao;
    private ProductsDao productsDao;
    private TaxesDao taxesDao;

    public FlooringServiceImpl(OrdersDao ordersDao, ProductsDao productsDao,
            TaxesDao taxesDao) {
        this.ordersDao = ordersDao;
        this.productsDao = productsDao;
        this.taxesDao = taxesDao;

    }

    @Override
    public List<Order> getOrders(LocalDate chosenDate) throws InvalidOrderNumberException,
            DataPersistenceException {
        List<Order> ordersByDate = ordersDao.getOrders(chosenDate);
        if (!ordersByDate.isEmpty()) {
            return ordersByDate;
        } else {
            throw new InvalidOrderNumberException("ERROR: No orders "
                    + "exist on that date.");
        }
    }

    @Override
    public Order getOrder(LocalDate chosenDate, int orderNumber)
            throws DataPersistenceException, InvalidOrderNumberException {
        List<Order> orders = getOrders(chosenDate);
        Order chosenOrder = orders.stream()
                .filter(o -> o.getOrderNumber() == orderNumber)
                .findFirst().orElse(null);
        if (chosenOrder != null) {
            return chosenOrder;
        } else {
            throw new InvalidOrderNumberException("ERROR: No orders with that number "
                    + "exist on that date.");
        }
    }

    @Override
    public Order getOrderDetails(Order o) throws DataPersistenceException,
            OrderValidationException, StateValidationException, ProductValidationException {

        validateOrder(o);
        calculateTax(o);
        calculateMaterial(o);
        calculateTotal(o);

        return o;

    }

    private void calculateTax(Order o) throws DataPersistenceException,
            StateValidationException {
        // Set state information in order
        Tax stateTax = taxesDao.getState(o.getState());
        if (stateTax == null) {
            throw new StateValidationException("ERROR: Invalid state entry");
        }
        o.setState(stateTax.getStateAbbr());
        o.setTaxRate(stateTax.getTaxRate());
    }

    private void calculateMaterial(Order o) throws DataPersistenceException,
            ProductValidationException {
        // Set product information in order.
        Product chosenProduct = productsDao.getProduct(o.getProductType());
        if (chosenProduct == null) {
            throw new ProductValidationException("ERROR: We do not sell that " + "product.");
        }
        o.setProductType(chosenProduct.getProductType());
        o.setCostPerSquareFoot(chosenProduct.getCostPerSquareFoot());
        o.setLaborCostPerSquareFoot(chosenProduct.getLaborCostPerSquareFoot());
    }

    private void calculateTotal(Order o) {
        // Calculate other order fields.
        o.setMaterialCost(o.getCostPerSquareFoot().multiply(o.getArea())
                .setScale(2, RoundingMode.HALF_UP));
        o.setLaborCost(o.getLaborCostPerSquareFoot().multiply(o.getArea())
                .setScale(2, RoundingMode.HALF_UP));
        o.setTax(o.getTaxRate().divide(new BigDecimal("100.00"))
                .multiply((o.getMaterialCost().add(o.getLaborCost())))
                .setScale(2, RoundingMode.HALF_UP));
        o.setTotal(o.getMaterialCost().add(o.getLaborCost()).add(o.getTax()));
    }

    private void validateOrder(Order o) throws OrderValidationException {
        String message = "";
        if (o.getCustomerName().trim().isEmpty() || o.getCustomerName() == null) {
            message += "Customer name is required.\n";
        }
        if (o.getState().trim().isEmpty() || o.getState() == null) {
            message += "State is required.\n";
        }
        if (o.getProductType().trim().isEmpty() || o.getProductType() == null) {
            message += "Product type is required.\n";
        }
        if (o.getArea().compareTo(BigDecimal.ZERO) == 0 || o.getArea() == null) {
            message += "Area square footage is required.";
        }
        if (!message.isEmpty()) {
            throw new OrderValidationException(message);
        }
    }

    @Override
    public Order addOrder(Order o) throws DataPersistenceException {
        ordersDao.addOrder(o);
        return o;
    }

    @Override
    public Order compareOrders(Order savedOrder, Order editedOrder)
            throws DataPersistenceException, StateValidationException,
            ProductValidationException {

        if (editedOrder.getCustomerName() == null
                || editedOrder.getCustomerName().trim().equals("")) {
        } else {
            savedOrder.setCustomerName(editedOrder.getCustomerName());
        }

        if (editedOrder.getState() == null
                || editedOrder.getState().trim().equals("")) {
        } else {
            savedOrder.setState(editedOrder.getState());
            calculateTax(savedOrder);
        }

        if (editedOrder.getProductType() == null
                || editedOrder.getProductType().equals("")) {
        } else {
            savedOrder.setProductType(editedOrder.getProductType());
            calculateMaterial(savedOrder);
        }

        if (editedOrder.getArea() == null
                || (editedOrder.getArea().compareTo(BigDecimal.ZERO)) == 0) {
        } else {
            savedOrder.setArea(editedOrder.getArea());
        }

        calculateTotal(savedOrder);

        return savedOrder;
    }

    @Override
    public Order removeOrder(Order removedOrder) throws DataPersistenceException,
            InvalidOrderNumberException {
        removedOrder = ordersDao.removeOrder(removedOrder);
        if (removedOrder != null) {
            return removedOrder;
        } else {
            throw new InvalidOrderNumberException("ERROR: No orders with that number "
                    + "exist on that date.");
        }
    }

    @Override
    public Order editOrder(Order updatedOrder) throws DataPersistenceException,
            InvalidOrderNumberException {
        updatedOrder = ordersDao.editOrder(updatedOrder);
        if (updatedOrder != null) {
            return updatedOrder;
        } else {
            throw new InvalidOrderNumberException("ERROR: No orders with that number "
                    + "exist on that date.");
        }
    }

    @Override
    public void exportAllData() throws DataPersistenceException {
        ordersDao.getAllOrders();

    }

}