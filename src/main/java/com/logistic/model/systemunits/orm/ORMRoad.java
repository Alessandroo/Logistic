package com.logistic.model.systemunits.orm;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.dao.interfaces.DAO;
import com.logistic.model.systemunits.entities.Road;

/**
 * Created by Vojts on 10.12.2016.
 */
public class ORMRoad extends ORMEntity {
    Road road = new Road();

    @Override
    public void create() throws DAOException {
        if (dao == null) {
            dao = daoFactory.getRoadDAO();
        }
        dao.create(road);
    }

    @Override
    public void read() throws DAOException {
        if (dao == null) {
            dao = daoFactory.getRoadDAO();
        }
        dao.read(road);
    }

    @Override
    public void update() throws DAOException {
        if (dao == null) {
            dao = daoFactory.getRoadDAO();
        }
        dao.update(road);
    }

    @Override
    public void delete() throws DAOException {
        if (dao == null) {
            dao = daoFactory.getRoadDAO();
        }
        dao.delete(road);
    }

    public static Road[] getPage(int page, int itemsPerPage) throws DAOException {
        DAO staticDAO = daoFactory.getRoadDAO();
        return (Road[])staticDAO.getPage(page, itemsPerPage);
    }

    public Road getEntity() {
        return road;
    }

    public void setEntity(Road road) {
        this.road = road;
    }
}
