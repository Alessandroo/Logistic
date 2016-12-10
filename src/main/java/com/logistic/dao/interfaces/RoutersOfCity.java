package com.logistic.dao.interfaces;

import com.logistic.dao.exceptions.InternalDAOException;
import com.logistic.dao.exceptions.InvalidDataDAOException;

/**
 * Created by Vojts on 16.11.2016.
 */
public interface RoutersOfCity {

    RouterEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc, CityEntity city)
            throws InvalidDataDAOException, InternalDAOException;

    int count_element(CityEntity city)
            throws InvalidDataDAOException, InternalDAOException;
}
