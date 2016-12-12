package com.logistic.model.systemunits.orm;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.dao.interfaces.DAO;
import com.logistic.model.systemunits.entities.CarCrew;

/**
 * Created by Vojts on 10.12.2016.
 */
public class ORMCarCrew extends ORMEntity {
    CarCrew carCrew = new CarCrew();

    @Override
    public void create() throws DAOException {
        if (dao == null) {
            dao = daoFactory.getCarCrewDAO();
        }
        dao.create(carCrew);
    }

    @Override
    public void read() throws DAOException {
        if (dao == null) {
            dao = daoFactory.getCarCrewDAO();
        }
        dao.read(carCrew);
    }

    @Override
    public void update() throws DAOException {
        if (dao == null) {
            dao = daoFactory.getCarCrewDAO();
        }
        dao.update(carCrew);
    }

    @Override
    public void delete() throws DAOException {
        if (dao == null) {
            dao = daoFactory.getCarCrewDAO();
        }
        dao.delete(carCrew);
    }

    public static int getCount() throws DAOException {
        DAO staticDAO = daoFactory.getCarCrewDAO();
        return staticDAO.count_element();
    }

    public static CarCrew[] getPage(int page, int itemsPerPage) throws DAOException {
        DAO staticDAO = daoFactory.getCarCrewDAO();
        return (CarCrew[])staticDAO.getPage(page, itemsPerPage);
    }

    public CarCrew getEntity(){
        return carCrew;
    }

    public void setEntity(CarCrew carCrew) {
        this.carCrew = carCrew;
    }
}
