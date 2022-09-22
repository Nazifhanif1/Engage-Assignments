package com.fm.dao;

import com.fm.model.Product;

public interface ProductsDao {

    Product getProduct(String productType) throws DataPersistenceException;

}