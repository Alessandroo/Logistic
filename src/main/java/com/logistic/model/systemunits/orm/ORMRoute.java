package com.logistic.model.systemunits.orm;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.model.systemunits.entities.Route;

/**
 * Created by Vojts on 10.12.2016.
 */
public class ORMRoute extends ORMEntity {
    Route route = new Route();

    @Override
    public void create() throws DAOException {

    }

    @Override
    public void read() throws DAOException {

    }

    @Override
    public void update() throws DAOException {

    }

    @Override
    public void delete() throws DAOException {

    }

    public Route getEntity() {
        return route;
    }

    public void setEntity(Route route) {
        this.route = route;
    }
}