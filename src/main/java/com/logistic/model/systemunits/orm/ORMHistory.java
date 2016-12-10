package com.logistic.model.systemunits.orm;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.model.systemunits.entities.History;

/**
 * Created by Vojts on 10.12.2016.
 */
public class ORMHistory extends ORMEntity {
    History history = new History();

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

    public History getEntity() {
        return history;
    }

    public void setEntity(History history) {
        this.history = history;
    }
}
