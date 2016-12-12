package com.logistic.model.systemunits.orm;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.dao.interfaces.DAO;
import com.logistic.model.systemunits.entities.Route;
import com.logistic.model.systemunits.entities.RouteArray;

/**
 * Created by Vojts on 10.12.2016.
 */
public class ORMRoute extends ORMEntity {
    RouteArray route = new RouteArray(0);

    @Override
    public void create() throws DAOException {
        if (dao == null) {
            dao = daoFactory.getRouteDAO();
        }
        dao.create(route);
    }

    @Override
    public void read() throws DAOException {
        if (dao == null) {
            dao = daoFactory.getRouteDAO();
        }
        dao.read(route);
    }

    @Override
    public void update() throws DAOException {
        if (dao == null) {
            dao = daoFactory.getRouteDAO();
        }
        dao.update(route);
    }

    @Override
    public void delete() throws DAOException {
        if (dao == null) {
            dao = daoFactory.getRouteDAO();
        }
        dao.delete(route);
    }

    public static RouteArray[] getPage(int page, int itemsPerPage) throws DAOException {
        DAO staticDAO = daoFactory.getRouteDAO();
        return (RouteArray[])staticDAO.getPage(page, itemsPerPage);
    }

    public RouteArray getEntity() {
        return route;
    }

    public void setEntity(RouteArray route) {
        this.route = route;
    }
}
