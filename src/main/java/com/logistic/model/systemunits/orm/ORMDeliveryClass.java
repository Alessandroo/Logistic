package com.logistic.model.systemunits.orm;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.model.systemunits.entities.DeliveryClass;

/**
 * Created by Vojts on 10.12.2016.
 */
public class ORMDeliveryClass extends ORMEntity {
    DeliveryClass deliveryClass = new DeliveryClass();

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

    public DeliveryClass getEntity() {
        return deliveryClass;
    }

    public void setEntity(DeliveryClass deliveryClass) {
        this.deliveryClass = deliveryClass;
    }
}
