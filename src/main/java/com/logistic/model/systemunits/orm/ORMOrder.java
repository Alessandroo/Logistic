package com.logistic.model.systemunits.orm;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.model.systemunits.entities.Order;

/**
 * Created by Vojts on 10.12.2016.
 */
public class ORMOrder extends ORMEntity {
    Order order = new Order();

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

    public Order getEntity() {
        return order;
    }

    public void setEntity(Order order) {
        this.order = order;
    }
}
