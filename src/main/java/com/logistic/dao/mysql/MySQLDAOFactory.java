package com.logistic.dao.mysql;

import com.logistic.dao.interfaces.DAO;
import com.logistic.dao.DAOAbstractFactory;
import com.logistic.dao.exceptions.InternalDAOException;

/**
 * Created by Vojts on 09.11.2016.
 */
public class MySQLDAOFactory extends DAOAbstractFactory {

    /**
     * @return
     * @throws InternalDAOException
     */
    public DAO getUserDAO() throws InternalDAOException {
        return UserDAO.getInstance();
    }

}
