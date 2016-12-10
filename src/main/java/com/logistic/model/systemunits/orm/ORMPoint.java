package com.logistic.model.systemunits.orm;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.model.systemunits.entities.Point;

/**
 * Created by Vojts on 10.12.2016.
 */
public class ORMPoint extends ORMEntity {
    Point point = new Point();

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

    public Point getEntity() {
        return point;
    }

    public void setEntity(Point point) {
        this.point = point;
    }
}
