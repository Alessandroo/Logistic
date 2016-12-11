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
        if (dao == null) {
            dao = daoFactory.getCargoDAO();
        }
        dao.create(cargo);
    }

    @Override
    public void read() throws DAOException {
        if (dao == null) {
            dao = daoFactory.getCargoDAO();
        }
        dao.read(cargo);
    }

    @Override
    public void update() throws DAOException {
        if (dao == null) {
            dao = daoFactory.getCargoDAO();
        }
        dao.update(cargo);
    }

    @Override
    public void delete() throws DAOException {
        if (dao == null) {
            dao = daoFactory.getCargoDAO();
        }
        dao.delete(cargo);
    }

    public Cargo getEntity(){
        return cargo;
    }

    public void setEntity(Cargo cargo) {
        this.cargo = cargo;
    }
}
