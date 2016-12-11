package com.logistic.model.systemunits.orm;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.dao.interfaces.DAO;
import com.logistic.model.systemunits.entities.Order;

/**
 * Created by Vojts on 10.12.2016.
 */
public class ORMOrder extends ORMEntity {
    Order order = new Order();

    @Override
    public void create() throws DAOException {
        if (dao == null) {
            dao = daoFactory.getOrderDAO();
        }
        dao.create(order);
    }

    @Override
    public void read() throws DAOException {
        if (dao == null) {
            dao = daoFactory.getOrderDAO();
        }
        dao.read(order);
    }

    @Override
    public void update() throws DAOException {
        if (dao == null) {
            dao = daoFactory.getOrderDAO();
        }
        dao.update(order);
    }

    @Override
    public void delete() throws DAOException {
        if (dao == null) {
            dao = daoFactory.getOrderDAO();
        }
        dao.delete(order);
    }

    public static Order[] getPage(int page, int itemsPerPage) throws DAOException {
        DAO staticDAO = daoFactory.getOrderDAO();
        return (Order[])staticDAO.getPage(page, itemsPerPage);
    }

    public Order getEntity() {
        return order;
    }

    public void setEntity(Order order) {
        this.order = order;
    }
}
