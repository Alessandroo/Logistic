package com.logistic.model.systemunits.orm;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.model.systemunits.entities.Road;

/**
 * Created by Vojts on 10.12.2016.
 */
public class ORMRoad extends ORMEntity {
    Road road = new Road();

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

    public Road getEntity() {
        return road;
    }

    public void setEntity(Road road) {
        this.road = road;
    }
}
