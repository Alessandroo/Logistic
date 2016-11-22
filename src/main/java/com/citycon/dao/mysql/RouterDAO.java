package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.DublicateKeyDAOException;
import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.RouterEntity;
import com.citycon.model.systemunits.entities.UserEntity;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Vojts on 09.11.2016.
 */
public class RouterDAO extends MySQLDAO implements RoutersOfCity{


    /**
     * @throws InternalDAOException
     */
    private RouterDAO() throws InternalDAOException {
        super();
        nameTable = " `Router` ";
        logger = LoggerFactory.getLogger("com.citycon.dao.mysql.RouterDAO");

        hashMap.put("", "`ID`");
        hashMap.put("id", "`ID`");
        hashMap.put("name", "`Name`");
        hashMap.put("SN", "`SN`");
        hashMap.put("portsNum", "`Port`");
        hashMap.put("isActive", "`In_Service`");
        hashMap.put("cityId", "`City_id`");
    }

    /**
     * @param page
     * @param itemsPerPage
     * @param sortBy
     * @param asc
     * @return
     * @throws InvalidDataDAOException
     * @throws InternalDAOException
     */
    public RouterEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc)
            throws InvalidDataDAOException, InternalDAOException {
        return this.getPage(page, itemsPerPage, sortBy, asc, null);
    }

    /**
     * @param newElement
     * @throws DublicateKeyDAOException
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    public void create(Entity newElement) throws DublicateKeyDAOException, InternalDAOException, InvalidDataDAOException {
        RouterEntity router= null;

        PreparedStatement preparedStatement = null;

        String insert = "insert into" + nameTable +
                "(`Name`, `SN`, `Port`, `In_Service`, `City_id`)" +
                " values (?, ?, ?, ?, ?)";

        try{
            router = (RouterEntity) newElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in create failed.", e);
            throw new InvalidDataDAOException("Cast Entity in create are failed", e);
        }

        try {
            preparedStatement = connection.prepareStatement(insert);
        } catch (SQLException e) {
            logger.warn("PrepareStatement in create wasn't created", e);
            throw new InternalDAOException("PrepareStatement in create wasn't created", e);
        }

        String log_parameters = "With parameters: Name("+ router.getName() + "), SN(" + router.getSN()
                + "), CountPorts(" + router.getPortsNum() + "), IsActive(" + router.isActive()
                + "), CityId(" + router.getCityId() + ")";

        try {
            preparedStatement.setString(1, router.getName());
            preparedStatement.setString(2, router.getSN());
            preparedStatement.setInt(3, router.getPortsNum());
            preparedStatement.setBoolean(4, router.isActive());
            preparedStatement.setInt(5, router.getCityId());

            preparedStatement.executeUpdate();

            logger.trace("Create {}.\n {}", nameTable, log_parameters);
        } catch (SQLException e){
            logger.info("Create {} failed.\n {}", nameTable, log_parameters, e);
            throw new DublicateKeyDAOException(String.format("Create %s failed", nameTable), e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.warn("Close PrepareStatement in create {} failed", nameTable, e);
                    throw new InternalDAOException(e);
                }
            }
        }
    }


    /**
     * @param readElement
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    public void read(Entity readElement)throws InternalDAOException, InvalidDataDAOException {
        RouterEntity router = null;

        PreparedStatement search_router = null;
        ResultSet resultSet = null;

        try {
            router = (RouterEntity) readElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in read failed.", e);
            throw new InvalidDataDAOException("Cast Entity in read failed.", e);
        }

        String log_parameters = "With parameters: ";

        String field = "";
        String value = "";

        if(router.getId() != 0) {
            field = "R.ID";
            value = String.valueOf(router.getId());
            log_parameters += "Id(" + value + ")";
        }
        else if(router.getSN() != null){
            field = "R.SN";
            value = "'" + router.getSN() + "'";
            log_parameters += "SN(" + value + ")";
        }
        else{
            logger.info("For reading router incorrectly chosen field, try ID or SN");
            throw new InvalidDataDAOException("For reading router incorrectly chosen field, try ID or SN");
        }

        String search = "select R.ID as ID, R.SN as SN, R.`Name`, R.`Port`, R.In_Service, R.City_id, " +
                "C.`Name` as CityName " +
                "from Router R join City C on R.City_id=C.ID where " + field + " = " + value;

        try {
            search_router = connection.prepareStatement(search);
        }catch (SQLException e) {
            logger.warn("PreparedStatement in read wasn't created", e);
            throw new InternalDAOException("PreparedStatement in read wasn't created", e);
        }

        try {
            resultSet = search_router.executeQuery();

            if(resultSet.first()) {
                router.setId(resultSet.getInt("id"));
                router.setName(resultSet.getString("Name"));
                router.setSN(resultSet.getString("SN"));
                router.setPortsNum(resultSet.getInt("Port"));
                router.isActive(resultSet.getBoolean("In_Service"));
                router.setCityId(resultSet.getInt("City_id"));
                router.setCityName(resultSet.getString("CityName"));

                logger.trace("Read {}.\n {}", nameTable, log_parameters);
            }
            else {
                logger.info("{} in read not found.\n {}", nameTable, log_parameters);
                throw new InvalidDataDAOException(String.format("%s in read not found", nameTable));
            }
        }catch (SQLException e){
            logger.info("Read {} failed.\n {}", nameTable, log_parameters, e);
            throw new InternalDAOException(String.format("Read %s failed", nameTable), e);
        }
        finally {
            if (search_router!=null){
                try {
                    search_router.close();
                } catch (SQLException e) {
                    logger.warn("Close PrepareStatement in read {} failed", nameTable, e);
                    throw new InternalDAOException(e);
                }
            }
            if (resultSet!= null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    logger.warn("Close ResultSet in read {} failed", nameTable,e);
                    throw new InternalDAOException(e);
                }
            }
        }
    }

    /**
     * @param updateElement
     * @throws InvalidDataDAOException, InternalDAOException
     */
    public void update(Entity updateElement)
            throws DublicateKeyDAOException, InvalidDataDAOException, InternalDAOException {
        RouterEntity router = null;

        String update = "update" + nameTable +
                "set `Name`=?, `SN`=?, `Port`=?, `In_Service`=?, `City_id`=? " +
                "where `id`=?";

        PreparedStatement preparedStatement = null;

        try {
            router = (RouterEntity) updateElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in update failed.", e);
            throw new InvalidDataDAOException("Cast Entity in update failed.", e);
        }

        try {
            preparedStatement = connection.prepareStatement(update);
        }catch (SQLException e) {
            logger.warn("PreparedStatement in update wasn't created", e);
            throw new InternalDAOException("PreparedStatement in update wasn't created", e);
        }

        String log_parameters = "With parameters: id(" + router.getId() + ")";

        try {
            preparedStatement.setString(1, router.getName());
            preparedStatement.setString(2, router.getSN());
            preparedStatement.setInt(3, router.getPortsNum());
            preparedStatement.setBoolean(4, router.isActive());
            preparedStatement.setInt(5, router.getCityId());
            preparedStatement.setInt(6, router.getId());

            preparedStatement.executeUpdate();

            logger.trace("Update {}.\n {}", nameTable, log_parameters);
        } catch (SQLException e) {
            logger.info("Update {} failed.\n {}", nameTable, log_parameters, e);
            throw new DublicateKeyDAOException((String.format("Update %s failed", nameTable)), e);
        }
        finally {
            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.warn("Close PrepareStatement in update {} failed", nameTable, e);
                    throw new InternalDAOException(e);
                }
            }
        }
    }

    /**
     * @throws InternalDAOException
     */
    public static RouterDAO getInstance() throws InternalDAOException {
        return new RouterDAO();
    }

    @Override
    public RouterEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc, CityEntity city)
            throws InvalidDataDAOException, InternalDAOException {

        PreparedStatement search_routers = null;
        ResultSet resultSet = null;

        ArrayList<RouterEntity> routers = new ArrayList();

        String sorter = hashMap.get(sortBy);

        String search = "";

        String log_parameters = "With parameters: page("+ page + "), itemsPerPage(" + itemsPerPage
                + "), sortBy(" + sortBy + "), asc(" + asc + ")";

        if(sorter != null) {
            String sorting_direction = "";

            if(asc){
                sorting_direction = " asc ";
            }
            else {
                sorting_direction = " desc ";
            }

            if (city != null){
                if (city.getName() == null || city.getCountryName() == null){
                    logger.info("For GetPage Routers of City incorrectly" +
                            " chosen field, try Name and CountryName");
                    throw new InvalidDataDAOException("For GetPage Routers of City incorrectly" +
                            " chosen field, try Name and CountryName");
                }
                log_parameters += ", City(" + city.getName() + "), Country(" + city.getCountryName() + ")";

                search = "select Distinct R.ID as ID, R.SN as SN, R.`Name`, R.`Port`, R.In_Service, R.City_id, " +
                        "C.`Name` as CityName " +
                        "from Router R join City C on R.City_id=C.ID " +
                        "where C.`Name`='"+ city.getName() +"' and C.Country='" + city.getCountryName() +
                        "' order by " + sorter + sorting_direction + " limit ?, ?";
            }
            else {
                search = "select Distinct R.ID as ID, R.SN as SN, R.`Name`, R.`Port`, R.In_Service, R.City_id, " +
                        "C.`Name` as CityName " +
                        "from Router R join City C on R.City_id=C.ID " +
                        "order by " + sorter + sorting_direction + " limit ?, ?";
            }
        }
        else {
            logger.info("Enter parameter to sort in read {} are invalid.\n {}", nameTable, log_parameters);
            throw new InvalidDataDAOException("Enter parameter to sort in read are invalid");
        }

        try {
            search_routers = connection.prepareStatement(search);
        }catch (SQLException e) {
            logger.warn("PrepareStatement in getPage wasn't created");
            throw new InternalDAOException("PrepareStatement in getPage wasn't created", e);
        }

        try{
            search_routers.setInt(1, (page-1)*itemsPerPage);
            search_routers.setInt(2, itemsPerPage);

            resultSet =  search_routers.executeQuery();

            try {
                while (resultSet.next()) {
                    RouterEntity router = new RouterEntity();
                    router.setId(resultSet.getInt("id"));
                    router.setName(resultSet.getString("Name"));
                    router.setSN(resultSet.getString("SN"));
                    router.setPortsNum(resultSet.getInt("Port"));
                    router.isActive(resultSet.getBoolean("In_Service"));
                    router.setCityId(resultSet.getInt("City_id"));
                    router.setCityName(resultSet.getString("CityName"));
                    routers.add(router);
                }

                logger.trace("GetPage of {}.\n {}", nameTable, log_parameters);
            }catch (SQLException e){
                logger.info("GetPage {} failed.\n {}", nameTable, log_parameters, e);
                throw new InvalidDataDAOException(String.format("GetPage %s failed", nameTable), e);
            }
        }catch (SQLException e){
            logger.info("Put data to PrepareStatement in {} invalid. \n {}", nameTable, log_parameters, e);
            throw new InvalidDataDAOException(String.format("Put data to PrepareStatement in {} invalid",
                    nameTable), e);
        }
        finally {
            if (search_routers!=null){
                try {
                    search_routers.close();
                } catch (SQLException e) {
                    logger.warn("Close PrepareStatement in getPage {} failed", nameTable, e);
                    throw new InternalDAOException(e);
                }
            }
            if (resultSet!= null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    logger.warn("Close ResultSet in getPage {} failed", nameTable, e);
                    throw new InternalDAOException(e);
                }
            }
        }

        return routers.toArray(new RouterEntity[routers.size()]);
    }

    @Override
    public int count_element(CityEntity city) throws InvalidDataDAOException, InternalDAOException {
        int count = 0;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String search = "";

        if (city.getName() != null && city.getCountryName() != null){
            search = "SELECT COUNT(ID) " +
                    "FROM (SELECT DISTINCT Router.ID AS ID FROM Router " +
                    "INNER JOIN `City` ON (`Router`.`City_id` = `City`.`ID`) " +
                    "WHERE (`City`.`Name` = ? AND `City`.`Country` = ?)) E";
        }
        else{
            logger.info("For reading router incorrectly chosen field, try name and country");
            throw new InvalidDataDAOException("For reading router incorrectly chosen field," +
                    " try name and country");
        }

        try {
            preparedStatement = connection.prepareStatement(search);
        } catch (SQLException e) {
            logger.warn("PrepareStatement in get count wasn't created", e);
            throw new InternalDAOException("PrepareStatement in get count wasn't created", e);
        }

        String log_parameters = "With parameters: City(" + city.getName() + "), Country(" +
                city.getCountryName() + ")";

        try {
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountryName());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.first()) {
                count = resultSet.getInt(1);

                logger.trace("Get count elements. {}", log_parameters);
            }
        } catch (SQLException e) {
            logger.info("Get count elements failed. {}", log_parameters, e);
            throw new InternalDAOException("Get count elements failed.", e);
        }
        finally {
            if (preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.warn("Close PrepareStatement in get count false", e);
                    throw new InternalDAOException(e);
                }
            }
            if (resultSet!= null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    logger.warn("Close ResultSet in get count false",e);
                    throw new InternalDAOException(e);
                }
            }
        }

        return count;
    }
}
