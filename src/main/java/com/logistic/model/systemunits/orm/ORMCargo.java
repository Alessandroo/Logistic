package com.logistic.model.systemunits.orm;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.model.systemunits.entities.Cargo;

/**
 * Created by Vojts on 10.12.2016.
 */
public class ORMCargo extends ORMEntity {
    Cargo cargo = new Cargo();

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

    public Cargo getEntity(){
        return cargo;
    }

    public void setEntity(Cargo cargo) {
        this.cargo = cargo;
    }
}
