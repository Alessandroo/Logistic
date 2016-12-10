package com.logistic.dao;


import com.logistic.dao.exceptions.DAOException;
import com.logistic.dao.interfaces.DAO;

/**
 * Created by Vojts on 09.11.2016.
 */
public abstract class DAOAbstractFactory {
    public abstract DAO getUserDAO() throws DAOException;
}
