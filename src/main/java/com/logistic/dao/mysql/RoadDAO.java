package com.logistic.dao.mysql;

import com.logistic.dao.exceptions.DublicateKeyDAOException;
import com.logistic.dao.exceptions.InternalDAOException;
import com.logistic.dao.exceptions.InvalidDataDAOException;
import com.logistic.model.systemunits.entities.Entity;

/**
 * Created by Vojts on 11.12.2016.
 */
public class RoadDAO extends MySQLDAO {
    /**
     * @throws InternalDAOException
     */
    protected RoadDAO() throws InternalDAOException {
    }

    @Override
    public void create(Entity newElement) throws DublicateKeyDAOException, InvalidDataDAOException, InternalDAOException {

    }

    @Override
    public void read(Entity readElement) throws InvalidDataDAOException, InternalDAOException {

    }

    @Override
    public void update(Entity updateElement) throws DublicateKeyDAOException, InvalidDataDAOException, InternalDAOException {

    }

    @Override
    public Entity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc) throws InvalidDataDAOException, InternalDAOException {
        return new Entity[0];
    }
}
