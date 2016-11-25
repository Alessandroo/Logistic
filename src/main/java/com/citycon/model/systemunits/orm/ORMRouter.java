package com.citycon.model.systemunits.orm;

import com.citycon.dao.interfaces.RoutersOfCity;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.entities.RouterEntity;
import com.citycon.dao.interfaces.DAO;
import com.citycon.dao.mysql.RouterDAO;
import com.citycon.dao.exceptions.DAOException;

/**
 * ORM wrapper for the <code>RouterEntity</code>. This class must be used in servlets to
 * manipulate CRUD operations for the plain entity through the concrete DAO. DAO object
 * is obtained from <code>DAOAbstractFactory</code>, incapsulated in superclass 
 * <code>ORMEnitity</code>. DAO initialization is lazy, so feel free to instantiate ORMCity objects.
 *
 * @author Mike
 * @version 1.0
 * @see  RouterEntity, ORMEntity
 */
public class ORMRouter extends ORMEntity {
	DAO dao;
	RouterEntity router = new RouterEntity();

	//Get-set interface for incapsulated object
	
	public int getId() {
		return router.getId();
	}
	public void setId(int id) {
		router.setId(id);
	}

	public String getName() {
		return router.getName();
	}
	public String getSN() {
		return router.getSN();
	}
	public int getPortsNum() {
		return router.getPortsNum();
	}
	public int getUsedPortsNum() {
		return router.getUsedPortsNum();
	}
	public int getCityId() {
		return router.getCityId();
	}
	public boolean isActive() {
		return router.isActive();
	}
	public String getCityName() {
		return router.getCityName();
	}
	public String getCountryName() {
		return router.getCountryName();
	}

	public void setName(String name) {
		router.setName(name);
	}
	public void setSN(String SN) {
		router.setSN(SN);
	}
	public void setPortsNum(int portsNum) {
		router.setPortsNum(portsNum);
	}
	public void setUsedPortsNum(int usedPortsNum) {
		router.setUsedPortsNum(usedPortsNum);
	}
	public void setActive(boolean isActive) {
		router.setActive(isActive);
	}
	public void setCityId(Integer sityId) {
		router.setCityId(sityId);
	}
	public void setCityName(String cityName) {
		router.setCityName(cityName);
	}
	public void setCountryName(String countryName) {
		router.setCountryName(countryName);
	}

	//ORM interface for incapsulated object

	public void create() throws DAOException {
		if (dao == null) {
			dao = daoFactory.getRouterDAO();
		}
		dao.create(router);
	}
    public void read() throws DAOException {
    	if (dao == null) {
			dao = daoFactory.getRouterDAO();
		}
		dao.read(router);
    }
    public void update() throws DAOException {
    	if (dao == null) {
			dao = daoFactory.getRouterDAO();
		}
		dao.update(router);
    }
    public void delete() throws DAOException {
    	if (dao == null) {
			dao = daoFactory.getRouterDAO();
		}
		dao.delete(router);
    }
    
   	public RouterEntity getEntity()  {
		return router;
	}

     /**
	 * Get any page of routers from DAO layer. 
	 *
	 * @param  page 			number of page to show
	 * @param  itemsPerPage		number of items to show on one page
	 * @param  sortBy 			field to sort by
	 * @param  asc 				sorting in asc order if true
	 * @return cityEntity[] 	the array of routers on demanded page.
	 * @throws DAOException 	if any DAO error occurs
	 */
    public static RouterEntity[] getPage(int page, int itemsPerPage, 
    							String sortBy, boolean asc) throws DAOException {
        DAO staticDAO = daoFactory.getRouterDAO();
        return (RouterEntity[])staticDAO.getPage(page, itemsPerPage, sortBy, asc);
    }

	/**
	 * Get any page of routers for concrete city from DAO layer. 
	 *
	 * @param  page 			number of page to show
	 * @param  itemsPerPage		number of items to show on one page
	 * @param  sortBy 			field to sort by
	 * @param  asc 				sorting in asc order if true
	 * @return cityEntity[] 	the array of routers on demanded page.
	 * @throws DAOException 	if any DAO error occurs
	 */
	public static RouterEntity[] getPage(int page, int itemsPerPage,
				 String sortBy, boolean asc, CityEntity city) throws DAOException {

		RoutersOfCity staticDAORouters = (RoutersOfCity) daoFactory.getRouterDAO();
		return staticDAORouters.getPage(page, itemsPerPage, sortBy, asc, city);
	}

	 /**
     *	Retrieves total number of routers from DAO layer.
     * 
     * @return int 				number of connections
     * @throws DAOException 	if any DAO internal error occur
     */
    public static int getCount() throws DAOException {
    	DAO staticDAO = daoFactory.getRouterDAO();
        return staticDAO.count_element();
    }

     /**
     *	Retrieves number of routers for concrete city from DAO layer.
     * 
     * @return int 				number of connections
     * @throws DAOException 	if any DAO internal error occur
     */
    public static int getCount(CityEntity city) throws DAOException {
    	RouterDAO staticDAO = (RouterDAO)daoFactory.getRouterDAO();
        return staticDAO.count_element(city);
    }
}