package com.logistic.model.systemunits.orm;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.model.systemunits.entities.CarCrew;

/**
 * Created by Vojts on 10.12.2016.
 */
public class ORMCarCrew extends ORMEntity {
    CarCrew carCrew = new CarCrew();

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

    public CarCrew getEntity(){
        return carCrew;
    }

    public void setEntity(CarCrew carCrew) {
        this.carCrew = carCrew;
    }
}