package com.citycon.dao;
import com.citycon.model.systemunits.entities.*;

import java.util.List;

/**
 * Created by Vojts on 09.11.2016.
 */
public interface DAO {
    void create(Entity newElement) throws DAOException;
    Entity read(int id) throws DAOException;
    void update(Entity updateElement) throws DAOException;
    void delete(Entity deleteElement) throws DAOException;
    Entity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc) throws DAOException;
}
