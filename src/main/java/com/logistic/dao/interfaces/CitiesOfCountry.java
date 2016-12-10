package com.logistic.dao.interfaces;

import com.logistic.dao.exceptions.InternalDAOException;
import com.logistic.dao.exceptions.InvalidDataDAOException;

/**
 * Created by Vojts on 24.11.2016.
 */
public interface CitiesOfCountry {
    CityEntity[] getCities(String country) throws InternalDAOException, InvalidDataDAOException;
    String[] getCountries();
}
