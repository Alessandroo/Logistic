package com.logistic.model.systemunits.orm;

import com.logistic.model.systemunits.entities.User;
import com.logistic.dao.interfaces.DAO;
import com.logistic.dao.exceptions.DAOException;

/**
 * ORM wrapper for the <code>User</code>. This class must be used in servlets to
 * manipulate CRUD operations for the plain entity through the concrete DAO.
 * DAO object is obtained from <code>DAOAbstractFactory</code>, incapsulated in superclass 
 * <code>ORMEnitity</code>. DAO initialization is lazy, so feel free to instantiate ORMCity objects.
 *
 * @author Mike
 * @version 0.4
 * @see  User , ORMEntity
 */
public class ORMUser extends ORMEntity {

    User user = new User();

	//Get-set interface for incapsulated object
	public User getEntity() {
		return user;
	}

	public void setEntity(User user) {
		this.user = user;
	}

	//ORM interface for incapsulated object

	public void create() throws DAOException {
		if (dao == null) {
			dao = daoFactory.getUserDAO();
		}
		dao.create(user);
	}
    public void read() throws DAOException {
    	if (dao == null) {
			dao = daoFactory.getUserDAO();
		}
		dao.read(user);
    }
    public void update() throws DAOException {
    	if (dao == null) {
			dao = daoFactory.getUserDAO();
		}
	   	dao.update(user);
    }
    public void delete() throws DAOException {
    	if (dao == null) {
			dao = daoFactory.getUserDAO();
		}
		dao.delete(user);
    }

    /**
	 * Get any page of users from DAO layer. 
	 *
	 * @param  page 			number of page to show
	 * @param  itemsPerPage		number of items to show on one page
	 * @param  sortBy 			field to sort by
	 * @param  asc 				sorting in asc order if true
	 * @return cityEntity[] 	the array of users on demanded page.
	 * @throws DAOException 	if any DAO error occurs
	 */
    public static User[] getPage(int page, int itemsPerPage,
                                 String sortBy, boolean asc) throws DAOException {

    	DAO staticDAO = daoFactory.getUserDAO();
        return (User[])staticDAO.getPage(page, itemsPerPage, sortBy, asc);
    }
    
    /**
     *	Retrieves total nuber of users from DAO layer.
     * 
     * @return int 				nuber of Users
     * @throws DAOException 	if any DAO internal error occur
     */
    public static int getCount() throws DAOException {
    	DAO staticDAO = daoFactory.getUserDAO();
        return staticDAO.count_element();
    }

}