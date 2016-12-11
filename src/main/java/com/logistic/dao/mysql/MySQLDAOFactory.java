package com.logistic.dao.mysql;

import com.logistic.dao.DAOAbstractFactory;
import com.logistic.dao.exceptions.DAOException;
import com.logistic.dao.exceptions.InternalDAOException;
import com.logistic.dao.interfaces.DAO;

/**
 * Created by Vojts on 09.11.2016.
 */
public class MySQLDAOFactory extends DAOAbstractFactory {

    /**
     * @return
     * @throws InternalDAOException
     */
    public DAO getUserDAO() throws InternalDAOException {
        return new UserDAO();
    }

    @Override
    public DAO getCargoDAO() throws DAOException {
        return new CargoDAO();
    }

    @Override
    public DAO getCarCrewDAO() throws DAOException {
        return new CarCrewDAO();
    }

    @Override
    public DAO getOrderDAO() throws DAOException {
        return new OrderDAO();
    }

    @Override
    public DAO getRoadDAO() throws DAOException {
        return new RoadDAO();
    }

    @Override
    public DAO getRouteDAO() throws DAOException {
        return new RouteDAO();
    }

    @Override
    public DAO getHistoryDAO() throws DAOException {
        return new HistoryDAO();
    }

}
