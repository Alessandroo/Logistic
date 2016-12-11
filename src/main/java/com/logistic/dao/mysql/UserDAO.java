package com.logistic.dao.mysql;

import com.logistic.dao.exceptions.DublicateKeyDAOException;
import com.logistic.dao.exceptions.InternalDAOException;
import com.logistic.dao.exceptions.InvalidDataDAOException;
import com.logistic.model.Grant;
import com.logistic.model.systemunits.entities.Entity;
import com.logistic.model.systemunits.entities.User;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
/**
 * Created by Vojts on 09.11.2016.
 */
public class UserDAO extends MySQLDAO {

    /**
     * @throws InternalDAOException
     */
    protected UserDAO() throws InternalDAOException {
        super();
        nameTable = " `User` ";
        logger = LoggerFactory.getLogger("com.logistic.dao.mysql.UserDAO");
    }

    public User[] getPage(int page, int itemsPerPage) throws InvalidDataDAOException, InternalDAOException {

        ArrayList<User> users = new ArrayList();

        String search = "select * from " + nameTable + " limit " + (page-1)*itemsPerPage + "," + itemsPerPage;

        statement = getStatement();

        try {
            resultSet =  statement.executeQuery(search);

            try {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setLogin(resultSet.getString("login"));
                    user.setPassword(resultSet.getString("password"));
                    user.setEmail(resultSet.getString("email"));
                    user.setName(resultSet.getString("name"));
                    user.setLast_name(resultSet.getString("last_name"));
                    user.setGroup(resultSet.getString("group"));
                    user.setTelephone(resultSet.getString("telephone"));
                    user.setCreateDate(resultSet.getDate("create_date"));
                    users.add(user);
                }
            }catch (SQLException e){
                throw new InvalidDataDAOException(e);
            }
        }catch (SQLException e){
            throw new InvalidDataDAOException(e);
        }
        finally {
            close();
        }

        return users.toArray(new User[users.size()]);
    }


    /**
     * @param newElement
     * @throws DublicateKeyDAOException
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    public void create(Entity newElement) throws DublicateKeyDAOException, InternalDAOException, InvalidDataDAOException {
        User user = null;

        Calendar calendar = Calendar.getInstance();
        Date startDate = new Date(calendar.getTime().getTime());

        String insert = "insert into" + nameTable +
                "(`login`, `password`, `email`, `name`, `last_name`, `group`, `telephone`, `create_date`)" +
                " values (?, ?, ?, ?, ?, ?)";

        try {
            user = (User) newElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in create failed.", e);
            throw new InvalidDataDAOException("Cast Entity in create are failed", e);
        }

        preparedStatement = getPrepareStatement(insert);

        try {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getLast_name());
            preparedStatement.setString(6, user.getGroup());
            preparedStatement.setString(7, user.getTelephone());
            preparedStatement.setDate(8,  startDate);

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DublicateKeyDAOException(String.format("Create %s failed", nameTable), e);
        }
        finally {
            close();
        }
    }

    /**
     * @param readElement
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    public void read(Entity readElement) throws InternalDAOException, InvalidDataDAOException {
        User user = null;

        String search = "select * from" + nameTable + "where `Login`=?";

        try {
            user = (User) readElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in read failed.", e);
            throw new InvalidDataDAOException("Cast Entity in read failed.", e);
        }

        if (user.getPassword() != null){
            search += " and `Pass`=?";
        }

        if(user.getLogin() == null){
            throw new InvalidDataDAOException("For reading user incorrectly chosen field, " +
                    "try Login with Password or only Login");
        }

        preparedStatement = getPrepareStatement(search);

        try {
            preparedStatement.setString(1, user.getLogin());

            if(user.getPassword() != null) {
                preparedStatement.setString(2, user.getPassword());
            }
            resultSet =  preparedStatement.executeQuery();
            if(resultSet.first()) {
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setLast_name(resultSet.getString("last_name"));
                user.setGroup(resultSet.getString("group"));
                user.setTelephone(resultSet.getString("telephone"));
                user.setCreateDate(resultSet.getDate("create_date"));
                user.setGrant(this.getGrantFromDB(user.getGroup()));
            }
            else{
                throw new InvalidDataDAOException(String.format("%s in read not found", nameTable));
            }
        }catch (SQLException e){
            throw new InternalDAOException(String.format("Read %s failed", nameTable), e);
        }
        finally {
            close();
        }

    }


    /**
     * @param updateElement
     * @throws DublicateKeyDAOException
     * @throws InvalidDataDAOException
     * @throws InternalDAOException
     */
    public void update(Entity updateElement) throws DublicateKeyDAOException, InvalidDataDAOException, InternalDAOException {
        User user = null;

        String update = "update" + nameTable + "set `login`=?, `password`=?, `email`=?, `name`=?, `last_name`, `group`=?, `telephone`=? where `id`=?";

        try {
            user = (User) updateElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in update failed.", e);
            throw new InvalidDataDAOException("Cast Entity in update failed.", e);
        }

        preparedStatement = getPrepareStatement(update);

        try {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getLast_name());
            preparedStatement.setString(6, user.getGroup());
            preparedStatement.setString(7, user.getTelephone());
            preparedStatement.setInt(8, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DublicateKeyDAOException((String.format("Update %s failed", nameTable)), e);
        }
        finally {
            close();
        }
    }

    private Grant getGrantFromDB(String group) throws InternalDAOException, InvalidDataDAOException {
        Grant grant = new Grant();

        String search = "select * from `Grant` where `idGrant`=?";

        preparedStatement = getPrepareStatement(search);

        try {
            preparedStatement.setString(1, group);

            resultSet =  preparedStatement.executeQuery();

            if(resultSet.first()) {
                grant.setSystemUnitsBranchLevel(resultSet.getInt("systemUnitBranch"));
                grant.setUsersBranchLevel(resultSet.getInt("userBranch"));
                grant.setClientBranchLevel(resultSet.getInt("clientBranch"));
                grant.setDriverBranchLevel(resultSet.getInt("driverBranch"));

            }
            else{
                throw new InvalidDataDAOException("Grant not found");
            }
        }catch (SQLException e){
            logger.info("Read grant failed", e);
            throw new InternalDAOException("Read grant failed", e);
        }
        finally {
            close();
        }

        return grant;
    }
}