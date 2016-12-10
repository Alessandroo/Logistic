package com.logistic.dao.interfaces;

import com.logistic.dao.exceptions.InternalDAOException;
import com.logistic.dao.exceptions.InvalidDataDAOException;
import com.logistic.model.systemunits.entities.CityEntity;
import com.logistic.model.systemunits.entities.RouterConnectionEntity;

/**
 * Created by Vojts on 20.11.2016.
 */
public interface ConnectionsOfCity {
    RouterConnectionEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc, CityEntity city)
            throws InvalidDataDAOException, InternalDAOException;

    int count_element(CityEntity city) throws InvalidDataDAOException, InternalDAOException;
}
