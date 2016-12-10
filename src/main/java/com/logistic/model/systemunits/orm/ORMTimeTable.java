package com.logistic.model.systemunits.orm;

import com.logistic.dao.exceptions.DAOException;
import com.logistic.model.systemunits.entities.TimeTable;

/**
 * Created by Vojts on 10.12.2016.
 */
public class ORMTimeTable extends ORMEntity {
    TimeTable timeTable = new TimeTable();

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

    public TimeTable getEntity() {
        return timeTable;
    }

    public void setEntity(TimeTable timeTable) {
        this.timeTable = timeTable;
    }
}
