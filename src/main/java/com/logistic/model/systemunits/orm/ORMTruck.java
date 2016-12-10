package com.logistic.model.systemunits.orm;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.model.systemunits.entities.Truck;

/**
 * Created by Vojts on 10.12.2016.
 */
public class ORMTruck extends ORMEntity {
    Truck truck = new Truck();

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

    public Truck getEntity() {
        return truck;
    }

    public void setEntity(Truck truck) {
        this.truck = truck;
    }
}
