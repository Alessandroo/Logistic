package com.logistic.model.systemunits.orm;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.model.systemunits.entities.TypeCargo;

/**
 * Created by Vojts on 10.12.2016.
 */
public class ORMTypeCargo extends ORMEntity {
    TypeCargo typeCargo = new TypeCargo();

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

    public TypeCargo getEntity() {
        return typeCargo;
    }

    public void setEntity(TypeCargo typeCargo) {
        this.typeCargo = typeCargo;
    }
}
