package com.logistic.dao.interfaces;
import com.logistic.dao.exceptions.*;
import com.logistic.model.systemunits.entities.Entity;

/**
 * Created by Vojts on 09.11.2016.
 */
public interface DAO {
    void create(Entity newElement) throws DublicateKeyDAOException, 
    							InvalidDataDAOException, InternalDAOException;

    void read(Entity readElement) throws InvalidDataDAOException, InternalDAOException;

    void update(Entity updateElement) throws DublicateKeyDAOException, 
    							InvalidDataDAOException, InternalDAOException;

    void delete(Entity deleteElement) throws InvalidDataDAOException, InternalDAOException;

    Entity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc) 
		throws InvalidDataDAOException, InternalDAOException;

    int count_element() throws InternalDAOException;

}
