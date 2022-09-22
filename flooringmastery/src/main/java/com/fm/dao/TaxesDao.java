package com.fm.dao;

import com.fm.model.Tax;

public interface TaxesDao {

    Tax getState(String state) throws DataPersistenceException;

}