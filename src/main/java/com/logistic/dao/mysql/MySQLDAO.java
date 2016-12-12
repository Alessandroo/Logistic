package com.logistic.dao.mysql;

import com.logistic.dao.interfaces.DAO;
import com.logistic.dao.exceptions.InternalDAOException;
import com.logistic.dao.exceptions.InvalidDataDAOException;
import com.logistic.model.systemunits.entities.Entity;
import org.slf4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vojts on 09.11.2016.
 */
public abstract class MySQLDAO implements DAO {

    protected String nameTable;

    protected Connection connection;
    protected Statement statement;
    protected PreparedStatement preparedStatement;
    protected ResultSet resultSet;

    /**
     * @throws InternalDAOException
     */
    protected MySQLDAO() throws InternalDAOException {
    }

    private Connection getConnection() throws InternalDAOException {
        Connection connection = MySQLDAOConnection.getInstance().getConnection();
        return connection;
    }

    protected Statement getStatement() throws InternalDAOException{
        Statement statement;
        try {
            connection = getConnection();
            statement = connection.createStatement();
        }catch (SQLException e) {
            throw new InternalDAOException(e);
        }
        return statement;
    }

    protected PreparedStatement getPrepareStatement(String search) throws InternalDAOException{
        PreparedStatement preparedStatement;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(search);
        }catch (SQLException e) {
            throw new InternalDAOException(e);
        }
        return preparedStatement;
    }

    protected void close() throws InternalDAOException {
        try {
            if(connection != null){
                if(!connection.isClosed()){
                    connection.close();
                }
            }
            if(statement != null) {
                if (!statement.isClosed()) {
                    statement.close();
                }
            }
            if(preparedStatement != null) {
                if (!preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            }
            if(resultSet!= null) {
                if (!resultSet.isClosed()) {
                    resultSet.close();
                }
            }
        } catch (SQLException e) {
            throw new InternalDAOException(e);
        }
    }

    /**
     * @return
     * @throws InternalDAOException
     */
    public int count_element() throws InternalDAOException {
        int count = 0;

        String search = String.format("select count(`id`) from %s", nameTable);

        statement = getStatement();

        try {
            resultSet = statement.executeQuery(search);


            if (resultSet.first()) {
                count = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            throw new InternalDAOException("Get count elements failed", e);
        }
        finally {
            close();
        }

        return count;
    }

    /**
     * @param deleteElement
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    public void delete(Entity deleteElement) throws InternalDAOException, InvalidDataDAOException {

        String delete = "delete from" + nameTable + "where `id`=" + deleteElement.getId();

        statement = getStatement();

        try {
            statement.executeUpdate(delete);

        } catch (SQLException e) {
            throw new InvalidDataDAOException("Delete failed", e);
        }
        finally {
            close();
        }
    }

}