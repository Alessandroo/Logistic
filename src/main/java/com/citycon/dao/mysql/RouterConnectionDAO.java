package com.citycon.dao.mysql;

import com.citycon.dao.DAO;
import com.citycon.dao.DAOException;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.RouterConnectionEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vojts on 09.11.2016.
 */
public class RouterConnectionDAO extends MySQLDAO {
    private static volatile RouterConnectionDAO instance;

    private RouterConnectionDAO(){}

    public RouterConnectionEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc) throws DAOException {
        if (false) {
            throw new DAOException("Dummy");
        }
        RouterConnectionEntity routerConnections[] = new RouterConnectionEntity[itemsPerPage];
        for (int i = 0; i< itemsPerPage; ++i) {
            routerConnections[i] = new RouterConnectionEntity();
        }
        return routerConnections;
    }

    public int create(Entity newElement) throws DAOException {
        if(false) {
            throw new DAOException("Dummy");
        }
        return 0;
    }

    public int read(Entity readElement) throws DAOException {
        if(false) {
            throw new DAOException("Dummy");
        }
        return 0;
    }

    public int update(Entity updateElement) throws DAOException {
        if(false) {
            throw new DAOException("Dummy");
        }
        return 0;
    }

    public void delete(Entity deleteElement) throws DAOException {
        if(false) {
            throw new DAOException("Dummy");
        }
    }
    public static RouterConnectionDAO getInstance() {
        RouterConnectionDAO localInstance = instance;
        if (localInstance == null) {
            synchronized (RouterConnectionDAO.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new RouterConnectionDAO();
                }
            }
        }
        return localInstance;
    }
}
